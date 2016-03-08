package songgane.elasticsearch.search;

import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * Created by songgane on 2016. 1. 4..
 */
public class SearchServiceImpl implements SearchService {
    private Client client;

    public SearchServiceImpl(Client client) {
        this.client = client;
    }

    public SearchResponse matchAll() {
        return client.prepareSearch().execute().actionGet();
    }

    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search.html
    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-query-dsl.html
    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search-aggs.html
    public SearchResponse search(String[] indices, String[] type, QueryBuilder query, QueryBuilder filter, AggregationBuilder[] aggregations, int from, int size, boolean explain) {
        SearchRequestBuilder builder = client.prepareSearch(indices);
        builder.setTypes(type)
                .setQuery(query)
                .setPostFilter(filter);

        if (aggregations != null && aggregations.length > 0) {
            for (AggregationBuilder ab : aggregations) {
                builder.addAggregation(ab);
            }
        }

        return builder
                .setFrom(from)
                .setSize(size)
                .setExplain(explain)
                .execute()
                .actionGet();
    }

    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search-msearch.html
    public MultiSearchResponse multiSearch(SearchRequestBuilder[] queries) {
        MultiSearchRequestBuilder builder = client.prepareMultiSearch();

        if (queries != null && queries.length > 0) {
            for (SearchRequestBuilder sb : queries) {
                builder.add(sb);
            }
        }

        return builder
                .execute()
                .actionGet();
    }

}
