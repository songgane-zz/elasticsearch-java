package songgane.elasticsearch.admin.cluster;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;

/**
 * Created by songgane on 2016. 3. 8..
 */
public interface ClusterService {
    ClusterHealthResponse getHealthStatus();

    ClusterHealthResponse getHealthStatus(String indexName);
}
