package songgane.elasticsearch.document;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.script.Script;

import java.util.concurrent.ExecutionException;

/**
 * Created by songgane on 2016. 3. 8..
 */
public class DocumentServiceImpl implements DocumentService {
    private Client client = null;

    public DocumentServiceImpl(Client client) {
        this.client = client;

    }

    public IndexResponse index(String clusterName, String typeName, String id, XContentBuilder builder) {
        return client.prepareIndex(clusterName, typeName, id)
                .setSource(builder)
                .get();
    }

    public IndexResponse index(String clusterName, String typeName, String id, String json) {
        return client.prepareIndex(clusterName, typeName, id)
                .setSource(json)
                .get();
    }

    public GetResponse get(String clusterName, String typeName, String id) {
        return client.prepareGet(clusterName, typeName, id).get();
    }

    public DeleteResponse delete(String clusterName, String typeName, String id) {
        return client.prepareDelete(clusterName, typeName, id).get();
    }

    public UpdateResponse update(String clusterName, String typeName, String id, XContentBuilder builder) throws ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(clusterName);
        updateRequest.type(typeName);
        updateRequest.id(id);
        updateRequest.doc(builder);

        return client.update(updateRequest).get();

        //        return client.prepareUpdate(clusterName, typeName, id)
        //                .setSource(builder)
        //                .get();
    }

    public UpdateResponse update(String clusterName, String typeName, String id, Script script) {
        //        UpdateRequest updateRequest = new UpdateRequest(clusterName, typeName, id).script(script);
        //        return client.update(updateRequest).get();

        return client.prepareUpdate(clusterName, typeName, id)
                .setScript(script)
                .get();
    }

    public BulkResponse bulk(BulkRequestBuilder builder) {
        return client.prepareBulk().get();
    }
}
