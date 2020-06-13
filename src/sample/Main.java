package sample;
/*
Second Semester, 2020
CPEN 312: OOP with Java
Final Project : Group 14, Project 5
Nudze Grace
Cephas Hammond
Baron Afutu
 */

import com.sun.xml.internal.ws.api.ResourceLoader;
import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.server.MultiPLayerConnect;
import sample.server.SQLiteJDBC;
import java.io.FileNotFoundException;

public class Main extends Application {

    static Stage window;
    static Scene mainMenu;
    private String userName;

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/

        window = primaryStage;
        window.setOnCloseRequest(e-> SQLiteJDBC.delete());

        window.setTitle("Ludu 1.0");

        VBox layout = new VBox(10);

        //WELCOME TEXT
        Text welcomeText = new Text();
        setFont(welcomeText);

        //NEW GAME BUTTON
        Image newGameImage = new Image(getClass().getClassLoader().getResourceAsStream("newGame1.png"));
        //Image newGameImage = new Image(new FileInputStream("src\\sample\\resources\\newGame1.png"));
        ImageView newGameIcon = new ImageView(newGameImage);

        Button newGame = new Button("Single Game",newGameIcon);
        newGame.setOnAction(e-> NewGame.startNewGame());
        newGame.setFont(new Font("Rockwell Condensed",20));

        //MULTI PLAY BUTTON
        //Image multiplayer = new Image(new FileInputStream("src\\sample\\resources\\multiplay1.png"));
        Image multiplayer = new Image(getClass().getClassLoader().getResourceAsStream("multiplay1.png"));
        ImageView multiplayerIcon = new ImageView(multiplayer);

        Button multiPlay = new Button("Multiplayer",multiplayerIcon);
        multiPlay.setOnAction(e->{
            if(MultiPLayerConnect.display(userName, SQLiteJDBC.getId()))NewGame.startNewGame();
        });
        multiPlay.setFont(new Font("Rockwell Condensed",20));

        //PROFILE BUTTON
        //Image profile = new Image(new FileInputStream("src\\sample\\resources\\profile1.png"));
        Image profile = new Image(getClass().getClassLoader().getResourceAsStream("profile1.png"));
        ImageView profileIcon = new ImageView(profile);

        Button profileBtn = new Button("Profile",profileIcon);
        profileBtn.setOnAction(e->Stat.display(window));
        profileBtn.setFont(new Font("Rockwell Condensed",20));


        layout.getChildren().addAll(newGame,multiPlay,profileBtn);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setPadding(new Insets(0,0,200,0));

        BorderPane withWelcomeText = new BorderPane();
        withWelcomeText.setPadding(new Insets(10,10,10,10));
        withWelcomeText.setBottom(welcomeText);
        withWelcomeText.setCenter(layout);

        Scene scene1 = new Scene(withWelcomeText,800,900);
        setBackground(withWelcomeText);//SETTING BACKGROUND

        intro(welcomeText);

        window.setScene(scene1);
        window.show();
        mainMenu = scene1;

        /*/requestUserName(welcomeText);
        playerDB = new SQLiteJDBC(userName);*/
    }

    private void setBackground(BorderPane layout) throws FileNotFoundException {
        //Image image = new Image(new FileInputStream("src\\sample\\resources\\menu_back1.png"));
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("menu_back1.png"));
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

    private void intro(Text welcomeText) throws FileNotFoundException {

        //Image image = new Image(new FileInputStream("src\\sample\\resources\\enterName2.png"));
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("enterName2.png"));
        ImageView introImage = new ImageView(image);

        VBox testLayout = new VBox(5);
        testLayout.setAlignment(Pos.CENTER);
        Stage stage = new Stage();

        BackgroundFill background_fill = new BackgroundFill(Color.color(0.3,0.3,0.7,0.5),
                CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        HBox subLayout = new HBox(5);
        subLayout.setAlignment(Pos.CENTER);

        Label userLabel = new Label("Username");
        userLabel.setFont(new Font("Rockwell Condensed",25));

        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        userNameField.setFont(new Font("Rockwell",15));
        //userNameField.setBackground();


        Button accept = new Button("OK");
        accept.setFont(new Font("Rockwell Condensed",15));
        accept.setDisable(true);
        accept.setOnAction(e->{
            if(!userNameField.getText().equals("")){
                userName = userNameField.getText();

                //requestUserName(welcomeText);
                SQLiteJDBC.startDB(userName);

                stage.close();
                welcomeText.setText("Welcome, "+userName);
            }
        });
        userNameField.setOnKeyPressed(e->{
            if (e.getCode().getName().equals("Enter") &&
                    !userNameField.getText().equals("")){
                userName = userNameField.getText();

                //requestUserName(welcomeText);
                SQLiteJDBC.startDB(userName);

                stage.close();
                welcomeText.setText("Welcome, "+userName);
            }
            if(!userNameField.getText().equals(""))accept.setDisable(false);
        });

        subLayout.getChildren().addAll(userLabel,userNameField,accept);
        testLayout.setBackground(background);
        testLayout.getChildren().addAll(introImage,subLayout);
        testLayout.setPadding(new Insets(10));

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(testLayout));
        stage.showAndWait();
    }

    public static void setFont(Text text){
        text.setFont(new Font("Rockwell Condensed",25));
        text.setFill(Color.WHITESMOKE);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
