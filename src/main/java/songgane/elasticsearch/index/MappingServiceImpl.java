package songgane.elasticsearch.mapping;

import org.elasticsearch.client.Client;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class MappingServiceImpl implements MappingService {
    private Client client = null;

    public MappingServiceImpl(Client client) {
        this.client = client;
    }

    public void put(String index, String typeName, String source) {
        client.admin().indices().preparePutMapping(index).setType(typeName).setSource(source).execute().actionGet();
    }

    public void delete(String index, String typeName) {
        //client.admin().indices().prepareDeleteMapping(index).setType(typeName).execute().actionGet();
    }
}
