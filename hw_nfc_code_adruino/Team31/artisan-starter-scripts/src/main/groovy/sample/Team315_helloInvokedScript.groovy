import com.axeda.drm.sdk.customobject.Call

/****************
 * Hello Invoked Groovy Script!
 *
 * 5_helloInvokedScript.groovy
 *
 * This script was invoked by either the 3_helloExecuteSync.groovy or 4_helloExecuteAsync.groovy script.
 *
 * Notice the parameters are found in the Call object, as opposed to the Request object.
 *
 * **************/

logger.info(Call.parameters.myname)
logger.info(Call.parameters.myage)

def response = "Hi, my name is " + Call.parameters.myname + " and my age is " + Call.parameters.myage + "."

return ['Content-Type': 'application/text', 'Content': response]
