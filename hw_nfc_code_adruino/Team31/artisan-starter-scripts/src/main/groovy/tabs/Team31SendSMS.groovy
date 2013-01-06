import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static groovyx.net.http.Method.POST
import static groovyx.net.http.ContentType.XML
import groovyx.net.http.HTTPBuilder
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.scheme.Scheme
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import java.security.SecureRandom
import java.security.GeneralSecurityException
import javax.net.ssl.HttpsURLConnection
import org.apache.http.conn.ssl.X509HostnameVerifier
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLException

import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.ContentType
import com.axeda.sdk.v2.dsl.Bridges
import com.axeda.drm.sdk.scripto.Request

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 1/2/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */

def message = Request.parameters.message
def target = Request.parameters.target
try {
    def token = Bridges.customObjectBridge.execute("Team31GetOauthToken",[:])
    if (token instanceof Map) {
        // we have an error
        logger.info "Unable to get Oauth Access Token ${token?.Content}\n"
        return ["Content-Type":"application/json","Content":token?.Content]
    }
    def http = Bridges.customObjectBridge.execute("Team31CreateHttpRequest",[host:"https://api.att.com/rest/sms/2/"])

    http.request(POST, JSON) {
        headers = ["Accept": "application/json", "Content-Type":"application/json", "Authorization":"Bearer ${token}"]
        uri.path = 'messaging/outbox'
        requestContentType = ContentType.JSON
        body = """
{
    "Message":${message},
    "Address":"tel:${target}"
}         """
        response.success = {resp, json ->
            // we have to check for <faultstring> even if response is 200
            return ["Content-Type":"application/json","Content":json]
        }
        // response code is not 200 OK
        response.failure = {resp ->
            logger.info "FAILED with HTTP $resp.status:$resp.statusLine\n"
            def error = """
{
    "Error":"$resp.status:$resp.statusLine"
}
            """
            return ["Content-Type":"application/json","Content":error]
        }
    }
} catch(e) {
    logger.info "FAILED: Exception occurred: ${e.message}"
    return ["Content-Type":"application/json","Content":"{Exception: \"${e.message}\"}"]
}
