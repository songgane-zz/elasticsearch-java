package songgane.elasticsearch.percolate;

import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by songgane on 2016. 1. 3..
 */
public interface PercolatorService {
    public PercolateResponse percolate(Client client, String index, String type, XContentBuilder docBuilder);

    public PercolateResponse percolate(Client client, String index, String type, String source);
}
