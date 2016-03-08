package songgane.elasticsearch.document;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.script.Script;

/**
 * Created by songgane on 2016. 3. 8..
 */
public interface DocumentService {
    //    Index API
    IndexResponse index(String clusterName, String typeName, String id, XContentBuilder builder);

    IndexResponse index(String clusterName, String typeName, String id, String json);

    //    Get API
    GetResponse get(String clusterName, String typeName, String id);

    //    Delete API
    DeleteResponse delete(String clusterName, String typeName, String id);

    //    Update API
    UpdateResponse update(String clusterName, String typeName, String id, XContentBuilder builder) throws Exception;

    UpdateResponse update(String clusterName, String typeName, String id, Script script);

    //    Bulk API
    BulkResponse bulk(BulkRequestBuilder builder);
}
