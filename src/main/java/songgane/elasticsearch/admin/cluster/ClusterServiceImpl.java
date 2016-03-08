package songgane.elasticsearch.admin.cluster;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class ClusterServiceImpl implements ClusterService {
    private Client client = null;

    public ClusterServiceImpl(Client client) {
        this.client = client;
    }

    public ClusterHealthResponse getHealthStatus() {
        return client.admin().cluster().prepareHealth().get();
    }

    public ClusterHealthResponse getHealthStatus(String indexName) {
        return client.admin().cluster().prepareHealth(indexName).get();
    }
}
