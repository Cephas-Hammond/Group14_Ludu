package sample;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Parent;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Server.MultiPLay;

public class Main extends Application {

    private Stage userNameRequest;
    static Stage window;
    private String userName;
    private SQLiteJDBC dbPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        window = primaryStage;
        /*window.setOnCloseRequest(e->{
            dbPlayer.delete();
        });*/

        window.setTitle("Ludu 1.0");

        VBox layout = new VBox(10);
        Button multiPlay = new Button("Multiplayer");
        multiPlay.setOnAction(e->{
            if(MultiPLay.display(userName))NewGame.startNewGame(dbPlayer);
        });

        //WELCOME TEXT
        Label welcomeText = new Label();

        Button newGame = new Button("Single Game");
        newGame.setOnAction(e->{
            NewGame.startNewGame(dbPlayer);
        });

        Button stats = new Button("Statistics");
        stats.setOnAction(e->Stat.display(window,dbPlayer));


        layout.getChildren().addAll(multiPlay,newGame,stats);
        layout.setAlignment(Pos.CENTER);

        BorderPane withWelcomeText = new BorderPane();
        withWelcomeText.setPadding(new Insets(5,0,0,0));
        withWelcomeText.setTop(welcomeText);
        withWelcomeText.setCenter(layout);

        Scene scene1 = new Scene(withWelcomeText,800,900);
        window.setScene(scene1);
        window.show();

        requestUserName(welcomeText);
        dbPlayer = new SQLiteJDBC(userName);
    }

    private void requestUserName(Label welcomeText) {
        //USERNAME REQUEST
        userNameRequest = new Stage();
        Label userLabel = new Label("Username");
        userLabel.setFont(new Font("Arial",15));

        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter username");

        Button accept = new Button("Ok");
        accept.setDisable(true);
        accept.setOnAction(e->{
            if(!userNameField.getText().equals("")){
                userName = userNameField.getText();
                userNameRequest.close();
                welcomeText.setText("Welcome, "+userName);
            }
        });
        userNameField.setOnKeyPressed(e->{
            if (e.getCode().getName().equals("Enter") &&
                    !userNameField.getText().equals("")){
                userName = userNameField.getText();
                userNameRequest.close();
                welcomeText.setText("Welcome, "+userName);
            }
            if(!userNameField.getText().equals(""))accept.setDisable(false);
        });

        GridPane userNameLayout = new GridPane();
        GridPane.setConstraints(userLabel,0,0,2,1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(userNameField,0,1);
        GridPane.setConstraints(accept,0,2,2,1, HPos.CENTER, VPos.CENTER);
        userNameLayout.setPadding(new Insets(10,5,0,5));
        userNameLayout.setVgap(5);
        userNameLayout.getChildren().addAll(userLabel,userNameField,accept);

        //UNCOMMENT CODE BELOW
        userNameRequest.initModality(Modality.APPLICATION_MODAL);
        userNameRequest.setOnCloseRequest(Event::consume);
        userNameRequest.setScene(new Scene(userNameLayout));
        userNameRequest.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
