package sample;


import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;

import javafx.geometry.*;


public class LudoBoard {
    
    public GridPane draw(){// returns the main ludo interface

        GridPane board = new GridPane();
        //board.setGridLinesVisible(true);
        board.setPadding(new Insets(30, 10, 10, 10));
        board.setVgap(3);
        board.setHgap(3);

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

        // pawns for the yellow house
        for(int i = 3;i <5;i++){
            for(int j = 1;j<3;j++  ){
        Circle box = new Circle();
        box.setRadius(12);
        box.setFill(Color.YELLOW);
        box.setStroke(Color.BLACK);
        board.add(box, j, i);
        GridPane.setHalignment(box, HPos.CENTER);

            }
        }

        for(int i =10; i<=15;i++){//red house
            for(int j = 6; j<=8;j++){
            Circle circle = new Circle();
            circle.setRadius(17);
            circle.setFill(Color.LIGHTSLATEGRAY);
            circle.setStroke(Color.BLACK);
            if(i < 15 && j == 7){//CHANGE EFFECTED HERE
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

        //Pawns for green house
        for(int i = 12;i <=13;i++){
            for(int j = 12;j<=13;j++  ){
        Circle box = new Circle();
        box.setRadius(12);
        box.setFill(Color.GREEN);
        box.setStroke(Color.BLACK);
        board.add(box, j, i);
        GridPane.setHalignment(box, HPos.CENTER);

            }
        }
       
        board.setAlignment(Pos.TOP_CENTER);

        return board;
    }

   
}
