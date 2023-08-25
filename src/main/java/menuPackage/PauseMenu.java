package menuPackage;

import game.Board;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;
import json.JsonSave;
import support.Settings;

public class PauseMenu extends Menu
{
	private static final Button resume   = new Button(null),
								save     = new Button(null),
								mainMenu = new Button(null);

	public PauseMenu(StackPane root, MediaPlayer song)	//costruisce il menu da visualizzare quando viene messo il gioco in pausa
	{
		super(root);
		Image newImage =new Image(Settings.TITLE_PAUSE_IMAGE_PATH, 360, 150, true, true);
		ImageView pauseTitle = new ImageView(newImage);

		VBox menuV = new VBox(30);
		menuV.getChildren().addAll(pauseTitle, getResume(),getSave(),getMainMenu());
		menuV.setAlignment(Pos.BASELINE_CENTER);

		HBox menuH = new HBox(getVolume(root, song));
		menuH.setAlignment(Pos.BASELINE_RIGHT);
		BorderPane.setMargin(menuH, new Insets(0, 80, 30, 0));//up right down left
		menu.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3);");
		menu.setTop(menuV);
		menu.setBottom(menuH);
		//menu.setMaxSize(550, 700);
	}
	public void useSave (Board game)					//salva su un file json la configurazione iniziale e la posizione corrente dei blocchi
	{
		EventHandler<ActionEvent> event = e  -> {
			try {
				JsonSave.setConfig(game.getConfiguration());
				JsonSave.writeSave(game.getBlocks());
              System.out.println("saving");
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			System.out.println("saved");
		};
		save.setOnAction(event);
	}
	public void useResume (Button pauseButton)			//chiude il menu di pausa  e disabilita ESC come pulsante rapido per Resume e lo imposta per PauseButton
	{
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
	public Button getResume ()							//restituisce il pulsante per tornare al gioco
	{
		resume.setId("Resume");
		resume.setText("Resume");
		create_button(resume, 180, 75);
		//
		return resume;
	}
	private Button getSave ()							//restituisce il pulsante per eseguire il salvataggio
	{
		save.setId("Save");
		save.setText("Save");
		create_button(save, 180, 75);
		return save;
	}
	private Button getMainMenu ()						//restituisce il pulsante per tornare al menu principale funzionante
	{
		mainMenu.setId("Main Menu");
		mainMenu.setText("Main Menu");
		create_button(mainMenu, 180, 75);
		EventHandler<ActionEvent> event = e -> {
			int n = root.getChildren().size()-1;
			root.getChildren().remove(n);
			root.getChildren().remove(n-1);
			//root.getChildren().remove(n-2, n+1);
			System.out.println("back to main menu");
		};
		mainMenu.setOnAction(event);
		return mainMenu;
	}

}