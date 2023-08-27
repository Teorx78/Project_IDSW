import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @BeforeAll
    public static void initJavaFX() {
        // Inizializza JavaFX Toolkit prima di eseguire i test
        JFXPanel jfxPanel = new JFXPanel();
    }

    @Test
    public void testMain() {
        // Esegui il test nel thread dell'applicazione JavaFX
        Platform.runLater(() -> {
            // Crea un'istanza della classe Main
            Main main = new Main();

            // Esegui il metodo start (il metodo principale dell'applicazione JavaFX)
            // Questo test si occupa principalmente di verificare che il metodo non generi eccezioni
            assertDoesNotThrow(() -> main.start(new Stage()));
        });
    }
}
