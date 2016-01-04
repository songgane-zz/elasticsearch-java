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

    public SearchResponse matchQuery(String index, String type, String key, String value, int size) {
        QueryBuilder qb = org.elasticsearch.index.query.QueryBuilders.matchQuery(key, value);

        return client
                .prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
//                .setPostFilter(fb)
//                .addAggregation(
//                        AggregationBuilders.avg("cpu_average").field("value"))
                .setSize(size).execute().actionGet();
    }
}
