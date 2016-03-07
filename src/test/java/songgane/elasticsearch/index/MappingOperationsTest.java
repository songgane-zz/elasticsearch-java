package songgane.elasticsearch.index;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.io.IOException;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertEquals;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class MappingOperationsTest {
    Client client;
    IndicesOperations index;
    String indexName = "mapping-test";
    String typeName = "mytype";

    @Before
    public void setUp() throws UnknownHostException {
        client = new NativeClient().getClient("songgane", "localhost", 9300);
        index = new IndicesOperations(client);
    }

    @Test
    public void testPutMapping() {
        index.createIndex(indexName);
        assertEquals(true, index.checkIndexExists(indexName));

        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder().
                    startObject().
                    field("type1").
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
            PutMappingResponse response = client.admin().indices().preparePutMapping(indexName).setType(typeName).setSource(builder).execute().actionGet();
            if (!response.isAcknowledged()) {
                System.out.println("Something strange happens");
            }
        } catch (IOException e) {
            System.out.println("Unable to create mapping");
        }
        client.admin().indices().prepareGetMappings().get();
        GetMappingsResponse res = client.admin().indices().prepareGetMappings().get();
        System.out.print(res);

        index.deleteIndex(indexName);
        assertEquals(false, index.checkIndexExists(indexName));
    }

    @Test
    public void testDeleteIndex() {
        index.deleteIndex(indexName);
        assertEquals(false, index.checkIndexExists(indexName));
    }
}
