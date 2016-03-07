package songgane.elasticsearch.mapping;

/**
 * Created by songgane on 2016. 1. 14..
 */
public interface MappingService {
    void put(String index, String typeName, String source);

    void delete(String index, String typeName);
}
