package songgane.elasticsearch.client.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class HttpClientFactory {
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        if (cm == null) {
            try {
                // SSL 호출을 위해 인증서를 설치하거나, 임의접근이 가능하도록 처리해야 한다.
                SSLContextBuilder builder = new SSLContextBuilder();
                builder.loadTrustMaterial(null, new TrustStrategy() {
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                });

                builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(builder.build(),
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", scsf)
                        .build();

                cm = new PoolingHttpClientConnectionManager(registry);

                // @CHECKME : 설정값에 대한 확인이 필요함
                cm.setDefaultMaxPerRoute(20);
                cm.setMaxTotal(128);

                IdleConnectionMonitorThread idleConnMonitor = new IdleConnectionMonitorThread(cm);
                idleConnMonitor.setName("IdleConnectionMonitorThread");
                idleConnMonitor.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the pooled client.
     *
     * @return the pooled client
     */
    public static CloseableHttpClient getPooledClient() {
        int timeout = 5;
        int retry = 3;

        return getPooledClient(timeout, retry);
    }

    /**
     * Gets the pooled client.
     *
     * @param second the second
     * @param retry  the retry
     * @return the pooled client
     */
    public static CloseableHttpClient getPooledClient(int second, int retry) {
        // Builder for CloseableHttpClient instances.
        HttpClientBuilder builder = HttpClientBuilder.create();

        // https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/client/config/RequestConfig.html
        //
        // A timeout value of zero is interpreted as an infinite timeout.
        // A negative value is interpreted as undefined (system default).
        if (second > 0) {
            int ms = second * 1000;
            RequestConfig config = RequestConfig.custom().setConnectTimeout(ms)
                    .setConnectionRequestTimeout(ms).setSocketTimeout(ms).build();

            builder.setDefaultRequestConfig(config);
        }

        if (retry > 0) {
            DefaultHttpRequestRetryHandler handler = new DefaultHttpRequestRetryHandler(retry, true);

            builder.setRetryHandler(handler);
        }

        return builder.setConnectionManager(cm).build();
    }
}
