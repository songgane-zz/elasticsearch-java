package songgane.elasticsearch.client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;
import songgane.elasticsearch.index.IndexService;
import songgane.elasticsearch.index.IndexServiceImpl;
import songgane.elasticsearch.percolate.PercolatorServiceImpl;

import java.io.IOException;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class NativeClientTest {

    @Test
    public void testNativeClient() throws IOException {
        // on startup

        Client client = new NativeClient().getClient("songgane", "songgane-mac", 9300);
        //Client client = new LocalClient().getClient("elasticsearch");

        // Get
        GetResponse res01 = client.prepareGet("customers", "customer", "1").get();
        System.out.println(res01.getSourceAsString());

        // Percolator
        XContentBuilder docBuilder = XContentFactory.jsonBuilder().startObject();
        docBuilder.field("doc").startObject(); //This is needed to designate the document
        docBuilder.field("full_name", "jaechang song");
        docBuilder.endObject(); //End of the doc field
        docBuilder.endObject(); //End of the JSON root object

        //Percolate
        PercolateResponse response = new PercolatorServiceImpl(client).percolate("customers", "customer", docBuilder);

        //Iterate over the results
        for (PercolateResponse.Match match : response) {
            //Handle the result which is the name of
            //the query in the percolator
            System.out.println(match.getIndex() + "," + match.getId());
        }

        IndexService idxSvc = new IndexServiceImpl(client);
        System.out.println(idxSvc.isExists("access-test"));

        idxSvc.put("ttt");
        String[] indices = idxSvc.list();
        if (indices != null && indices.length > 0) {
            for (String index : indices) {
                System.out.println(index);
            }
        }

        idxSvc.delete("ttt");
        indices = idxSvc.list();
        if (indices != null && indices.length > 0) {
            for (String index : indices) {
                System.out.println(index);
            }
        }

        System.out.println("end");

        // on shutdown
        client.close();
    }
}
