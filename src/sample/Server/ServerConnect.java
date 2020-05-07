package sample.Server;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.NewGame;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class ServerConnect extends MultiPLay {

    final static int ServerPort = 1234;
    static Socket s;

    static void connect(String userName)throws IOException{

        Text connectStatus = new Text("Connecting...");

        //connectStatus.setText(connectStatus.getText()+".");
        VBox layout = new VBox(connectStatus);
        layout.setPadding(new Insets(10,5,10,5));
        layout.setAlignment(Pos.CENTER);

        window.setScene(new Scene(layout));

        //ESTABLISHING CONNECTION
        try {
            s = new Socket("localhost", ServerPort);
            NewGame.setSocket(s,userName);
            window.close();
            start.set(true);
        }catch (ConnectException e){
            connectStatus.setText("Failed to connect to server!!");
            //return;
        }

        //System.out.println("Continue...");
    }

}
