package json;

import java.util.Map;

/**
 * L'interfaccia `JsonInterface` definisce i metodi necessari per la lettura di configurazioni JSON.
 */
public interface JsonInterface {

    /**
     * Legge una configurazione da una fonte JSON.
     */
    void readJson();

    /**
     * Restituisce la dimensione della configurazione letta.
     *
     * @return La dimensione della configurazione.
     */
    int size();

    /**
     * Restituisce il nome della configurazione letta.
     *
     * @return Il nome della configurazione.
     */
    String getConfiguration();

    /**
     * Restituisce una mappa rappresentante la configurazione letta.
     *
     * @return Una mappa che contiene la configurazione.
     */
    Map<Object, Object> getConfigurationMap();
}
