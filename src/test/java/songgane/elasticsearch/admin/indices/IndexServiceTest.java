package songgane.elasticsearch.admin.indices;

import org.elasticsearch.client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class IndexServiceTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;

    private final String INDEX_NAME = "test-index-2";
    private final String TYPE_NAME = "test-type-2";

    Client client = null;
    IndicesService index = null;

    @Before
    public void before() throws UnknownHostException {
        client = new NativeClient().getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);
        index = new IndicesServiceImpl(client);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testIndexService() {
        index.create(INDEX_NAME);
        assertEquals(true, index.isExists(INDEX_NAME));

        index.close(INDEX_NAME);
        assertEquals(true, index.isExists(INDEX_NAME));

        index.open(INDEX_NAME);
        assertEquals(true, index.isExists(INDEX_NAME));

        index.delete(INDEX_NAME);
        assertEquals(false, index.isExists(INDEX_NAME));
    }
}
