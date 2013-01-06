import static com.axeda.sdk.v2.dsl.Bridges.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import com.axeda.services.v2.ExternalCredential

/****************
 * Hello Credentials!
 *
 * 6_helloCredentials.groovy
 *
 * Use the externalCredentialBridge to store and then to find credentials.
 * The find method returns a map that contains all stored information - no need
 * to find first the username then the password.
 *
 * **************/

 def response = []

try {

    ExternalCredential externalCredential = new ExternalCredential()
    externalCredential.name = "MyPrivateCredential"
    externalCredential.username = "my_private_username"
    externalCredential.password = "my_private_password"

    externalCredentialBridge.create(externalCredential)

    def private_credential = externalCredentialBridge.find("MyPrivateCredential")

    // authentication tokens (username + password)
    def auth_tokens = [username: private_credential.username, password: private_credential.password]

    // demonstrate using HTTPBuilder with credentials retrieved from external credential bridge
    def script = "3_helloDataItems"
    def http = new HTTPBuilder( 'http://192.168.1.160/services/v1/rest/Scripto/execute/'+script )

    http.request (GET, JSON) {
      //uri.path = '/' + script
      uri.query = auth_tokens

      //println uri.path
      //println uri.query

      response.success = { resp, json ->
        logger.info "probably did get a response."
      }

      response.failure = { resp ->
        logger.info "probably did not get a response."
      }

    }
}
catch (Exception e){

logger.info(e.localizedMessage)
}

