package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MultiPLay {

    private static Stage window;
    private static VBox layout;
    private static Scene scene1;
    private static Label warn;

    public  static void  display(){

        window = new Stage();
        window.setTitle("Enter Details");
        window.setMinWidth(300);
        window.initModality(Modality.APPLICATION_MODAL);

        TextField name = new TextField();
        Label nameTxt = new Label("Name: ");
        HBox nameLayout = new HBox(5);
        nameLayout.setAlignment(Pos.CENTER);
        nameLayout.getChildren().addAll(nameTxt,name);

        TextField id = new TextField();
        Label idTxt = new Label("ID: ");
        HBox idLayout = new HBox(5);
        idLayout.setAlignment(Pos.CENTER);
        idLayout.getChildren().addAll(idTxt,id);

        Button ok = new Button("Ok");
        ok.setOnAction(e->{
            //e.consume();
            checkClose(name.getText(),id.getText());
        });

        warn = new Label();


        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameLayout,idLayout,ok,warn);

        scene1 = new Scene(layout);
        window.setScene(scene1);

        window.showAndWait();


    }

    private static void checkClose(String name, String id) {
        if(name.isEmpty() || id.isEmpty()){
            warn.setText("Empty Fields!!");
        }
        else{
            window.close();
        }
    }
}
