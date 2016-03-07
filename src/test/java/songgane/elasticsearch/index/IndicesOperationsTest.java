package songgane.elasticsearch.index;

import org.elasticsearch.client.Client;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

/**
 * Created by songgane on 2016. 3. 7..
 */
public class IndicesOperationsTest {
    Client client;
    IndicesOperations index;
    String indexName = "index-test";

    @Before
    public void setUp() throws UnknownHostException {
        client = new NativeClient().getClient("songgane", "localhost", 9300);
        index = new IndicesOperations(client);
    }

    @Test
    public void testCreateIndex() {
        index.createIndex(indexName);
        assertEquals(true, index.checkIndexExists(indexName));

        index.closeIndex(indexName);
        assertEquals(true, index.checkIndexExists(indexName));

        index.openIndex(indexName);
        assertEquals(true, index.checkIndexExists(indexName));

        index.deleteIndex(indexName);
        assertEquals(false, index.checkIndexExists(indexName));
    }

    @Test
    public void testDeleteIndex() {
        index.deleteIndex(indexName);
        assertEquals(false, index.checkIndexExists(indexName));
    }
}
