package songgane.elasticsearch.percolate;

import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by songgane on 2016. 1. 3..
 */
public class PercolatorServiceImpl implements PercolatorService {

    public PercolateResponse percolate(Client client, String index, String type, XContentBuilder docBuilder) {
        return client.preparePercolate()
                .setIndices(index)
                .setDocumentType(type)
                .setSource(docBuilder).execute().actionGet();
    }
}
