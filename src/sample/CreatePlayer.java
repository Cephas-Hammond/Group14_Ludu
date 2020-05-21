package sample;

import javafx.scene.layout.GridPane;

public class CreatePlayer {

    Player[] player = new Player[2];
    
    int[][] initialx= {
    
            {3,4,3,4},//red x coordinates
            {11,12,11,12}//blue x coordinates
     

    };

    int[][] initialy= {

        {2,2,1,1},//red y coordinates
        {29,9,10,10}//blue y coordinates
    };

    public CreatePlayer(int height, int width){
        for(int i =  0; i< 2; i ++){
            player[i] = new Player(height, width);
        }
    }

    public void draw(GridPane pane){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j< 4; j++){
                player[i].pa[j].draw(pane, initialx[i][j],initialy[i][j],i);
            }
        }
    }
    
}


    
