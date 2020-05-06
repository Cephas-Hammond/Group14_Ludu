package sample;

import javafx.animation.TranslateTransition;  
import javafx.application.Application;  
import javafx.event.EventHandler;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.input.MouseEvent;  
import javafx.scene.paint.Color;  
import javafx.scene.shape.Sphere;  
import javafx.scene.shape.CullFace;
import javafx.stage.Stage;  
import javafx.util.Duration;  

public class ColourPiece {

    private int NumOfPiece = 4;



    Sphere s = new Sphere(10);
    


    public int getNumOfPiece(){
        return NumOfPiece;
    }

    public void setNumOfPiece(int num){
        NumOfPiece = num;
    }
    public void movePiece(int Xaxis){
        TranslateTransition trans = new TranslateTransition();

        trans.setAutoReverse(true);  
        trans.setByX(Xaxis);  
        trans.setCycleCount(10);  
        trans.setDuration(Duration.millis(500));  
        trans.setNode(s);  
    

    }
    
}