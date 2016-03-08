package songgane.elasticsearch.document;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class DocumentServiceTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;

    private final String INDEX_NAME = "test-index-2";
    private final String TYPE_NAME = "test-type-2";

    Client client = null;
    DocumentService doc = null;

    @Before
    public void before() throws UnknownHostException {
        client = new NativeClient().getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);
        doc = new DocumentServiceImpl(client);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testDocumentService() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("name").value("songgane").endObject();
        System.out.print(builder.string());

        IndexResponse index = doc.index(INDEX_NAME, TYPE_NAME, "10", builder);
        assertNotNull(index.isCreated());

        GetResponse get = doc.get(INDEX_NAME, TYPE_NAME, "10");
        System.out.print(get);

        doc.delete(INDEX_NAME, TYPE_NAME, "10");
        assertEquals(false, doc.get(INDEX_NAME, TYPE_NAME, "10").isExists());

        IndexResponse index2 = doc.index(INDEX_NAME, TYPE_NAME, "10", "{\"name\":\"songgane\"}");
        assertNotNull(index2.isCreated());

        doc.delete(INDEX_NAME, TYPE_NAME, "10");
        assertEquals(false, doc.get(INDEX_NAME, TYPE_NAME, "10").isExists());
    }

    @Test
    public void testBulk() {

    }
}
