package songgane.elasticsearch.client.http;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import java.net.UnknownHostException;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Created by songgane on 2016. 1. 14..
 */
public class LocalClient {
    public Client getClient(String clusterName) throws UnknownHostException {
        // The client node is a real node, and should require everything that any other node requires.
        // The transport client should be light weight and shouldn't require any config etc.
        // So let's not require path.home for the transport client, and not load any config by default.
        // If the user needs to load a config file, then that should be explicitly specified.
        Node node = nodeBuilder().clusterName(clusterName).client(true).settings(Settings.builder()
                .put("path.home", "/apps/src/git/elasticsearch/distribution/tar/target")).node();

        return node.client();
    }
}
