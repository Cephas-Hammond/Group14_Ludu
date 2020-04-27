package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewGame {

    public static void startNewGame(Stage window){
        Label text = new Label("This is a new Game");

        VBox layout = new VBox(10);
        layout.getChildren().add(text);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,800,900);

        window.setScene(scene);
    }
}
