package sample.server;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.NewGame;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;


public class MultiPLayerConnect {

    private static Stage window;
    private static Text warn;
    private static Background background;
    private static AtomicBoolean start = new AtomicBoolean(false);

    //CHANGES
    private final static int ServerPort = 1234;
    private static Socket socket;

    public  static boolean display(String userName,String id){

        window = new Stage();
        window.setMinWidth(300);

        window.initModality(Modality.APPLICATION_MODAL);

        Text name = new Text(userName);
        name.setFont(new Font("Rockwell Condensed",25));
        
        Text nameTxt = new Text("Name: ");
        nameTxt.setFont(new Font("Rockwell Condensed",25));

        TextField ID = new TextField(id);//SHOULD RANDOMIZED NUMBER
        ID.setFont(new Font("Rockwell",15));

        Text idTxt = new Text("ID: ");
        idTxt.setFont(new Font("Rockwell Condensed",25));

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(5);
        layout.setPadding(new Insets(5,5,0,5));

        GridPane.setConstraints(nameTxt,0,0);
        GridPane.setConstraints(name,1,0);
        GridPane.setConstraints(idTxt,0,1);
        GridPane.setConstraints(ID,1,1);

        Button ok = new Button("Ok");
        ok.setFont(new Font("Rockwell Condensed",20));
        ok.setOnAction(e->{
            if(name.getText().isEmpty() || ID.getText().isEmpty()){
                warn.setText("Empty Fields!!");
            }
            else{
                //CONNECT TO SERVER
                try {
                    //ServerConnect.connect(userName);
                    connectToServer(userName);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        GridPane.setConstraints(ok,1,2);

        warn = new Text();
        warn.setFont(new Font("Rockwell Condensed",25));

        GridPane.setConstraints(warn,1,3);

        layout.getChildren().addAll(nameTxt,name,idTxt,ID,ok,warn);

        BackgroundFill background_fill = new BackgroundFill(Color.color(0.3,0.3,0.7,0.5),
                CornerRadii.EMPTY, Insets.EMPTY);
        background = new Background(background_fill);
        layout.setBackground(background);

        Scene scene1 = new Scene(layout);
        window.setScene(scene1);

        window.showAndWait();

        return start.get();
    }


    //SERVER CONNECT
    static void connectToServer(String userName)throws IOException{

        Text connectStatus = new Text("Connecting...");
        connectStatus.setFont(new Font("Rockwell Condensed",25));

        HBox layout = new HBox(connectStatus);
        layout.setSpacing(10);
        layout.setPadding(new Insets(10,5,10,5));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(background);

        window.setScene(new Scene(layout));


        //ESTABLISHING CONNECTION
        try {
            socket = new Socket("localhost", ServerPort);
            NewGame.setSocket(socket,userName);
            window.close();
            start.set(true);
        }catch (ConnectException e){
            connectStatus.setText("Failed to connect to server!!");
        }
    }

    public static void disconnect() throws IOException {socket.close();}

}
