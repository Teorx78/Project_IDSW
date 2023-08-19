import game.Board;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import support.Settings;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Board root = new Board("conf1");

        Image sfondo = new Image(Settings.BACKGROUND_IMAGE_PATH);

        BackgroundImage backgroundImage = new BackgroundImage(sfondo,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        HBox h = new HBox(root.createBoard());
        h.setAlignment(Pos.BASELINE_LEFT);

        h.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(h, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        scene.getStylesheets().addAll(Settings.CSS_BUTTON_FILE, Settings.CSS_LABEL_FILE);

        root.setScene(scene);

        root.startGame();

        stage.setTitle("KLOTSKI");
        stage.setScene(scene);

        Image image = new Image(Settings.CURSOR_IMAGE);
        stage.getScene().setCursor(new ImageCursor(image, image.getWidth() / 2.7, image.getHeight() / 2.7));

        stage.setResizable(false);
        stage.show();
    }
}
