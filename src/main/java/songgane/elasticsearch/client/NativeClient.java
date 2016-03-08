package songgane.elasticsearch.client;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import songgane.elasticsearch.client.http.HttpClientFactory;
import songgane.elasticsearch.percolate.PercolatorServiceImpl;
import songgane.elasticsearch.search.SearchService;
import songgane.elasticsearch.search.SearchServiceImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by songgane on 2016. 1. 3..
 */
public class NativeClient {
    private final String DEFAULT_PATH_HOME = "/apps/src/git/elasticsearch/distribution/tar/target";

    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/node-client.html
    public Client getNodeClient(String clusterName) throws UnknownHostException {
        // The client node is a real node, and should require everything that any other node requires.
        // The transport client should be light weight and shouldn't require any config etc.
        // So let's not require path.home for the transport client, and not load any config by default.
        // If the user needs to load a config file, then that should be explicitly specified.
        Node node = nodeBuilder().clusterName(clusterName).client(true).settings(Settings.builder()
                .put("path.home", DEFAULT_PATH_HOME).put("http.enabled", false)).node();

        return node.client();
    }

    public Client getNodeClient(String clusterName, String pathHome) throws UnknownHostException {
        Node node = nodeBuilder().clusterName(clusterName).client(true).settings(Settings.builder()
                .put("path.home", pathHome)).node();

        return node.client();
    }

    public Client getNodeClient() {
        return nodeBuilder().node().client();
    }

    public Client getLocalNodeClient() {
        return nodeBuilder().local(true).settings(Settings.builder()
                .put("path.home", DEFAULT_PATH_HOME).put("http.enabled", false)).node().client();
    }

    // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html
    public Client getTransportClient(String clusterName, String serverAddr, int port) throws UnknownHostException {
        Client client = null;

        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();

        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(serverAddr), port));

        return client;
    }

    public Client getTransportClient(String clusterName, InetSocketTransportAddress... addresses) {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();

        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddresses(addresses);

        return client;
    }
}
