package sample;

import com.sun.xml.internal.ws.api.ResourceLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.server.SQLiteJDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Stat {

    static void display(Stage window, SQLiteJDBC dbUser){
        Scene prevScene = window.getScene();
        dbUser.loadUser();

        Text userNameLabel = new Text("User Name:");
        userNameLabel.setTextAlignment(TextAlignment.RIGHT);
        Main.setFont(userNameLabel);

        TextField userName = new TextField(dbUser.getUserName());
        userName.setMaxWidth(100);
        userName.setFont(new Font("Rockwell",15));


        Text userIDLabel = new Text("User ID:");
        userIDLabel.setTextAlignment(TextAlignment.RIGHT);
        Main.setFont(userIDLabel);

        Text userID = new Text((""+dbUser.getId()));
        Main.setFont(userID);

        Text gamesPlayedLabel = new Text("Games Played:");
        gamesPlayedLabel.setTextAlignment(TextAlignment.RIGHT);
        Main.setFont(gamesPlayedLabel);

        Text gamesPlayed = new Text(""+dbUser.getGames());
        Main.setFont(gamesPlayed);

        Text winsLabel = new Text("Wins:");
        winsLabel.setTextAlignment(TextAlignment.RIGHT);
        Main.setFont(winsLabel);

        Text wins = new Text(""+dbUser.getWins());
        Main.setFont(wins);

        Text lossLabel = new Text("Losses:");
        lossLabel.setTextAlignment(TextAlignment.RIGHT);
        Main.setFont(lossLabel);

        Text loss = new Text(""+dbUser.getLosses());
        Main.setFont(loss);

        Button save = new Button("Save");
        save.setOnAction(e->{
            dbUser.setUserName(userName.getText());
            userID.setText("p_"+ dbUser.getUserName()+145);
            //SET ID UPDATE IN DB
        });
        save.setFont(new Font("Rockwell Condensed",20));

        Button back = new Button("Back");
        back.setOnAction(e->window.setScene(prevScene));
        back.setFont(new Font("Rockwell Condensed",20));

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(100));
        layout.setVgap(10);
        layout.setHgap(15);

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
        //layout.setBackground(background);
        setBackground(layout);

        window.setScene(new Scene(layout,800,900));
    }

    private static void setBackground(GridPane layout) {
        //Image image = new Image(new FileInputStream("src\\sample\\resources\\prof_back.png"));
        Image image = new Image(ResourceLoader.class.getClassLoader().getResourceAsStream("prof_back.png"));
        ImageView menuBack = new ImageView(image);

        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // create Background
        Background background = new Background(backgroundimage);
        layout.setBackground(background);
    }
}
