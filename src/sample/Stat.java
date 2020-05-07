package sample;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Stat {

    static  void display(Stage window,SQLiteJDBC dbUser){
        Scene prevScene = window.getScene();
        dbUser.loadUser();

        Text userNameLabel = new Text("User Name:");
        userNameLabel.setTextAlignment(TextAlignment.RIGHT);
        TextField userName = new TextField(dbUser.getUserName());
        userName.setMaxWidth(100);

        Text userIDLabel = new Text("User ID:");
        userIDLabel.setTextAlignment(TextAlignment.RIGHT);
        Text userID = new Text((""+dbUser.getId()));

        Text gamesPlayedLabel = new Text("Games Played:");
        gamesPlayedLabel.setTextAlignment(TextAlignment.RIGHT);
        Text gamesPlayed = new Text(""+dbUser.getGames());

        Text winsLabel = new Text("Wins:");
        winsLabel.setTextAlignment(TextAlignment.RIGHT);
        Text wins = new Text(""+dbUser.getWins());

        Text lossLabel = new Text("Losses:");
        lossLabel.setTextAlignment(TextAlignment.RIGHT);
        Text loss = new Text(""+dbUser.getLosses());

        Button save = new Button("Save");
        save.setOnAction(e->{
            dbUser.setUserName(userName.getText());
            userID.setText("p_"+ dbUser.getUserName()+145);
            //SET ID UPDATE IN DB
        });
        Button back = new Button("Back");
        back.setOnAction(e->window.setScene(prevScene));

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.setVgap(10);
        layout.setHgap(5);

        GridPane.setConstraints(userNameLabel,0,0,1,1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(userIDLabel,0,1,1,1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(gamesPlayedLabel,0,2,1,1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(winsLabel,0,3,1,1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(lossLabel,0,4,1,1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(save,1,5,1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(back,0,5,1,1, HPos.RIGHT, VPos.CENTER);

        GridPane.setConstraints(userName,1,0,1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(userID,1,1,1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(gamesPlayed,1,2,1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(wins,1,3,1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(loss,1,4,1,1, HPos.LEFT, VPos.CENTER);

        layout.getChildren().addAll(userNameLabel,userIDLabel,gamesPlayedLabel,winsLabel,lossLabel,back,save);
        layout.getChildren().addAll(userName,userID,gamesPlayed,wins,loss);

        window.setScene(new Scene(layout,800,900));
    }
}
