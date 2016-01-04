package songgane.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;

/**
 * Created by songgane on 2016. 1. 4..
 */
public interface SearchService {
    SearchResponse matchQuery(String index, String type, String key, String value, int size);
}
