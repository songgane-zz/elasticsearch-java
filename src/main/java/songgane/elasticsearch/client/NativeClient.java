package songgane.elasticsearch.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.client.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Creaing a native Elasticsearch Client
 *
 * Created by songgane on 2015. 12. 30..
 */
public class NativeClient {
    public Client getClient(String clusterName, String serverAddr, int port) throws UnknownHostException {
        Client client = null;

        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();

        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(serverAddr), port));

        return client;
    }

    public Client getClient(String clusterName, InetSocketTransportAddress... addresses) {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName).build();

        Client client = TransportClient.builder().settings(settings).build()
                .addTransportAddresses(addresses);

        return client;
    }
}
