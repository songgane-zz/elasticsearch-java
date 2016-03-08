package songgane.elasticsearch.admin.indices;

import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by songgane on 2016. 1. 14..
 */
// https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-admin-indices.html
public class IndicesServiceImpl implements IndicesService {
    private Client client = null;

    public IndicesServiceImpl(Client client) {
        this.client = client;
    }

    public boolean isExists(String name) {
        IndicesExistsResponse response = client.admin().indices().prepareExists(name).get();
        return response.isExists();
    }

    public CreateIndexResponse create(String name) {
        return client.admin().indices().prepareCreate(name).get();
    }

    public DeleteIndexResponse delete(String name) {
        return client.admin().indices().prepareDelete(name).get();
    }

    public String[] list() {
        return client.admin().indices().prepareGetIndex().get().getIndices();
    }

    public CloseIndexResponse close(String name) {
        return client.admin().indices().prepareClose(name).get();
    }

    public OpenIndexResponse open(String name) {
        return client.admin().indices().prepareOpen(name).get();
    }

    public GetSettingsResponse getSettings(String[] indices) {
        return client.admin().indices()
                .prepareGetSettings(indices).get();
    }

    public UpdateSettingsResponse updateSetttings(String[] indices, Settings.Builder builder) {
        return client.admin().indices().prepareUpdateSettings(indices)
                .setSettings(builder)
                .get();
    }
}
