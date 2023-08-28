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
/**
 * La classe PauseMenu rappresenta un menu di pausa nell'interfaccia di gioco.
 * Fornisce opzioni per riprendere il gioco, salvare lo stato e tornare al menu principale.
 */
public class PauseMenu extends Menu
{
	private static final Button resume   = new Button(null),
								save     = new Button(null),
								mainMenu = new Button(null);

	/**
	 * Costruisce un menu di pausa da visualizzare quando il gioco viene messo in pausa.
	 *
	 * @param root Il pannello radice dell'interfaccia di gioco.
	 * @param song Il MediaPlayer per la musica di sottofondo.
	 */

	public PauseMenu(StackPane root, MediaPlayer song)
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
	/**
	 * Imposta la funzionalità di salvataggio dello stato di gioco.
	 *
	 * @param game La configurazione del gioco.
	 */
	public void useSave (Board game)
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
	/**
	 * Imposta la funzionalità di ripresa del gioco.
	 *
	 * @param pauseButton Il pulsante di pausa del gioco.
	 */
	public void useResume (Button pauseButton)
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
	/**
	 * Restituisce il pulsante per riprendere il gioco.
	 *
	 * @return Il pulsante per la ripresa del gioco.
	 */
	public Button getResume ()
	{
		resume.setId("Resume");
		resume.setText("Resume");
		create_button(resume, 180, 75);
		//
		return resume;
	}
	/**
	 * Restituisce il pulsante per eseguire il salvataggio dello stato di gioco.
	 *
	 * @return Il pulsante per il salvataggio.
	 */
	private Button getSave ()
	{
		save.setId("Save");
		save.setText("Save");
		create_button(save, 180, 75);
		return save;
	}
	/**
	 * Restituisce il pulsante per tornare al menu principale.
	 *
	 * @return Il pulsante per il menu principale.
	 */
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