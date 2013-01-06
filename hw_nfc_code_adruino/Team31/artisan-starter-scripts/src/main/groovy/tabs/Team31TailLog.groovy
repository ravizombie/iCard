import com.axeda.drm.services.customobject.LogUtils
import groovy.xml.MarkupBuilder
import groovy.json.JsonBuilder
import net.sf.json.JSONArray

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 12/23/12
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */

def contentType = "application/json"
def serviceName = "TailLog"
def prefix = "Team31"
def logLinePrefix = ["DEBU","INFO","ERRO","WARN"]

def json = new JsonBuilder()

try {
    def fileNames = LogUtils.fileNames
    def groupedMessages = [:]
    if (fileNames?.size()>0) {

        fileNames.each() { fileName ->
            if (fileName.endsWith("customobjects.log")) {
                def currentLine = ""
                def currentScript = ""
                tail(new File(fileName),1000)?.each() { line ->
                    // if the line is a logging line
                    if (isALoggingLine(logLinePrefix,line)) {
                        def (content,scriptName) = formatLine(line)
                        if (currentLine != null && (currentScript?.startsWith(prefix))) {
                            appendMessage(groupedMessages,currentScript,currentLine)
                        }
                        currentLine = content
                        currentScript = scriptName
                    } else {
                        if (line != null && (!line?.trim().equals(""))) {
                            currentLine += line + "\n"
                        }
                    }
                }
                if (currentLine != null && (currentScript?.startsWith(prefix))) {
                    // add last line
                    appendMessage(groupedMessages,currentScript,currentLine)
                }
            }
        }
        // invert the lists
        def invertedMessages = [:]
        groupedMessages?.each() { k, List v ->
            invertedMessages.put(k,v.reverse())
        }

        return ["Content-Type": contentType,"Content":JSONArray.fromObject(invertedMessages).toString(2)]
    }
} catch (Exception e) {
    StringWriter sw = new StringWriter()
    PrintWriter ps = new PrintWriter(sw)
    e.printStackTrace(ps)

    json.Exception (
        description: "Execution Failed!!! An Exception was caught...",
        name: e.getMessage(),
        stack: sw.toString(),
    )
}

def appendMessage(groupedMessages, scriptName, content) {
    if (content != null && (!scriptName?.equals(""))) {
        def messageList = []

        if (groupedMessages.get(scriptName) != null) {
            messageList = groupedMessages.get(scriptName)
        }
        messageList << content

        groupedMessages.put(scriptName,messageList)
    }
}

def isALoggingLine(logLinePrefix,String line) {
    if (line?.length()>=4) {
        if (logLinePrefix.contains(line?.substring(0,4))) {
            return true
        } else {
            return false
        }
    } else {
        return false
    }
}

def tail(File src, int maxLines) throws FileNotFoundException, IOException {
    BufferedReader reader = new BufferedReader(new FileReader(src));
    String[] lines = new String[maxLines];
    int lastNdx = 0;
    for (String line=reader.readLine(); line != null; line=reader.readLine()) {
        if (lastNdx == lines.length) {
            lastNdx = 0;
        }
        lines[lastNdx++] = line;
    }
    return lines
}

def formatLine(line) {
    def scriptName = ""
    def debugLine = ""

    if (line instanceof String) {
        def chunks = line.split("\\s+")
        def counter = 0
        chunks.each() { chunk ->
            counter++
        }
        if (chunks.size() >=4) {
            dateStamp = chunks[1] + ": " + chunks[2].substring(0,chunks[2].indexOf(","))
            scriptName = chunks[3].substring(1,chunks[3].indexOf(","))
            // remove the script name
            debugLine = line.substring(line.lastIndexOf(scriptName)+scriptName.length())
            // now remove the threadstamp
            debugLine = "("+ dateStamp +") " + line.substring(line.indexOf("]")+1)
            //scriptName = scriptName.substring(0,scriptName.length()-1)
        } else {
            scriptName = "Unknown"
            debugLine = line
        }
    }

    return [debugLine,scriptName]

}

return ["Content-Type":contentType,"Content":json.toPrettyString()]
