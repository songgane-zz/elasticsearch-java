package songgane.elasticsearch.search;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.net.UnknownHostException;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class SearchServiceTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;

    private final String INDEX_NAME = "test-index";
    private final String TYPE_NAME = "test-type";

    Client client = null;
    SearchService search = null;

    @Before
    public void before() throws UnknownHostException {
        client = new NativeClient().getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);
        search = new SearchServiceImpl(client);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testMatchAll() {
        SearchResponse response = search.matchAll();

        System.out.println(response);
    }

    @Test
    public void testSearch() {
        String[] indices = {INDEX_NAME, "mytest"};
        String[] type = {};

        // - query : _score
        //        . have to not only find matching documents, but also calculate how relevant each document is, which typically makes queries heavier than filters. Also, query results are not cachable.
        // match query
        // multi_match query
        // bool query
        MatchAllQueryBuilder query = matchAllQuery();

        // - filter :
        // . filter clauses—a simple list of the documents that match the filter—is quick to calculate and easy to cache in memory, using only 1 bit per document. These cached filters can be reused efficiently for subsequent requests.
        // . The goal of filters is to reduce the number of documents that have to be examined by the query.
        // term filter
        // terms filter
        // range filter : lt, lte, gt, gte
        // exists filter
        // missing filter
        // bool filter : must, must not, should
        RangeQueryBuilder filter = QueryBuilders.rangeQuery("price").from(4).to(5);

        SearchResponse response = search.search(indices, type, query, filter, null, 0, 100, false);

        System.out.println(response);
    }

    @Test
    public void testSearchnAggregation() {
        String[] indices = {INDEX_NAME};
        String[] type = {};

        MatchAllQueryBuilder query = matchAllQuery();

        AggregationBuilder[] aggs = {AggregationBuilders.terms("agg1").field("price")};

        SearchResponse response = search.search(indices, type, query, null, aggs, 0, 100, false);

        System.out.println(response);
    }

    @Test
    public void testMultiSearch() {
        SearchRequestBuilder srb1 = client
                .prepareSearch(INDEX_NAME).setQuery(QueryBuilders.queryStringQuery("Tester")).setSize(1);
        SearchRequestBuilder srb2 = client
                .prepareSearch("mytest").setQuery(QueryBuilders.matchQuery("text", "unicorn")).setSize(1);
        SearchRequestBuilder[]  bulders = {srb1, srb2};

        MultiSearchResponse sr = search.multiSearch(bulders);

        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().getTotalHits();
        }

        System.out.print(nbHits);
    }
}
