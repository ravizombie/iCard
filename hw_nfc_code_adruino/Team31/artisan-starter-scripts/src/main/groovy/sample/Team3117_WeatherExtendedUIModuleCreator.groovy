// WeatherExtendedUIModuleCreator.groovy:
// 
// This example will be used to create an Extended UI Module that specifies
// the "WeatherWebResourceUser" Custom Object (created in the previous example) as the content source.
// 
// After running this example, the resulting Extended UI Module, named "The Weather Ext UI Module,"
// can be added to the Asset Dashboard, resulting in a Weather report for the ZIP code associated with the
// Location of an Asset.  To add it to the Default Asset Dashboard Layout, from the Axeda Platform UI, follow
// Configuration -> Preferences -> Default Layout and add it using the instructions on that page.
// Once this is done, you will see the newly added Extended UI Module when you click on an Asset within the Service
// tab of the Axeda Platform.

import static com.axeda.sdk.v2.dsl.Bridges.*

import com.axeda.services.v2.CustomObjectCriteria;
import com.axeda.services.v2.CustomObjectModuleProvider;
import com.axeda.services.v2.CustomObjectReference;
import com.axeda.services.v2.ExtendedUIModule;
import com.axeda.services.v2.ExtendedUIModuleProvider;
import com.axeda.services.v2.ExtendedUIModuleType;

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

// First find the WeatherWebResource
CustomObjectCriteria coCrit = new CustomObjectCriteria(name: "WeatherWebResourceUser")  // Refers to the WeatherWebResourceUser Custom Object of type "Extended UI Module."
CustomObjectReference weatherCustomObject = customObjectBridge.find(coCrit)?.customObjects[0]

logger.info("CustomObjectReference ref member is: ${weatherCustomObject?.label}")

CustomObjectModuleProvider weatherCOMProvider = new CustomObjectModuleProvider(customObject: weatherCustomObject) 
weatherCOMProvider.setType(ExtendedUIModuleType.CUSTOM_OBJECT)

// title has a 25 character max length
ExtendedUIModule weatherEUIM = new ExtendedUIModule(title: "The Weather Ext UI Module", height: 100, moduleProvider: weatherCOMProvider)

def result = extendedUIModuleBridge.create(weatherEUIM)

if (result.successful) {
  return result
}
else {
  return buildErrorResponse(result) 
}
