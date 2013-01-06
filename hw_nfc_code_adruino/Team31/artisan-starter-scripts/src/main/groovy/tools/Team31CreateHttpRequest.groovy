import groovyx.net.http.HTTPBuilder
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.scheme.Scheme
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import java.security.SecureRandom
import java.security.GeneralSecurityException
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import org.apache.http.conn.ssl.X509HostnameVerifier
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLException
import com.axeda.drm.sdk.customobject.Call

/**
 * Created with IntelliJ IDEA.
 * User: kholbrook
 * Date: 1/2/13
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */

return createHttp(Call.parameters)

public HTTPBuilder createHttp(Map serverInfo) {
    URL aURL = new URL(serverInfo.host);
    def aPort = 443;
    if (aURL.getPort() != -1) {
        aPort = aURL.getPort();
    }

    def http = new HTTPBuilder(serverInfo.host)

//    SSLSocketFactory sslSocketFactory = new SSLSocketFactory();
//    sslSocketFactory.setHostnameVerifier(new LeniantHostnameVerifier());
//    http.client.connectionManager.schemeRegistry.register(
//            new Scheme("https", sslSocketFactory, 443)
//    )

    if (serverInfo.skipAuth == "true") {
        logger.info "Skipping auth"
        http.client.params.setBooleanParameter 'http.protocol.handle-authentication', false
    }

    http.client.params.setBooleanParameter 'http.protocol.expect-continue', false
    return http
}


//def trustAllHttpsCertificates() {
//    SSLContext context;
//
//    // Create a trust manager that does not validate certificate chains
//    TrustManager[] _trustManagers = new TrustManager[1];
//    _trustManagers[0] = new LenientTrustManager();
//    // Install the all-trusting trust manager:
//    try {
//        context = SSLContext.getInstance("SSL");
//        context.init(null, _trustManagers, new SecureRandom());
//    } catch (GeneralSecurityException gse) {
//        throw new IllegalStateException(gse.getMessage());
//    } // catch
//    HttpsURLConnection.setDefaultSSLSocketFactory(context.
//            getSocketFactory());
//}
//
//
//
///**
// * This class allow any X509 certificates to be used to authenticate the
// * remote side of a secure socket, including self-signed certificates.
// *
// */
//class LenientTrustManager implements X509TrustManager {
//
//    /**
//     * Empty array of certificate authority certificates.
//     */
//    private static final X509Certificate[] _AcceptedIssuers =
//        new X509Certificate[0];
//
//    /**
//     * Always trust for client SSL chain peer certificate
//     * chain with any authType authentication types.
//     *
//     * @param chain the peer certificate chain.
//     * @param authType the authentication type based on the client
//     * certificate.
//     */
//    public void checkClientTrusted(X509Certificate[] chain,
//                                   String authType) {
//    } // checkClientTrusted
//
//    /**
//     * Always trust for server SSL chain peer certificate
//     * chain with any authType exchange algorithm types.
//     *
//     * @param chain the peer certificate chain.
//     * @param authType the key exchange algorithm used.
//     */
//    public void checkServerTrusted(X509Certificate[] chain,
//                                   String authType) {
//    } // checkServerTrusted
//
//    /**
//     * Return an empty array of certificate authority certificates which
//     * are trusted for authenticating peers.
//     *
//     * @return a empty array of issuer certificates.
//     */
//    public X509Certificate[] getAcceptedIssuers() {
//        return (_AcceptedIssuers);
//    } // getAcceptedIssuers
//
//
//} // FakeX509TrustManager
//
//public class LeniantHostnameVerifier implements X509HostnameVerifier {
//
//
//    @Override
//    public boolean verify(String s, SSLSession sslSession) {
//        return true;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void verify(String s, SSLSocket sslSocket) throws IOException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void verify(String s, X509Certificate x509Certificate) throws SSLException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void verify(String s, String[] strings, String[] strings1) throws SSLException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//}
