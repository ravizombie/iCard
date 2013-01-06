// WeatherWebResourceCreator.groovy:
// 
// This example will use the WebResourceBridge to
// create a Web Resource around a Weather web service available on the Internet.
//
// Use this code as the source of a Custom Object named "WeatherWebResourceCreator" of type "Action."
// 
// Invoke it: http://<axeda platform server>:<axeda platform port>/services/v1/rest/Scripto/execute/WeatherWebResourceCreator?username=username&password=password
// 
// Once invoked, move onto the next example, the WeatherWebResourceUser.

import com.axeda.sdk.v2.bridge.*
import com.axeda.services.v2.*

WebResourceBridge bridge = bridges.getWebResourceBridge()

def buildErrorResponse = {
  def buffer = new StringBuffer()
  it.failures.each { failure ->
    buffer << "Ref: ${failure.ref}\n"
    buffer << "Message: ${failure.getMessage().replaceAll(/\$\{target\}/, failure.getSourceOfFailure())}\n"
    buffer << "Source Of Failure: ${failure.sourceOfFailure}\n"
    buffer << "Code: ${failure.code}\n\n"
  }
  return buffer.toString()
}

// create web resource
def resource = new WebResource()
resource.name = "Weather Service"
resource.description = "Weather Web Service"
resource.packageNamespace = "com.axeda.webresource.weather"
resource.wsdlURLs.add("http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL")

ExecutionResult result = bridge.create(resource)
if(result.isSuccessful())
{
  // return the WebResource object serialized to XML
  return bridge.find(result.getSucceeded().get(0).getId())
}
else
{
  // if the create failed, just return the result - we want to know why it failed
  return buildErrorResponse(result);
}
