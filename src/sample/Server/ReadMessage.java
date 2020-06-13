package sample.server;

import javafx.application.Platform;
import sample.Alert;
import sample.GamePlay;
import sample.NewGame;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class ReadMessage extends NewGame implements Runnable {
    String receivedMsg;
    Socket socket;
    DataInputStream dis;

    public ReadMessage(Socket s,DataInputStream d){
        socket = s;
        dis = d;
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                // read the message sent to this client
                receivedMsg = dis.readUTF();
                StringTokenizer st = new StringTokenizer(receivedMsg, "#");
                String msgReceived = st.nextToken();
                String sender = st.nextToken();
                decodeMsg(msgReceived,sender);//Received die output
                //readMessage.interrupt();
            } catch (IOException e) {
                Thread.yield();
                break;
                //e.printStackTrace();
            }
        }
    }

    private static void decodeMsg(String a,String user){
        Platform.runLater(new Runnable() {
            //INTERRUPTING NON JAVAFX THREAD
            @Override
            public void run() {
                if(a.equals("out")){
                    Alert.display(user+" has exited the game.");
                }
                else if(a.equals("start")){
                    Alert.closeAlert();
                    getBtn().setDisable(true);
                }
                else if(a.equals("won")){//someone has won
                    Alert.display("You Lost\n"+ user + " Won");
                    SQLiteJDBC.haslost();
                }
                else if(a.equals("yourTurn")){
                    getBtn().setDisable(false);
                }
                else if(a.contains("color")){
                    try {
                        int color = Integer.parseInt(a.split(":")[1]);
                        GamePlay.takenColors[0] = color;
                        GamePlay.resetColors();
                        //System.out.println(color);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else if(a.contains(",")){
                    Alert.showPlayers(a);
                }
                else {
                    try {
                        int val =  Integer.parseInt(a);
                        getPlay().setText(""+val);
                        GamePlay.movePiece(val);
                    } catch (NumberFormatException e) {
                        //e.printStackTrace();
                    }
                }
            }
        });

    }
}
