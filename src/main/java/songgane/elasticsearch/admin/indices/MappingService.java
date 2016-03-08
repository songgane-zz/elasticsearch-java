package songgane.elasticsearch.admin.indices;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by songgane on 2016. 1. 14..
 */
public interface MappingService {
    PutMappingResponse put(String index, String typeName, XContentBuilder builder);

    PutMappingResponse put(String index, String typeName, String source);

    @Deprecated
    void delete(String index, String typeName);
}
