package sample.bridges

import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.services.v2.ExecutionResult
import com.axeda.services.v2.AsyncCustomObjectExecutionStatus

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 9/27/12
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */

def response = new StringBuilder()
def startString = "Here is our String!!! asldkjasldkjas"


ExecutionResult async = Bridges.customObjectBridge.initiateAsync("CustomObjectSyncCall",["inString":startString],60)


def UUID = UUID.fromString(async.succeeded.get(0).id)
response.append("Got Token: ${UUID}\n")

AsyncCustomObjectExecutionStatus status = Bridges.customObjectBridge.getAsyncStatus(UUID)

def start = System.currentTimeMillis()
while (!(status == AsyncCustomObjectExecutionStatus.FAILED || status == AsyncCustomObjectExecutionStatus.SUCCESS)) {
    response.append "Status: ${status}\n"
    status = Bridges.customObjectBridge.getAsyncStatus(UUID)
}
def duration = System.currentTimeMillis() - start

response.append "Final Status: ${status}, ${duration} ms\n"

// get the result
def asyncResult = Bridges.customObjectBridge.getAsyncResult(UUID) as String

response.append "Result: ${asyncResult}\n"

return ["Content-Type":"application/text","Content":response.toString()]
