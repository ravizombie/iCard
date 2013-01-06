import com.axeda.services.v2.ExecutionResult
import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.drm.sdk.customobject.Call
/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 9/27/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */

def maxTimes = 10

int currentTime = 0
if (Call.parameters.currentTime) {
    currentTime = Call.parameters.currentTime as int
}

if (currentTime >= maxTimes) {
    logger.info "Done"
} else {
    logger.info "Count ${currentTime}"
    currentTime++
    Bridges.customObjectBridge.initiateAsync("CustomObjectAsyncCall",["currentTime":currentTime],60)
}

return ["Content-Type":"text/plain","Content":"Hello"]
