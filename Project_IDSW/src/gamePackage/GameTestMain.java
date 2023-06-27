package gamePackage;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameTestMain extends Application{
	Game game;

	public static void main(String[] args) { launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		
		game = new Game(stage);
    	
    	Group root = game.getGroup();
    	StackPane stackPane = new StackPane();
    	stackPane.getChildren().add(root);
    	
    	game.startGame();
    	
		Scene scene = new Scene(stackPane, 550, 700);
		stage.setScene(scene);
		stage.setTitle("KLOTSKI");
		stage.setResizable(false);
		stage.show();
	}

}
