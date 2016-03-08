package songgane.elasticsearch.admin.indices;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class MappingServiceImpl implements MappingService {
    private Client client = null;

    public MappingServiceImpl(Client client) {
        this.client = client;
    }

    public PutMappingResponse put(String index, String typeName, XContentBuilder builder) {
        return client.admin().indices().preparePutMapping(index).setType(typeName).setSource(builder).get();
    }

    public PutMappingResponse put(String index, String typeName, String source) {
        return client.admin().indices().preparePutMapping(index).setType(typeName).setSource(source).get();
    }

    public void delete(String index, String typeName) {
        //client.admin().indices().prepareDeleteMapping(index).setType(typeName).execute().actionGet();
    }
}
