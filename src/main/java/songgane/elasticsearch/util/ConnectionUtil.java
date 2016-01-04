package songgane.elasticsearch.util;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by songgane on 2016. 1. 3..
 */
public class ConnectionUtil {
    public Client getClient(String clusterName, String serverAddr, int port) {
        Client client = null;
        try {
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", clusterName).build();

            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(serverAddr), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return client;
    }
}
