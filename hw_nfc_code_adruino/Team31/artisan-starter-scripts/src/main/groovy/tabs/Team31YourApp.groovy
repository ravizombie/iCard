import com.axeda.drm.sdk.Context
import com.axeda.drm.sdk.data.*
import com.axeda.drm.sdk.device.*
import net.sf.json.JSONObject
import groovy.json.JsonBuilder
import net.sf.json.JSONArray
import com.axeda.drm.sdk.scripto.Request

/*
   YourApp.groovy

   This is your starter application for your m2m Hackathon project.

   You will have received a Device Kit featuring an Arduino board and a set of sensors.

   This board has already been registered to the Platform, and can be found using this service. The SerialNumber and Model in this service
   have already been populated. Get this working first, and your m2m App will be well underway!

   This service should be your basis for writing new server-side logic for your project. It provides a simple layout with
   error handling, and a couple of options for returning JSON.

   You can access this script via ghe following URL:

   https://atthackathon-sandbox.axeda.com/services/v1/rest/Scripto/execute/Team31YourApp?username=team31user&password=team31pass

   You can access

*/

def contentType = "application/json"
def serialNumber = "Team31Asset"
def modelNumber = "Team31Model"
def dataItemName = "ICCID"

// Create a JSON Builder
def json = new JsonBuilder()

// Global try/catch. Gotta have it, you never know when your code will be exceptional!
try {

    // all of our Request Parameters are available here
    def parameters = Request.parameters
    // if you want to post content directly to this service, it can be accessed in 3 ways:
    // get the POSTed content as a String
    def body = Request.body
    // get an inputStream of the POSTed content (great for binary)
    def inputStream = Request.inputStream
    // or, if this is a multi-part form with attachments, get your attachments here
    def attachments = Request.attachments

    // Uusing the V1 SDK Pattern here. Everything centers around the "Context"
    final def CONTEXT = Context.getSDKContext()

    DataItem dataitem

    // This is the V1 "Finder" pattern. You will find this for most domain objects.
    ModelFinder modelFinder = new ModelFinder(CONTEXT)
    modelFinder.setName(modelNumber)
    Model model = modelFinder.find()

    // THe Finder acts as the Criteria for finding an object. You can use code completion to see the available criteria
    DeviceFinder deviceFinder = new DeviceFinder(CONTEXT)
    deviceFinder.setModel(model)
    deviceFinder.setSerialNumber(serialNumber)
    Device device = deviceFinder.find()

    // Data values can be current (a single value per data item) or historical (N values).
    // Use the HistoricalDataFinder to lookup values in the past
    CurrentDataFinder cdFinder = new CurrentDataFinder(CONTEXT, device)
    DataValue dvalue = cdFinder.find(dataItemName)

    // construct our JSON response using our JSONBuilder
    json.Device (
            "serialNumber": device.serialNumber,
            "modelNumber": device.model.name,
            "lastContact":device.lastContactDate,
            "iccid": dvalue.asString()
    )

    // return the JSONBuilder contents
    // we specify the content type, and any object as the return (even an outputstream!)
    return ["Content-Type": contentType,"Content":json.toPrettyString()]

    // alternately you may just want to serial an Object as JSON:
    // return ["Content-Type": contentType,"Content":JSONArray.fromObject(invertedMessages).toString(2)]

} catch (Exception e) {

    // I knew you were exceptional!
    // we'll capture the output of the stack trace and return it in JSON
    StringWriter sw = new StringWriter()
    PrintWriter ps = new PrintWriter(sw)
    e.printStackTrace(ps)

    // Build up our output
    json.Exception (
        description: "Execution Failed!!! An Exception was caught...",
        name: e.getMessage(),
        stack: sw.toString(),
    )

    // return the output
    return ["Content-Type":contentType,"Content":json.toPrettyString()]
}


