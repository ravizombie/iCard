import static groovyx.net.http.Method.POST
import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.ContentType
import com.axeda.sdk.v2.dsl.Bridges

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 1/2/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
def client_id = "64fe7074addd391a11f1cc6504e8b44f"
def client_secret = "29a5ac19fb268a5b"
def scope = "MMS,SMS,SPEECH,WAP"

def http = Bridges.customObjectBridge.execute("Team31CreateHttpRequest",[host:"https://api.att.com/oauth/"])
try {
    http.request(POST, JSON) {
        headers = ["Accept": "application/json", "Content-Type":"application/x-www-form-urlencoded"]
        uri.path = 'token'
        requestContentType = ContentType.URLENC
        body = ["client_id": client_id, "client_secret": client_secret, "scope": scope, "grant_type": "client_credentials"]
        response.success = {resp, json ->
            // we have to check for <faultstring> even if response is 200
            logger.info "Found JSON ${json}"
            return json.access_token
        }
        // response code is not 200 OK
        response.failure = {resp ->
            logger.info "FAILED with HTTP $resp.status:$resp.statusLine\n"
            return ["Content-Type":"application/json","Content":"[{Nothing: [\"Zip\",\"Zero\"]}]"]
        }
    }
} catch(e) {
    logger.info "FAILED: Exception occurred: ${e.message}"
    return ["Content-Type":"application/json","Content":"{Exception: \"${e.message}\"}"]

}


