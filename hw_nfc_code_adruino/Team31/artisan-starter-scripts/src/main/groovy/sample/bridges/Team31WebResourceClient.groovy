package sample.bridges

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 9/27/12
 * Time: 11:26 PM
 * To change this template use File | Settings | File Templates.
 */


import com.axeda.sdk.v2.bridge.WebResourceBridge
import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.webresource.weatherkh.WeatherSoap
import com.axeda.webresource.weatherkh.ForecastReturn

WebResourceBridge bridge = Bridges.webResourceBridge
WeatherSoap endpoint = bridge.getClientEndpoint("Weather ServiceKH", "Weather", "WeatherSoap12")

ForecastReturn forecast = endpoint.getCityForecastByZIP("02048")

