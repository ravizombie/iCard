// WeatherWebResourceUser.groovy:
// 
// This example uses the Web Resource created by the WeatherWebResourceCreator script
// to generate a Weather report for a ZIP code.
// 
// In order to facilitate the WeatherExtendedUIModuleCreator example, use this code
// as the source of a Custom Object named "WeatherWebResourceUser," of type "Extended UI Module."
// Doing so will allow the WeatherExtendedUIModuleCreator example to specify this Custom Object as the content source.
// 
// Before moving on, go ahead an invoke this script:
// http://<axeda platform server>:<axeda platform port>/services/v1/rest/Scripto/execute/WeatherWebResourceUser?username=username&password=password&zipcode=02035
// 
// You should see a result similar to this:
// <weather>
//   <zipcode>02035</zipcode>
//   <city>Foxboro</city>
//   <state>MA</state>
//   <weatherStationCity>Norwood</weatherStationCity>
//   <description>Sunny</description>
//   <temperature>81</temperature>
//   <wind>CALM</wind>
//   <pressure>30.14S</pressure>
// </weather>

import static com.axeda.sdk.v2.dsl.Bridges.*
import com.axeda.sdk.v2.bridge.WebResourceBridge
import com.axeda.services.v2.Asset;

// When running these examples on the Axeda platform, the com.axeda.webresource.weather
// package will appear on the classpath only after running the WeatherWebResourceCreator example first.
//
// If viewing this example in your IDE, you will need to download the jar created for the Weather Web Resource from
// the Axeda platform after running the WeatherWebResourceCreator example, either in jar or Maven artifact form.
// Follow the Axeda platform help link to learn how to obtain this jar.
//
// This will allow you to uncomment the lines below marked with *** without seeing unresolved class errors for
// the com.axeda.webresource.weather package.

//import com.axeda.webresource.weather.WeatherSoap    // *** Uncomment when com.axeda.webresource.weather is on the classpath
//import com.axeda.webresource.weather.WeatherReturn  // *** Uncomment when com.axeda.webresource.weather is on the classpath
import groovy.xml.MarkupBuilder

def assetId = parameters.assetId // parameters.assetId is automatically passed to Custom Objects of type Extended UI Module
Asset theAsset
def theAssetZip
if (assetId) {
  theAsset = assetBridge.findById(assetId)
  theAssetZip = locationBridge.findById(theAsset.location.id)?.postalCode
}

def zipCode
if(parameters.zipcode && !parameters.zipcode?.isEmpty())
{
  zipCode = parameters.zipcode  // This script was called with the query parameter ?zipcode=XXXXX, so we'll use that.
}
else if (theAssetZip && !theAssetZip?.isEmpty()) {  
  zipCode = theAssetZip  // This script was called as the content source of an Extended UI Module, so we'll use the zip code of the Asset location
}
else {
  zipCode = "02035" // Default to Foxboro, MA
}

// We will now find the WebResource and use it to lookup the weather.

// create client endpoint using Web Resource name, service name, and endpoint name
//WeatherSoap endpoint = webResourceBridge.getClientEndpoint("Weather Service", "Weather", "WeatherSoap")  // *** Uncomment when com.axeda.webresource.weather is on the classpath

// initialize our XML response
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

// issue web service call to get weather by zipcode
//WeatherReturn weather = endpoint.getCityWeatherByZIP(zipCode)  // *** Uncomment when com.axeda.webresource.weather is on the classpath

// return weather response as XML
xml.weather() {
  zipcode(parameters.zipcode)
  city(weather.city)
  state(weather.state)
  weatherStationCity(weather.weatherStationCity)
  description(weather.description)
  temperature(weather.temperature)
  wind(weather.wind)
  pressure(weather.pressure)
}

return ["Content-Type": "application/xml","Content":writer.toString()]
