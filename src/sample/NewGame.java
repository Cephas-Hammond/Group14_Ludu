package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.server.MultiPLayerConnect;
import sample.server.ReadMessage;
import sample.server.SQLiteJDBC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class NewGame {

    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static String socketName;

    private static Text play;
    private static int dice;
    //private static Button btn; //#serverside_change
    private static Button[] btn = new Button[2];
    private static MenuItem Exit2;

    //player indicators
    private static HBox indication = new HBox(10);

    private static Color colors[] = new Color[4];

    public static Rectangle playerColor;
    public static Text playerNum;


    public static void startNewGame(){
        SQLiteJDBC.hasPlayed();

        Label text = new Label("This is a new Game");

        
        play = new Text();

			
		BorderPane root = new BorderPane();//main layout pane -> Border pane
        root.setPadding(new Insets(0,0,0,0));

        //SETTING MENU BAR FOR ROOT TOP
        MenuBar menubar = new MenuBar();//create a menu bar
        Menu FileMenu = new Menu("Menu");
        MenuItem New=new MenuItem("new");
        New.setOnAction(e->{
            NewGame.startNewGame();//starts a  new game
        });
        MenuItem Save=new MenuItem("Save");
        MenuItem Exit=new MenuItem("Exit");
        Exit.setOnAction(e->Main.window.setScene(Main.mainMenu));

        VBox vBox = new VBox(20);
        vBox.setMinHeight(100);
        vBox.setMaxHeight(100);
        vBox.setPrefHeight(100);
        vBox.setPrefWidth(800);
        vBox.setMaxWidth(800);

        FileMenu.getItems().removeAll(FileMenu.getItems());
        FileMenu.getItems().addAll(New,Save);

        if(FileMenu.getItems().contains(Exit) || FileMenu.getItems().contains(Exit2))FileMenu.getItems().remove(3);

        if(socketName == null){
            FileMenu.getItems().add(Exit);
        }
        else{
            FileMenu.getItems().add(Exit2);
        }


        menubar.getMenus().add(FileMenu);

        vBox.getChildren().add(menubar);
        vBox.getChildren().add(indication);
        root.setTop(vBox);
        //END OF MENU BAR

                btn[0] = new Button("TOSS");//for Player 1 toss
        btn[1] = new Button("P2: TOSS");// for player 2 toss
        btn[1].setDisable(true);//disabled for player 1 to toss first
        btn[0].setOnAction(e->{
            rollDice();
            if(play.getText()!="6"){//if toss is not 6 playern1 is disabled and player 2 enabled
                btn[0].setDisable(true);
                btn[1].setDisable(false);
            }
            });
        btn[1].setOnAction(e->{
            rollDice();

            if(play.getText()!="6"){//if toss is not 6 player 2 is disabled and player 1 enabled
                btn[1].setDisable(true);
                btn[0].setDisable(false);
            }
        });

        GridPane board = new LudoBoard().draw();

        //MOVEMENT CHANGES
        Pane pane = new Pane();
        pane.getChildren().add(board);
        pane.getChildren().get(0).setLayoutX(100);

        GamePlay.setUp(pane,socketName);
        //END OF MOVEMENT CHANGES

        root.setCenter(pane);


        HBox lay = new HBox(5);//Hbox layout for toss buttons
        lay.setAlignment(Pos.CENTER);
        lay.getChildren().add(btn[0]);//adds buttons to horizontal box

        if(socketName==null){
            btn[0].setText("P1: TOSS");
            lay.getChildren().add(btn[1]);//adds buttons to horizontal box
        }

        Rectangle die = new Rectangle(30,30,Color.WHITESMOKE);
        die.setStroke(Color.BLACK);
        Pane diePane = new Pane();
        diePane.getChildren().addAll(die,play);
        die.setLayoutX(370);
        die.setLayoutY(0);
        play.setLayoutX(380);
        play.setLayoutY(20);

        VBox layout = new VBox(10);//vbox for arrangement
        layout.getChildren().addAll(lay,diePane);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        root.setBottom(layout);

        Image gameScreenBack = new Image(NewGame.class.getClassLoader().getResourceAsStream("finalBack.png"));
        ImageView gameScreenBackImage = new ImageView(gameScreenBack);
        root.setBackground(new Background(new BackgroundImage(gameScreenBack, BackgroundRepeat.NO_REPEAT,
                                                                BackgroundRepeat.NO_REPEAT,
                                                                BackgroundPosition.DEFAULT,
                                                                BackgroundSize.DEFAULT)));

        Scene scene = new Scene(root,800,900);

        Main.window.setScene(scene);
        if(socketName !=null){
            Alert.waitPlayers(socketName,Main.window.getX(),Main.window.getY());
            sendMessage("findPlayers");
        }

        playerNum = new Text("Player " + (GamePlay.getTurn()+1)+":");
        Main.setFont(playerNum);
        Text playerName = new Text(socketName);
        Main.setFont(playerName);
        playerColor = new Rectangle(25,25,GamePlay.getColorPosition());
        playerColor.setStroke(Color.BLACK);
        indication.getChildren().removeAll(indication.getChildren());
        indication.getChildren().addAll(playerNum,playerName,playerColor);
        indication.setAlignment(Pos.CENTER);
    }

    private static void rollDice(){
    Random rand = new Random();
        dice = rand.nextInt(6)+1;

        if(socketName!=null)btn[0].setDisable(true);//#serverside_change

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
                //btn.setDisable(false);
                play.setText("6");
                break;
        }
        if(socketName!=null)sendMessage(play.getText());
        GamePlay.movePiece(dice);
    }
    public static int getDice(){
        return dice;
    }

    public static Button getBtn() {return btn[0];}

    public static String getSocketName() {return socketName;}

	
	

	
	
	
	//#serverside_change

    public static Text getPlay() {return play;}
    // ONLINE MULTIPLAYER THREADS
    // DO NOT TOUCH

    //SET SOCKET
    public static void setSocket(Socket s, String name) {
        socketName = name;

        try {
            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            Main.window.setOnCloseRequest(e->{
                Main.window.getOnCloseRequest();
                try {
                    //TELL OTHER USERS OF EXIT
                    sendMessage("out");
                    Thread.sleep(500);
                    dis.close();
                    dos.close();
                    s.close();
                    Thread.yield();
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                    Main.window.close();
                }
            });
            Exit2 = new MenuItem("Exit");
            Exit2.setOnAction(e->{
                try {
                    //TELL OTHER USERS OF EXIT
                    sendMessage("out");
                    Thread.sleep(500);
                    dis.close();
                    dos.close();
                    s.close();
                    socketName=null;
                    Thread.yield();
                    Main.window.setScene(Main.mainMenu);//exit to Main screen
                    Main.window.setOnCloseRequest(f->Main.window.close());
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                }
            });

            //Thread sendMsgThread = new Thread(new SendMessage());
            Thread readMsgThread = new Thread(new ReadMessage(s,dis));

            //NO THREAD FOR SENDING MESSAGES
            sendMessage(socketName);

            //readMessage.start();
            readMsgThread.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void sendMessage(String msg){
        if (!msg.equals("")) {
            //setMsg(getMsg()+ "#" +getSocketName());
            msg+="#"+socketName;
            try {
                // write on the output stream
                dos.writeUTF(msg);
            } catch (IOException e) {
                //IN CASE CONNECTION IS LOST
                Alert.display("Connection to Server Lost...");
                e.printStackTrace();//comment line out
            }
        }
    }

    static void ExitGame() {
        if(socketName!=null) {
            try {
                //TELL OTHER USERS OF EXIT
                sendMessage("out");
                Thread.sleep(500);
                dis.close();
                dos.close();
                MultiPLayerConnect.disconnect();
                socketName = null;
                Thread.yield();
                Main.window.setScene(Main.mainMenu);//exit to Main screen
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
        }
        Main.window.setScene(Main.mainMenu);
    }
}



