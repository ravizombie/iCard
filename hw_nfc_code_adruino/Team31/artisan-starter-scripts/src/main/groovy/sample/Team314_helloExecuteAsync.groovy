import static com.axeda.sdk.v2.dsl.Bridges.*

/****************
 * Hello Asynchronous Groovy Execution!
 *
 * 4_helloExecuteAsync.groovy
 *
 * One of the important features in the Andover release is the asynchronous execution of Groovy scripts.
 * This is a "fire and forget" chain in which one Groovy script can invoke another Groovy script,
 * however the first script does not wait for the output of the invoked Groovy script.
 *
 * **************/

try {
// initiate an asynchronous execution of custom object "5_helloAsyncInvoked"
// pass in parameters of myname and myage with values "Fred" and "35"
// pass in a timeout of how long in seconds the executing script should wait for a confirmation before timing out
customObjectBridge.initiateAsync("5_helloAsyncInvoked", [myname:"Fred", myage: "35"], 600)

}
catch (Exception e){

logger.info(e.localizedMessage)
}
