package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        window = primaryStage;

        window.setTitle("Ludu 1.0");

        VBox layout = new VBox(10);
        Button multiPlay = new Button("Multiplayer");
        multiPlay.setOnAction(e->{
            MultiPLay.display();
            NewGame.startNewGame(window);
        });

        Button newGame = new Button("New Game");
        newGame.setOnAction(e->{
            NewGame.startNewGame(window);
        });

        layout.getChildren().addAll(multiPlay,newGame);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout,800,900);
        window.setScene(scene1);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
