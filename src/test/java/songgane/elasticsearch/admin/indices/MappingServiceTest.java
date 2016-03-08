package songgane.elasticsearch.admin.indices;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
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

/**
 * Created by songgane on 2016. 3. 8..
 */
public class MappingServiceTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;

    private final String INDEX_NAME = "test-index-2";
    private final String TYPE_NAME = "test-type-2";

    Client client = null;
    IndicesService index = null;
    MappingService mapping = null;

    @Before
    public void before() throws UnknownHostException {
        client = new NativeClient().getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);
        index = new IndicesServiceImpl(client);
        mapping = new MappingServiceImpl(client);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testMappingService() {
        index.create(INDEX_NAME);
        assertEquals(true, index.isExists(INDEX_NAME));

        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder().
                    startObject().
                    field(TYPE_NAME).
                    startObject().
                    field("properties").
                    startObject().
                    field("nested1").
                    startObject().
                    field("type").
                    value("nested").
                    endObject().
                    endObject().
                    endObject().
                    endObject();
            System.out.println(builder.string());
            PutMappingResponse response = mapping.put(INDEX_NAME, TYPE_NAME, builder);

            if (!response.isAcknowledged()) {
                System.out.println("Something strange happens");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to create mapping");
        }

        GetMappingsResponse res = client.admin().indices().prepareGetMappings().get();
        System.out.print(res.mappings());
        assertEquals(true, res.mappings().get(INDEX_NAME).containsKey(TYPE_NAME));

        index.delete(INDEX_NAME);
        assertEquals(false, index.isExists(INDEX_NAME));
    }
}
