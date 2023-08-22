package json;

import java.util.Map;

public interface JsonInterface {
    void readJson();
    int size();
    String getConfiguration();
    Map<Object, Object> getConfigurationMap();
}
