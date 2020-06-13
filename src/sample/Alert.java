package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.StringTokenizer;

public class Alert {
    private static ListView<String> players;
    private static Button ok;
    private static Stage alert;

    public static void display(String msg){
        Button ok = new Button("OK");

        Text message = new Text(msg);
        message.setFont(new Font("Rockwell Condensed",25));

        VBox layout = new VBox(10);

        layout.getChildren().addAll(message,ok);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.minWidth(200);
        layout.setPrefWidth(200);

        layout.setBackground(new Background(new BackgroundFill(Color.color(0.3,0.3,0.7,0.5),
                CornerRadii.EMPTY, Insets.EMPTY)));

        Stage alert = new Stage();
        alert.setX(Main.window.getX()+300);
        alert.setY(Main.window.getY()+350);

        ok.setOnAction(e->alert.close());
        alert.setScene(new Scene(layout));
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }

    public static void waitPlayers(String socketName,double x, double y){
        GamePlay.pickColor();

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
            Text waitText = new Text("The other Players are almost ready ...");
            waitText.setFont(new Font("Rockwell Condensed",25));
            VBox wait = new VBox(waitText);
            wait.setBackground(new Background(new BackgroundFill(Color.color(0.3,0.3,0.7,0.5),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            wait.setPadding(new Insets(10));
            wait.setAlignment(Pos.CENTER);
            alert.setScene(new Scene(wait));
            NewGame.sendMessage("ready");
        });

        Scene scene = new Scene(layout,200,200);
        alert.setScene(scene);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setX(Main.window.getX()+300);
        alert.setY(Main.window.getY()+350);
        alert.show();
    }

    public static void showPlayers(String str){
        int n = 1;
        try{
        n = players.getItems().size();
        }catch (NumberFormatException e){
            //e.printStackTrace();
        }
        int i=0;
        String playerName;
        players.getItems().remove(1,n);//if uncomment, change 0 to 1
        StringTokenizer st = new StringTokenizer(str, ",");

        while (st.hasMoreTokens()) {
            String[] msg = st.nextToken().split("]");
            playerName = msg[0];
            players.getItems().add(playerName + " joined.");
            //System.out.println();



            if(NewGame.getSocketName().equals(playerName)){
             GamePlay.setPlayerNumber(i);
             }
            else{
                GamePlay.setPlayerColors(i,msg[1]);
            }
            i++;
        }
        ok.setDisable(false);
    }

    public static void closeAlert(){
        alert.close();
    }

}
