import static com.axeda.sdk.v2.dsl.Bridges.*

/****************
 * Hello Web Resource!
 *
 * 7_helloWebResource.groovy
 *
 *
 * Use the Web Resource utility to make a SOAP call.
 * Use the externalCredentialBridge to find credentials.
 *
 * **************/

 def response = []

try {

    def private_credential = externalCredentialBridge.find("MyPrivateCredential")

    def endpoint = webResourceBridge.getClientEndpoint("Asset", "Asset", "AssetService")

    webResourceBridge.setHttpBasicAuthCredentials(endpoint, credential.username, credential.password)



}
catch (Exception e){

logger.info(e.localizedMessage)
}

