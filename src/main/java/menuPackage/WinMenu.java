package menuPackage;

//layout
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//immagini
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import support.Settings;
//timer
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class WinMenu extends Menu {
    public WinMenu(StackPane root)      //crea una schermata di vittoria che rimane a schermo per 5 secondi prima ri riportare l'utente al menu principale
    {
        super(root);
        Image newImage = new Image(Settings.TITLE_WIN_IMAGE_PATH, 550, 250, true, true);
        ImageView winMenuTitle = new ImageView(newImage);

        VBox menuV = new VBox(30);
        menuV.getChildren().addAll(winMenuTitle);
        menuV.setAlignment(Pos.BASELINE_CENTER);
        BorderPane.setMargin(menuV, new Insets(150,0,0,0));

        menu.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
        menu.setTop(menuV);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            int n = root.getChildren().size();
            root.getChildren().remove(n-2, n);
            System.out.println("back to main menu");
        });
        pause.play();
    }


}
