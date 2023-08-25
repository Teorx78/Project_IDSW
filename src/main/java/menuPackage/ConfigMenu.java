package menuPackage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ConfigMenu extends Menu{
        protected static Button C1 = new Button(null), C2 = new Button(null),  
                                C3 = new Button(null), C4 = new Button(null),
                                C5 = new Button(null), C6 = new Button(null);
    
    public ConfigMenu(StackPane root){
        super(root);
        VBox menu1 = new VBox(5);
        //menu1.getChildren().addAll();
        //menuV.setAlignment(Pos.BASELINE_CENTER);

        VBox menu2 = new VBox();
        //menuH.setAlignment(Pos.BASELINE_RIGHT);
        menu.setLeft(menu1);
        menu.setRight(menu2);
        menu.setMaxSize(400, 500);
    }
    private static void createConfig(Button button, int x, int y)
    {

    }
}
