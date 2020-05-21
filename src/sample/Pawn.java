package sample;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn {

    int current;
    int height;
    int width;
    int x;
    int y;

    public Pawn(int h,int w){
        current = -1;
        x = -1;
        y = -1;
        height = h;
        width = w;
    }

    public void draw(GridPane pane, int i, int j, int play){
        
        Circle circle = new Circle();
        if(current == -1){

            x=i;
            x=j;
            
            if(play == 0){
                circle.setFill(Color.RED);

            }
            if(play == 1){
                circle.setFill(Color.BLUE);

            }
            circle.setRadius(12);
            pane.add(circle,height,width);


        }else{
            x = Path.xmove[play][current];
            y = Path.ymove[play][current];

            if(play == 0){
                circle.setFill(Color.RED);

            }
            if(play == 1){
                circle.setFill(Color.BLUE);

            }
            circle.setRadius(12);
            pane.add(circle,y,x);


        }
    }

}