package songgane.elasticsearch.admin.cluster;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import songgane.elasticsearch.client.NativeClient;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class ClusterServiceTest {
    private final String CLUSTER_NAME = "songgane";
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 9300;

    private final String INDEX_NAME = "test-index";

    Client client = null;
    ClusterService cluster = null;

    @Before
    public void before() throws UnknownHostException {
        client = new NativeClient().getTransportClient(CLUSTER_NAME, SERVER_ADDR, SERVER_PORT);
        cluster = new ClusterServiceImpl(client);
    }

    @After
    public void after() {
        client.close();
    }

    @Test
    public void testClusterService() {
        ClusterHealthResponse healths = cluster.getHealthStatus();

        String clusterName = healths.getClusterName();
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        int numberOfNodes = healths.getNumberOfNodes();

        System.out.println(clusterName + "::" + numberOfDataNodes + "::" + numberOfNodes);

        Map<String, ClusterIndexHealth> cluster = healths.getIndices();
        for (String indexName : cluster.keySet()) {
            ClusterIndexHealth health = cluster.get(indexName);

            String index = health.getIndex();
            int numberOfShards = health.getNumberOfShards();
            int numberOfReplicas = health.getNumberOfReplicas();
            ClusterHealthStatus status = health.getStatus();

            System.out.println(index + "::" + numberOfShards + "::" + numberOfReplicas + "::" + status);
        }
    }

    @Test
    public void testWaitForClusterStatus() {
        ClusterHealthResponse response = client.admin().cluster().prepareHealth()
                .setWaitForGreenStatus()
                .setTimeout(TimeValue.timeValueSeconds(2))
                .get();

        System.out.print(response.getIndices());

        ClusterHealthStatus status = response.getIndices().get(INDEX_NAME).getStatus();
        if (!status.equals(ClusterHealthStatus.GREEN)) {
            throw new RuntimeException("Index is in " + status + " state");
        }
    }
}
