package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;


public class Alert {
    private static ListView<String> players;
    private static Button ok;
    private static Stage alert;

    public static void display(String msg){
        Button ok = new Button("OK");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Text(msg),ok);
        layout.setPadding(new Insets(10,5,10,5));
        layout.setAlignment(Pos.CENTER);

        Stage alert = new Stage();

        ok.setOnAction(e->alert.close());
        alert.setScene(new Scene(layout));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void waitPlayers(String socketName,double x, double y){
        ok = new Button("OK");
        ok.setDisable(true);//UNCOMMENT LATER

        players = new ListView<>();
        players.getItems().add("Waiting for Players....");
        //players.setPadding(new Insets(10,10,0,10));
        //players.setAlignment(Pos.CENTER);
        players.getItems().add(socketName + " joined.");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5));
        layout.getChildren().addAll(players,ok);

        alert = new Stage();
        alert.setX(x+300);
        alert.setY(y+350);
        alert.initStyle(StageStyle.UNDECORATED);

        ok.setOnAction(e->{
            VBox wait = new VBox(new Text("The other Players are almost ready ..."));
            wait.setAlignment(Pos.CENTER);
            alert.setScene(new Scene(wait));
            NewGame.sendMessage("ready");
        });

        Scene scene = new Scene(layout,200,200);
        alert.setScene(scene);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }

    public static void showPlayers(String str){
        AtomicBoolean ready = new AtomicBoolean(false);

        int n = players.getItems().size();
        String playerName;
        //if (n > 1) {
        players.getItems().remove(1,n);//if uncomment, change 0 to 1
        //}
        StringTokenizer st = new StringTokenizer(str, ",");
        while (st.hasMoreTokens()) {
            playerName = st.nextToken();
            players.getItems().add(playerName + " joined.");
        }
        //playersList.setText(str+" has joined\n");
        ok.setDisable(false);
    }

    public static void closeAlert(){
        alert.close();
    }

}
