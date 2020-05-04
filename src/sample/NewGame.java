package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

public class NewGame {
    
    private static Label play;
    private static int dice;

    public static void startNewGame(Stage window){
        Label text = new Label("This is a new Game");
        
        play = new Label();
        Button btn = new Button("Roll Dice");
        btn.setOnAction(e->{
            rollDice();
            
            });
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(text,btn,play);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,800,900);

        window.setScene(scene);
    }
    private static void rollDice(){
    Random rand = new Random();
        dice = rand.nextInt(7);
        

        switch(dice){
            case 0:
            case 1:
                play.setText("1");
                break;
            case 2:
                play.setText("2");
                break;
            case 3:
                play.setText("3");
                break;
            case 4:
                play.setText("4");
                break;
            case 5:
                play.setText("5");
                break;
            case 6:
                play.setText("6");
                break;

        }
    }
    public static int getDice(){
        return dice;
    }
}
