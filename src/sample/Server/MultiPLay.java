package sample.Server;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class MultiPLay{

    static Stage window;
    private static Label warn;
    static AtomicBoolean start = new AtomicBoolean(false);



    public  static boolean display(String userName){

        window = new Stage();
        //window.setTitle("Enter Details");
        window.setMinWidth(300);
        window.initModality(Modality.APPLICATION_MODAL);

        Text name = new Text(userName);
        Label nameTxt = new Label("Name: ");

        TextField id = new TextField(userName+232);//SHOULD RANDOMIZED NUMBER
        Label idTxt = new Label("ID: ");

        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        layout.setHgap(5);
        layout.setPadding(new Insets(5,5,0,5));

        GridPane.setConstraints(nameTxt,0,0);
        GridPane.setConstraints(name,1,0);
        GridPane.setConstraints(idTxt,0,1);
        GridPane.setConstraints(id,1,1);

        Button ok = new Button("Ok");
        ok.setOnAction(e->{
            if(name.getText().isEmpty() || id.getText().isEmpty()){
                warn.setText("Empty Fields!!");
            }
            else{
                //CONNECT TO SERVER
                try {
                    ServerConnect.connect(userName);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        GridPane.setConstraints(ok,1,2);

        warn = new Label();
        GridPane.setConstraints(warn,1,3);

        layout.getChildren().addAll(nameTxt,name,idTxt,id,ok,warn);

        Scene scene1 = new Scene(layout);
        window.setScene(scene1);

        window.showAndWait();

        return start.get();
    }
}
