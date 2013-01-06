import static com.axeda.sdk.v2.dsl.Bridges.*

/****************
 * Hello Synchronously Executing a Groovy Script!
 *
 * 3_helloExecuteSync.groovy
 *
 * This script executes the 5_helloInvokedScript.groovy script and waits for its output.
 *
 * **************/

def response = ""
try {
    def result = customObjectBridge.execute("5_helloInvokedScript", [myname: "Max", myage:"30"])

    response = result

}
catch (Exception e){

response = [error: e.localizedMessage]
}

return ['Content-Type': 'application/text', 'Content': response]
