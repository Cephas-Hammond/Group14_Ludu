package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.geometry.*;


public class LudoBoard {
    
    public GridPane draw(){// returns the main ludo interface

        GridPane board = new GridPane();

        board.setPadding(new Insets(30, 10, 10, 10));
        board.setVgap(3);
        board.setHgap(3);

        //Design for yellow house

        Circle yello = new Circle(0,0,105);
        yello.setFill(Color.YELLOW);
        yello.setStroke(Color.BLACK);

        board.add(yello, 0,0,7,7);

       
        // Rectangle yellowhite = new Rectangle(0,0,135,140);
        Circle yellowhite = new Circle(0,0,68);
        yellowhite.setFill(Color.WHITE);
        yellowhite.setStroke(Color.BLACK);
        board.add(yellowhite,1,1,6,6);

        //Design for blue house
        
        // Rectangle blu = new Rectangle(0,0,210,210);
        Circle blu = new Circle(0,0,105);
        blu.setFill(Color.BLUE);
        blu.setStroke(Color.BLACK);
        board.add(blu, 9,0,7,7);

        //Rectangle bluwhite = new Rectangle(0,0,135,140);
        Circle bluwhite = new Circle(0,0,68);
        bluwhite.setFill(Color.WHITE);
        bluwhite.setStroke(Color.BLACK);
        board.add(bluwhite,10,1,6,6);

        //Design for green house
        //Rectangle green = new Rectangle(0,0,210,210);
        Circle green = new Circle(0,0,105);
        green.setFill(Color.GREEN);
        green.setStroke(Color.BLACK);
        board.add(green, 9,10,7,7);

        //Rectangle greenwhite = new Rectangle(0,0,135,140);
        Circle greenwhite = new Circle(0,0,68);
        greenwhite.setFill(Color.WHITE);
        greenwhite.setStroke(Color.BLACK);
        board.add(greenwhite,10,10,6,6);

        //Design for red house
        //Rectangle red = new Rectangle(0,0,210,210);
        Circle red = new Circle(0,0,105);
        red.setFill(Color.RED);
        red.setStroke(Color.BLACK);
        board.add(red, 0,10,7,7);

        //Rectangle redwhite = new Rectangle(0,0,135,140);
        Circle redwhite = new Circle(0,0,68);
        redwhite.setFill(Color.WHITE);
        redwhite.setStroke(Color.BLACK);
        board.add(redwhite,1,10,6,6);

        for(int i =1; i<=6;i++){//blue house row index
            for(int j = 6; j<=8;j++){//column index
            Circle circle = new Circle();
            circle.setRadius(17);
            circle.setFill(Color.LIGHTSLATEGRAY);
            circle.setStroke(Color.BLACK);
            if(i > 1 && j == 7){
                circle.setFill(Color.BLUE);
            }
            if(i ==2 && j == 8){
                circle.setFill(Color.BLUE);
            }
            board.add(circle, j, i);
            }
        }

        
        for(int i = 7; i<=9;i++){//yellow house row index
            for(int j = 0;j<=5;j++){//column index
                Circle circle = new Circle();
                circle.setRadius(17);
                circle.setFill(Color.LIGHTSLATEGRAY);
                circle.setStroke(Color.BLACK);
                if(i == 8 && j > 0){
                    circle.setFill(Color.YELLOW);
                }
                if(i == 7  && j ==1 ){
                    circle.setFill(Color.YELLOW);
                }
                board.add(circle, j, i);
            }
        }
        //StackPane stack = new StackPane();

        //center 

        Stop[] stops = new Stop[] { new Stop(0, Color.YELLOW), new Stop(1, Color.RED)};  
        LinearGradient linear = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        Image center = new Image(getClass().getClassLoader().getResourceAsStream("center1.png"));
        ImageView centerImage = new ImageView(center);
        Polygon octahome = new Polygon();
        octahome.getPoints().addAll(0.0,10.0,
                20.0,0.0,
                85.0,0.0,
                105.0,10.0,
                105.0,85.0,
                85.0,95.0,
                20.0,95.0,
                0.0,85.0);
        octahome.setFill(linear);
        octahome.setStroke(Color.BLACK);
        board.add(centerImage,6,6,5,5);
        centerImage.setFitWidth(110);
        centerImage.setPreserveRatio(true);


        for(int i =10; i<=15;i++){//red house
            for(int j = 6; j<=8;j++){
            Circle circle = new Circle();
            circle.setRadius(17);
            circle.setFill(Color.LIGHTSLATEGRAY);
            circle.setStroke(Color.BLACK);
            if(i<15 && j == 7){
                circle.setFill(Color.RED);
            }
            if(i ==14 && j == 6){
                circle.setFill(Color.RED);
            }
            board.add(circle, j, i);
            }
        }
        
        for(int i = 7; i<=9;i++){//green house
            for(int j = 9;j<=14;j++){
                Circle circle = new Circle();
                circle.setRadius(17);
                circle.setFill(Color.LIGHTSLATEGRAY);
                circle.setStroke(Color.BLACK);
                if(i == 8 && j < 14){
                    circle.setFill(Color.GREEN);
                }
                if(i == 9  && j ==13 ){
                    circle.setFill(Color.GREEN);
                }
                board.add(circle, j, i);
            }
        }
       
        board.setAlignment(Pos.TOP_CENTER);

        return board;
    }

   
}