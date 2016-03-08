package songgane.elasticsearch.search;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * Created by songgane on 2016. 1. 4..
 */
public interface SearchService {
    SearchResponse matchAll();
    SearchResponse search(String[] indices, String[] type, QueryBuilder query, QueryBuilder filter, AggregationBuilder[] aggregation, int from, int size, boolean explain);
    MultiSearchResponse multiSearch(SearchRequestBuilder[] queries);
}
