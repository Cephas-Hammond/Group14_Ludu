package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
        dbPlayer=db;
        dbPlayer.hasPlayed();
        Label text = new Label("This is a new Game");
        
        play = new Text();
        Button btn = new Button("Roll Dice");
        btn.setOnAction(e->{
            rollDice();
            });
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(text,btn,play);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,800,900);

        Main.window.setScene(scene);
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
        msg = play.getText();
    }
    public static int getDice(){
        return dice;
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
