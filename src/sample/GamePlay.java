package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GamePlay {
    private static Pane pane;
    //points on board differ by 38
    //x coordinates of all points on the board
    private static final double[] x = {166,204,242,280,318,356,356,356,356,356,356,394,432,
            432,432,432,432,432,470,508,546,584,622,660,660,660,   //starts from blue start point
            622,584,546,508,470,432,432,432,432,432,432,394,356,   //starts from green start point
            356,356,356,356,356,318,280,242,204,166,128,128,128};   //Starts from red start point

    //y coordinates of all points on the board
    private static final double[] y = {279,279,279,279,279,240,202,164,126,88 ,50 ,50 ,50 ,
            88 ,126,164,202,240,278,278,278,278,278,278,316,354,   //starts from blue start point
            354,354,354,354,354,392,430,468,506,544,582,582,582,    //starts from green start point
            544,506,468,430,392,354,354,354,354,354,354,316,278};   //Starts from red start point
    //x coordinates of home points on the board
    private static final double[][] xHome = {{166,204,242,280,318},     //player 1 home xpos
                                            {622,584,546,508,470}};     //player 2 home xpos
    //x coordinates of home points on the board
    private static final double[][] yHome = {{269.0,267.0,267.0,267.0},     //player 1 home ypos
                                            {2}};
    //Pieces for playing created as an array of  2 players x 4 pieces
    private static final Circle[][] piece1 = new Circle[2][4]; //[player][pieceNumber]
    //Current position of each player
    private static final int[] pos = {-1,25}; //{yellow,green}
    //private static int[] pos = {-1,70}; // for testing

    //Player 1 takes the first turn
    private static int turn = 0;
    //The piece number that a player is currently moving. Each element increases to signify change in piece number
    private static final int[] pieceNumber = {0,0};
    private static final boolean[] pieceCanStart = {false,false};

    public static void setUp(Pane p) {
        pane = p;
        for(int i=0;i<4;i++){
            piece1[0][i] = new Circle(0,0,12, Color.YELLOW);
            piece1[0][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[0][i]);
        }
        piece1[0][0].setLayoutX(165);
        piece1[0][0].setLayoutY(127);
        piece1[0][1].setLayoutX(203);
        piece1[0][1].setLayoutY(127);
        piece1[0][2].setLayoutX(165);
        piece1[0][2].setLayoutY(165);
        piece1[0][3].setLayoutX(203);
        piece1[0][3].setLayoutY(165);

        for(int i=0;i<4;i++){
            piece1[1][i] = new Circle(0,0,12, Color.GREEN);
            piece1[1][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[1][i]);
        }
        piece1[1][0].setLayoutX(583);
        piece1[1][0].setLayoutY(468);
        piece1[1][1].setLayoutX(624);
        piece1[1][1].setLayoutY(468);
        piece1[1][2].setLayoutX(583);
        piece1[1][2].setLayoutY(507);
        piece1[1][3].setLayoutX(624);
        piece1[1][3].setLayoutY(507);

    }

    public static void movePiece(int die) {

        if(!pieceCanStart[turn]) {
            if (die == 6) {
                //System.out.println("Die equals 6");
                pieceCanStart[turn] = true;
                pos[turn]++;
                piece1[turn][pieceNumber[turn]].setLayoutX(x[pos[turn]]);
                piece1[turn][pieceNumber[turn]].setLayoutY(y[pos[turn]]);
            } else {
                turn = (turn == 1)? 0:1;
            }
            return;
        }
        //System.out.println("Start Movement");


        pos[turn]+=die;

        if(turn==0){
            if(pos[turn]>55){
                if(pos[turn]!=56)pos[turn]-=die;//need exact number to go home
                else {
                    System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[turn][pieceNumber[turn]].setLayoutX(356 + pieceNumber[turn] * 2);
                    piece1[turn][pieceNumber[turn]].setLayoutY(316);

                    if (pieceNumber[turn] < 3) {
                        //changing piece being used
                        pieceNumber[turn]++; //since only 1 piece at a time

                        pos[turn] = -1; //new piece start from point 0
                        pieceCanStart[turn] = false;
                    } else {
                        System.out.println("Player 1 has won");//ALERT BOX SHOULD COME HERE

                    }
                }
            }
            else if(pos[turn]>=51){//going home
                piece1[turn][pieceNumber[turn]].setLayoutX(xHome[turn][pos[turn]-51]);
                piece1[turn][pieceNumber[turn]].setLayoutY(316);
            }
            else{
                piece1[turn][pieceNumber[turn]].setLayoutX(x[pos[turn]]);
                piece1[turn][pieceNumber[turn]].setLayoutY(y[pos[turn]]);
            }

        }
        //PLAYER 2
        else if(turn == 1){
            if(pos[turn]>81){
                if(pos[turn]!=82)pos[turn]-=die;//need exact number to go home
                else {
                    System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[turn][pieceNumber[turn]].setLayoutX(432 - pieceNumber[turn] * 2);
                    piece1[turn][pieceNumber[turn]].setLayoutY(316);

                    if (pieceNumber[turn] < 3) {
                        //changing piece being used
                        pieceNumber[turn]++; //since only 1 piece at a time

                        pos[turn] = 25; //new piece start from point 0
                        pieceCanStart[turn] = false;
                    } else {
                        System.out.println("Player 2 has won");//ALERT BOX SHOULD COME HERE
                        //End Game
                    }
                }
            }
            else if(pos[turn]>=77){
                piece1[turn][pieceNumber[turn]].setLayoutX(xHome[turn][ pos[turn]%53 -24]);
                piece1[turn][pieceNumber[turn]].setLayoutY(316);
            }
            else {
                piece1[turn][pieceNumber[turn]].setLayoutX(x[pos[turn] % 52]);
                piece1[turn][pieceNumber[turn]].setLayoutY(y[pos[turn] % 52]);
            }
        }


        turn = (die==6)? turn: (turn == 1)? 0:1;
    }
}
