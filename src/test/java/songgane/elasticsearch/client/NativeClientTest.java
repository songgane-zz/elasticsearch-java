package songgane.elasticsearch.client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Native;
import java.util.Map;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class NativeClientTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;
    NativeClient nc = null;

    @Before
    public void before() {
        nc = new NativeClient();
    }

    @After
    public void after() {
    }

    @Test
    public void testNodeClient() throws IOException {
        Client client = nc.getNodeClient();

        Map<String, String> settings = client.settings().getAsMap();
        for (String key : settings.keySet()) {
            System.out.println(key + "=" + settings.get(key));
        }

        client.close();
    }

    @Test
    public void testTransportClient() throws IOException {
        Client client = nc.getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);

        Map<String, String> settings = client.settings().getAsMap();
        for (String key : settings.keySet()) {
            System.out.println(key + "=" + settings.get(key));
        }

        client.close();
    }
}
