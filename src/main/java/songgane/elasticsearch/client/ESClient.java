package songgane.elasticsearch.client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import songgane.elasticsearch.percolate.PercolatorServiceImpl;
import songgane.elasticsearch.search.SearchService;
import songgane.elasticsearch.search.SearchServiceImpl;
import songgane.elasticsearch.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by songgane on 2016. 1. 3..
 */
public class ESClient {
    public static void main(String[] args) throws IOException {
        // on startup

        Client client = new ConnectionUtil().getClient("songgane", "localhost", 9300);

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
        PercolateResponse response = new PercolatorServiceImpl().percolate(client, "customers", "customer", docBuilder);

        //Iterate over the results
        for (PercolateResponse.Match match : response) {
            //Handle the result which is the name of
            //the query in the percolator
            System.out.println(match.getIndex() + "," + match.getId());
        }

        SearchService search = new SearchServiceImpl(client);
        QueryBuilder query = QueryBuilders.matchQuery("full_name", "jaechang song");
        SearchResponse searchResponse = search.search("customers", "customer", query, null, 0, 10, true);
        System.out.println(searchResponse.toString());

        System.out.println("end");

        // on shutdown
        client.close();
    }
}
