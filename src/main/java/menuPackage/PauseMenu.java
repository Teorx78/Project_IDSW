package menuPackage;

import game.Board;
import game.Reset;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

//resources
import java.io.*;
import java.util.Scanner;

//immagini e colore
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//pulsanti
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//altro

import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;
import support.DuplicateMap;
import support.Settings;
public class PauseMenu extends Menu
{
	protected static Button resume   = new Button(null),
							restart  = new Button(null),
							save     = new Button(null),
							mainMenu = new Button(null);
	public PauseMenu(StackPane root, MediaPlayer song){
		super(root);
		Image newImage =new Image(Settings.TITLE_PAUSE_IMAGE_PATH, 360, 150, true, true);
		ImageView pauseTitle = new ImageView(newImage);

		VBox menuV = new VBox(5);
		menuV.getChildren().addAll(pauseTitle, getResume(),getRestart(),getLoad(),getSave(),getMainMenu());
		menuV.setAlignment(Pos.BASELINE_CENTER);

		HBox menuH = new HBox(getVolume(root, song));
		menuH.setAlignment(Pos.BASELINE_RIGHT);
		BorderPane.setMargin(menuH, new Insets(0, 80, 30, 0));//up right down left
		menu.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
		menu.setTop(menuV);
		menu.setBottom(menuH);
		//menu.setMaxSize(550, 700);
	}
	public void useResume (Button pauseButton){
		EventHandler<ActionEvent> event = e -> {
			int n =root.getChildren().size();
			if(root.getChildren().get(n-1)==menu)
			{
				root.getChildren().remove(menu);
				pauseButton.setCancelButton(true);
				resume.setCancelButton(false);
				System.out.println("resumed");
			}
			else
			{
				root.getChildren().remove(n-1);
				System.out.println("slider closed");
			}
		};
		resume.setOnAction(event);
	}
	public Button getResume	 () {
		resume.setId("Resume");
		resume.setText("Resume");
		create_button(resume, 180, 75);
		//
		return resume;
	}
	public Button getRestart () {
		restart.setId("Restart");
		restart.setText("Restart");
		create_button(restart, 180, 75);
		restart.setOnAction(Board.getResetEvent(root));
		return restart;
	}
	public Button getSave	 () {
		save.setId("Save");
		save.setText("Save");
		create_button(save, 180, 75);
		EventHandler<ActionEvent> event = e -> {
//String saveFilePath = Settings.LAST_GAME;
			/*String coordinates = "resources/prova.json";

			try (FileReader reader = new FileReader(coordinates);
				 PrintWriter writer = new PrintWriter(Settings.LAST_GAME)) {
				Scanner in = new Scanner(reader);
				while (in.hasNextLine())			//copia un file json
				{
					String line=in.nextLine();
					writer.println(line);

				}
				in.close();


				System.out.println("Saved");
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			System.out.println("saving");
		};
		save.setOnAction(event);
		return save;
	}
	public Button getMainMenu() {
		mainMenu.setId("Main Menu");
		mainMenu.setText("Main Menu");
		create_button(mainMenu, 180, 75);
		EventHandler<ActionEvent> event = e -> {
			root.getChildren().remove(root.getChildren().size()-1);
			root.getChildren().remove(root.getChildren().size()-1);
			System.out.println("back to main menu");
		};
		mainMenu.setOnAction(event);
		return mainMenu;
	}

	/*public PauseMenu(StackPane gameRoot, Button pauseButton, HBox gameInBackground, MediaPlayer mediaPlayer) throws FileNotFoundException
	{

		Save.setOnAction    (	new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//String saveFilePath = Settings.LAST_GAME;
				String coordinates = "resources/prova.json";

				try (FileReader reader = new FileReader(coordinates);
					 PrintWriter writer = new PrintWriter(Settings.LAST_GAME)) {
					Scanner in = new Scanner(reader);
					while (in.hasNextLine())			//copia un file json
					{
						String line=in.nextLine();
						writer.println(line);

					}
					in.close();


					System.out.println("Saved");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		});
		MainMenu.setOnAction(	new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				gameRoot.getChildren().remove(menu);
				gameRoot.getChildren().remove(gameInBackground);
			}
		});

	}*/
}