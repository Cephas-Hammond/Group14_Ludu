package sample;

public class Player {

    int height;
    int width;
    int status;
    int home;

    Pawn[] pa = new Pawn[4];

    public Player(int height, int width){
        status = -1;
        home = 0;

        for(int i = 0; i < 4; i++){
            pa[i] = new Pawn(height, width);
        }

    }
   
}