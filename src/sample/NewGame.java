package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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


    public static void startNewGame(SQLiteJDBC playerDB){
        playerDB.hasPlayed();
		Scene prevScene = Main.window.getScene();
        
        Label text = new Label("This is a new Game");

        
        play = new Text();
        /*btn = new Button("Toss");
        btn.setOnAction(e->{
            rollDice();
            });*/
			
		BorderPane root = new BorderPane();//main layout pane -> Border pane
        root.setPadding(new Insets(5));
        //SETTING MENU BAR FOR ROOT TOP
        MenuBar menubar = new MenuBar();//create a menu bar
        Menu FileMenu = new Menu("Menu");
        MenuItem New=new MenuItem("new");
        New.setOnAction(e->{
            NewGame.startNewGame(playerDB);//starts a  new game
        });
        MenuItem Save=new MenuItem("Save");

        MenuItem Exit=new MenuItem("Exit");
        Exit.setOnAction(e->{
            Main.window.setScene(prevScene);//exit to Main screen
        });
        
        FileMenu.getItems().addAll(New,Save,Exit);
        menubar.getMenus().add(FileMenu);
        root.setTop(menubar);
        //END OF MENU BAR

        //CHANGES
        btn[0] = new Button("P1: Toss");//for Player 1 toss
        btn[1] = new Button("P2: Toss");// for player 2 toss
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

        GamePlay.setUp(pane);
        //END OF MOVEMENT CHANGES

        root.setCenter(pane);
        root.getCenter().setOnMouseClicked(e -> {
            System.out.println(e.getX()+","+e.getY());
        });
        //root.setCenter(board);


        HBox lay = new HBox(5);//Hbox layout for toss buttons
        lay.setAlignment(Pos.CENTER);
        lay.getChildren().add(btn[0]);//adds buttons to horizontal box

        if(socketName==null){
            lay.getChildren().add(btn[1]);//adds buttons to horizontal box
        }

        VBox layout = new VBox(10);//vbox for arrangement
        layout.getChildren().addAll(text,lay,play);
        layout.setPrefHeight(300);
        layout.setAlignment(Pos.CENTER);
        root.setBottom(layout);

        //END OF CHANGES
        


        Scene scene = new Scene(root,800,900);
        /*scene.setOnMouseClicked(e -> {
            System.out.println(e.getX()+","+e.getY());
        });*/

        Main.window.setScene(scene);
        if(socketName !=null){
            Alert.waitPlayers(socketName,Main.window.getX(),Main.window.getY());
            sendMessage("findPlayers");
        }

    }



    /*private static void overlay(Pane pane) {
        piece1[0][0] = new Circle(0,0,12,Color.YELLOW);
        piece1[0][0].setLayoutX(206);
        piece1[0][0].setLayoutY(121);
        pane.getChildren().add(piece1[0][0]);
    }*/

    private static void rollDice(){
    Random rand = new Random();
        dice = rand.nextInt(6)+1;

        GamePlay.movePiece(dice);

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
    }
    public static int getDice(){
        return dice;
    }

    public static Button getBtn() {return btn[0]; }
	
	
	/*public static GridPane draw(){// returns the main ludo  as a Gridpane

        GridPane board = new GridPane();
        board.setPadding(new Insets(30, 10, 10, 10));
        board.setVgap(5);
        board.setHgap(5);

        for(int i =1; i<=5;i++){//blue house row index
            for(int j = 5; j<8;j++){//column index
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
                if(i > 1 && j == 6){
                    circle.setFill(Color.BLUE);
                }
                if(i ==2 && j == 7){
                    circle.setFill(Color.BLUE);
                }
                board.add(circle, j, i);
            }
        }


        for(int i = 6; i<9;i++){//yellow house row index
            for(int j = 1;j<=5;j++){//column index
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
                if(i == 7 && j > 1){
                    circle.setFill(Color.YELLOW);
                }
                if(i == 6  && j ==2 ){
                    circle.setFill(Color.YELLOW);
                }
                board.add(circle, j, i);
            }
        }

        // pawns for the yellow house
        for(int i = 3;i <5;i++){
            for(int j = 1;j<3;j++  ){
                Circle box = new Circle();
                box.setRadius(12);
                box.setFill(Color.YELLOW);
                box.setStroke(Color.BLACK);
                board.add(box, j, i);

            }
        }

        for(int i =10; i<15;i++){//red house
            for(int j = 5; j<8;j++){
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
                if(i > 10 && j == 6){
                    circle.setFill(Color.RED);
                }
                if(i ==13 && j == 5){
                    circle.setFill(Color.RED);
                }
                board.add(circle, j, i);
            }
        }

        for(int i = 6; i<9;i++){//green house
            for(int j = 7;j<12;j++){
                Circle circle = new Circle();
                circle.setRadius(15);
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
                if(i == 7 && j < 11){
                    circle.setFill(Color.GREEN);
                }
                if(i == 8  && j ==10 ){
                    circle.setFill(Color.GREEN);
                }
                board.add(circle, j, i);
            }
        }

        //Pawns for green house
        for(int i = 12;i <14;i++){
            for(int j = 9;j<11;j++  ){
                Circle box = new Circle();
                box.setRadius(12);
                box.setFill(Color.GREEN);
                box.setStroke(Color.BLACK);
                board.add(box, j, i);

            }
        }

        board.setAlignment(Pos.TOP_CENTER);

        return board;
    }*/
	
	
	
	
	
	
	
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
                    s.close();
                    Thread.yield();
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                    Main.window.close();
                }
            });

            //Thread sendMsgThread = new Thread(new SendMessage());
            Thread readMsgThread = new Thread(new ReadMessage(s,dis));

            //Start threads here
            //sendMessage.start();
            //sendMsgThread.start();

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
}



