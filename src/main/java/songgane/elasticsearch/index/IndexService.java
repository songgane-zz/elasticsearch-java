package songgane.elasticsearch.index;

/**
 * Created by songgane on 2016. 1. 14..
 */
public interface IndexService {
    boolean isExists(String name);

    void put(String name);

    void delete(String name);

    String[] list();

    void close(String name);

    void open(String name);
}
