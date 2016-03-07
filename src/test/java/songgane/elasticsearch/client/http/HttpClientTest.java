package songgane.elasticsearch.client.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by songgane on 2016. 3. 7..
 */
public class HttpClientTest {
    @Before
    public void setup() {

    }

    @Test
    public void testHttpClient() {
        CloseableHttpClient client = null;

        try {
            client = HttpClientFactory.getPooledClient();
            HttpGet method = new HttpGet("http://localhost:9200/customers/customer/1");
            CloseableHttpResponse response = client.execute(method);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + response.getStatusLine());
            } else {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                System.out.println(responseBody);
            }
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
