package songgane.elasticsearch.index;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class IndexServiceImpl implements IndexService {
    private Client client = null;

    public IndexServiceImpl(Client client) {
        this.client = client;
    }

    public boolean isExists(String name) {
        IndicesExistsResponse response = client.admin().indices().prepareExists(name).execute().actionGet();
        return response.isExists();
    }

    public void put(String name) {
        client.admin().indices().prepareCreate(name).execute().actionGet();
    }

    public void delete(String name) {
        client.admin().indices().prepareDelete(name).execute().actionGet();
    }

    public String[] list() {
        return client.admin().indices().prepareGetIndex().execute().actionGet().getIndices();
    }

    public void close(String name) {
        client.admin().indices().prepareClose(name).execute().actionGet();
    }

    public void open(String name) {
        client.admin().indices().prepareOpen(name).execute().actionGet();
    }
}
