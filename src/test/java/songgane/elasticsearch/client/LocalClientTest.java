package songgane.elasticsearch.client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;
import songgane.elasticsearch.client.http.LocalClient;

import java.io.IOException;

/**
 * Created by songgane on 2016. 3. 7..
 */
public class LocalClientTest {
    @Test
    public void testNativeClient() throws IOException {
        // on startup
        Client client = new LocalClient().getClient("songgane");

        // Get
        GetResponse res01 = client.prepareGet("customers", "customer", "1").get();
        System.out.println(res01.getSourceAsString());

        // on shutdown
        client.close();
    }
}
