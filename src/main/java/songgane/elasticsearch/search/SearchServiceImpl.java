package songgane.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by songgane on 2016. 1. 4..
 */
public class SearchServiceImpl implements SearchService {
    private Client client;

    public SearchServiceImpl(Client client) {
        this.client = client;
    }

    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search.html
    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-query-dsl.html
    public SearchResponse search(String index, String type, QueryBuilder query, QueryBuilder filter, int from, int size, boolean explain) {
        return client
                .prepareSearch(index)
                .setTypes(type)
                .setQuery(query)
                .setPostFilter(filter)
                .setFrom(from)
                .setSize(size)
                .setExplain(explain)
                .execute()
                .actionGet();
    }
}
