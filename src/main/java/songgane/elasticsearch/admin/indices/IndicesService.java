package songgane.elasticsearch.admin.indices;

import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.common.settings.Settings;

/**
 * Created by songgane on 2016. 1. 14..
 */
public interface IndicesService {
    boolean isExists(String name);

    CreateIndexResponse create(String name);

    DeleteIndexResponse delete(String name);

    String[] list();

    CloseIndexResponse close(String name);

    OpenIndexResponse open(String name);

    GetSettingsResponse getSettings(String[] indices);

    UpdateSettingsResponse updateSetttings(String[] indices, Settings.Builder builder);
}
