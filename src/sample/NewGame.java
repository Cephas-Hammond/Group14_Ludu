package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class NewGame {

    private static Socket s = null;
    private static DataInputStream dis;
    private static DataOutputStream dos;
    private static String msg,receivedMsg;
    private static SQLiteJDBC dbPlayer;
    private static Text play;
    private static int dice;

    

    public static void startNewGame(SQLiteJDBC db){
        Scene prevScene = Main.window.getScene();

        dbPlayer=db;// initial db
        dbPlayer.hasPlayed();
        Label text = new Label("This is a new Game");

    
        play = new Text();

        BorderPane root = new BorderPane();//main layout pane -> Border pane
        root.setPadding(new Insets(5));
        MenuBar menubar = new MenuBar();//create a menu bar
        Menu FileMenu = new Menu("Menu");
        MenuItem New=new MenuItem("new");
        New.setOnAction(e->{
            NewGame.startNewGame(dbPlayer);//starts a  new game
        });
        MenuItem Save=new MenuItem("Save");

        MenuItem Exit=new MenuItem("Exit");
        Exit.setOnAction(e->{
            Main.window.setScene(prevScene);//exit to Main screen
        });
        
        FileMenu.getItems().addAll(New,Save,Exit);
        menubar.getMenus().add(FileMenu);
        root.setTop(menubar);

        //CHANGES
        Button[] btn = new Button[2];
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

        GridPane board = draw();
        root.setCenter(board);


        HBox lay = new HBox(5);//Hbox layout for toss buttons
        lay.setAlignment(Pos.CENTER);
        lay.getChildren().addAll(btn[0],btn[1]);//adds buttons to horizontal box
        
        VBox layout = new VBox(10);//vbox for arrangement
        layout.getChildren().addAll(text,lay,play);
        layout.setPrefHeight(300);
        layout.setAlignment(Pos.CENTER);
        root.setBottom(layout);

        //END OF CHANGES
        
        Scene scene = new Scene(root,800,800);

        Main.window.setScene(scene);
    }
    private static void rollDice()  {
    Random rand = new Random();
        dice = rand.nextInt(6) + 1;
        switch(dice){
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
        msg = play.getText();//sets the msg to display in the UI
    }
    

    public static GridPane draw(){// returns the main ludo  as a Gridpane

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
    }
    
    // ONLINE MULTIPLAYER THREADS
    // DO NOT TOUCH

    //SET SOCKET
    public static void setSocket(Socket socket, String name) {
        s = socket;
        dbPlayer = new SQLiteJDBC(name);
        try {
            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            Main.window.setOnCloseRequest(e->{
                Main.window.getOnCloseRequest();
                try {
                    s.close();
                    Thread.yield();
                    Thread.yield();
                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                    Main.window.close();
                }
            });
            //Start threads here
            sendMessage.start();
            readMessage.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // private static void createSwingContent(final SwingNode swingNode){
    //     SwingUtilities.invokeLater(new Runnable() {
    //         @Override
    //         public void run() {
    //             swingNode.setContent(new Painter());
    //         }
    //     });
    // }

    // MESSAGE SEND THREAD
    static Thread sendMessage = new Thread(new Runnable()
    {
        @Override
        public void run() {
            msg = dbPlayer.getUserName();
            while (!s.isClosed()) {

                // read the message to deliver.
                if (!msg.equals("")) {
                    msg = msg + "#" + dbPlayer.getUserName();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        //IN CASE CONNECTION IS LOST
                        /*VBox layout = new VBox(new Text("Connection to Server lost!!"));
                        layout.setPadding(new Insets(10,5,10,5));
                        layout.setAlignment(Pos.CENTER);
                        Stage alert = new Stage();
                        alert.setScene(new Scene(layout));
                        alert.initModality(Modality.APPLICATION_MODAL);
                        alert.showAndWait();*/
                        e.printStackTrace();//comment line out
                        break;
                    }
                    msg = "";
                }
                //if(readMessage.isInterrupted()) readMessage.start();
            }
            Thread.yield();
        }
    });

    // MESSAGE READ THREAD
    static Thread readMessage = new Thread(new Runnable()
    {
        @Override
        public void run() {

            while (!s.isClosed()) {
                try {
                    // read the message sent to this client
                    receivedMsg = dis.readUTF();
                    StringTokenizer st = new StringTokenizer(receivedMsg, "#");
                    String msgReceived = st.nextToken();
                    String sender = st.nextToken();
                    getInt(msgReceived);//Received die output
                    readMessage.interrupt();
                } catch (IOException e) {
                    Thread.yield();
                    break;
                    //e.printStackTrace();
                }
                if(sendMessage.isInterrupted()) sendMessage.start();
            }
        }
    });
    static void getInt(String a){
        int val;
        try {
            val =  Integer.parseInt(a);
            play.setText(""+val);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
    }
}
