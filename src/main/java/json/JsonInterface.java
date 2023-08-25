package json;

import java.util.Map;

public interface JsonInterface {
    void readJson();        //lettura del json
    int size();         //dimensione della lettura
    String getConfiguration();      //stringa della configurazione letta
    Map<Object, Object> getConfigurationMap();      //ritorna la mappa della configurazione
}
