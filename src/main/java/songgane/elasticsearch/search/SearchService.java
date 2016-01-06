package songgane.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Created by songgane on 2016. 1. 4..
 */
public interface SearchService {
    SearchResponse search(String index, String type, QueryBuilder query, QueryBuilder filter, int from, int size, boolean explain);
}
