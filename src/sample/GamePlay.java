package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.server.SQLiteJDBC;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class GamePlay {
    private static Pane pane;
    private static String socketName;
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
                                            {394},                      //player 2 home xpos
                                            {622,584,546,508,470},      //player 3 home xpos
                                            {123}};                     //player 4 home xpos
    //x coordinates of home points on the board
    private static final double[][] yHome = {{267.0},     //player 1 home ypos
                                            {88,126,164,202,240},
                                            {123},
                                            {546,508,470,432,394/*work on*/}};
    //Pieces for playing created as an array of  2 players x 4 pieces
    private static final Circle[][] piece1 = new Circle[4][4]; //[player][pieceNumber]
    //Current position of each player
    private static int[] pos;

    //Player 1 takes the first turn
    private static int turn;
    private static int[] colorPosition;
    //The piece number that a player is currently moving. Each element increases to signify change in piece number
    private static int[] pieceNumber;//should be 0,0
    private static boolean[] pieceCanStart;
    private static ComboBox<String> colorPicker;

    private static int playerNumber;

    public static void setPlayerNumber(int playerNumber) {
        GamePlay.playerNumber = playerNumber;
        colorPosition[GamePlay.playerNumber] = tempNumber;
        NewGame.playerColor.setFill(getColorPosition());
        NewGame.playerNum = new Text("Player " + (playerNumber)+":");
    }

    public static int[] takenColors = {5,5,5};

    public static void setUp(Pane p, String s) {
        pane = p;
        socketName = s;
        pos = new int[]{-1,12,25,38};
        turn = 0;
        colorPosition = new int[]{0,0,0,0};
        pieceNumber = new int[]{0,0,0,0};
        pieceCanStart = new boolean[]{false,false,false,false};
        playerNumber=0;

        for(int i=0;i<4;i++){
            piece1[0][i] = new Circle(0,0,12, Color.YELLOW);
            piece1[0][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[0][i]);
        }
        piece1[0][0].setLayoutX(195);
        piece1[0][0].setLayoutY(127);
        piece1[0][1].setLayoutX(233);
        piece1[0][1].setLayoutY(127);
        piece1[0][2].setLayoutX(195);
        piece1[0][2].setLayoutY(165);
        piece1[0][3].setLayoutX(233);
        piece1[0][3].setLayoutY(165);

        for(int i=0;i<4;i++){
            piece1[1][i] = new Circle(0,0,12, Color.BLUE);
            piece1[1][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[1][i]);
        }
        piece1[1][0].setLayoutX(535);
        piece1[1][0].setLayoutY(127);
        piece1[1][1].setLayoutX(577);
        piece1[1][1].setLayoutY(127);
        piece1[1][2].setLayoutX(535);
        piece1[1][2].setLayoutY(165);
        piece1[1][3].setLayoutX(577);
        piece1[1][3].setLayoutY(165);

        for(int i=0;i<4;i++){
            piece1[2][i] = new Circle(0,0,12, Color.GREEN);
            piece1[2][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[2][i]);
        }
        piece1[2][0].setLayoutX(535);
        piece1[2][0].setLayoutY(468);
        piece1[2][1].setLayoutX(577);
        piece1[2][1].setLayoutY(468);
        piece1[2][2].setLayoutX(535);
        piece1[2][2].setLayoutY(507);
        piece1[2][3].setLayoutX(577);
        piece1[2][3].setLayoutY(507);

        for(int i=0;i<4;i++){
            piece1[3][i] = new Circle(0,0,12, Color.RED);
            piece1[3][i].setStroke(Color.BLACK);
            pane.getChildren().add(piece1[3][i]);
        }
        piece1[3][0].setLayoutX(195);
        piece1[3][0].setLayoutY(468);
        piece1[3][1].setLayoutX(233);
        piece1[3][1].setLayoutY(468);
        piece1[3][2].setLayoutX(195);
        piece1[3][2].setLayoutY(507);
        piece1[3][3].setLayoutX(233);
        piece1[3][3].setLayoutY(507);

        if(socketName==null){
            colorPosition[0] = getNumber();
            colorPosition[1] = (colorPosition[0]+1)%4;//Play Attention
        }

    }

    public static void movePiece(int die) {
        //System.out.println(colorPosition[turn]+","+turn);
        int n = colorPosition[turn];
        if(!pieceCanStart[n]) {
            if (die == 6) {
                //System.out.println("Die equals 6");
                pieceCanStart[n] = true;
                pos[n]++;
                piece1[n][pieceNumber[n]].setLayoutX(x[pos[n]]);
                piece1[n][pieceNumber[n]].setLayoutY(y[pos[n]]);
            } else {
                turn = (turn+1)%2;//PAY ATTENTION TO THIS. ONLY 2 CAN PLAY
            }
            return;
        }
        //System.out.println("Start Movement");


        pos[n]+=die;

        //PLAYER 1
        if(n==0){
            if(pos[n]>55){
                if(pos[n]!=56)pos[n]-=die;//need exact number to go home
                else {
                    //System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[n][pieceNumber[n]].setLayoutX(356 + pieceNumber[n] * 2);
                    piece1[n][pieceNumber[n]].setLayoutY(316);

                    if (pieceNumber[n] < 3) {
                        //changing piece being used
                        pieceNumber[n]++; //since only 1 piece at a time

                        pos[n] = -1; //new piece start from point 0
                        pieceCanStart[n] = false;
                    } else {
                        //System.out.println("Player 1 has won");//ALERT BOX SHOULD COME HERE
                        if(socketName == null){
                            Alert.display("Player 1  Won");
                            SQLiteJDBC.hasWon();
                        }
                        else{//multiplayer
                            if(playerNumber == turn) {
                                NewGame.sendMessage("won");
                                Alert.display("You  Won");
                                SQLiteJDBC.hasWon();
                            }
                        }
                        NewGame.ExitGame();
                    }
                }
            }
            else if(pos[n]>=51){//going home
                piece1[n][pieceNumber[n]].setLayoutX(xHome[n][pos[n]-51]);
                piece1[n][pieceNumber[n]].setLayoutY(316);
            }
            else{
                piece1[n][pieceNumber[n]].setLayoutX(x[pos[n]]);
                piece1[n][pieceNumber[n]].setLayoutY(y[pos[n]]);
            }

        }
        //PLAYER 2
        else if(n == 1){
            if(pos[n]>68){
                if(pos[n]!=69)pos[n]-=die;//need exact number to go home
                else {
                    //System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[n][pieceNumber[n]].setLayoutX(390);
                    piece1[n][pieceNumber[n]].setLayoutY(280 + pieceNumber[n] * 2);

                    if (pieceNumber[n] < 3) {
                        //changing piece being used
                        pieceNumber[n]++; //since only 1 piece at a time

                        pos[n] = 12; //new piece start from point 0
                        pieceCanStart[n] = false;
                    } else {
                        //System.out.println("Player 2 has won");//ALERT BOX SHOULD COME HERE
                        if(socketName == null){
                            Alert.display("Player 2  Won");
                            SQLiteJDBC.haslost();
                        }
                        else{//multiplayer
                            if(playerNumber == turn) {
                                NewGame.sendMessage("won");
                                Alert.display("You  Won");
                                SQLiteJDBC.hasWon();
                            }
                        }
                        NewGame.ExitGame();
                    }
                }
            }
            else if(pos[n]>=64){
                piece1[n][pieceNumber[n]].setLayoutX(394);
                piece1[n][pieceNumber[n]].setLayoutY(yHome[n][ pos[n]%53 -11]);
            }
            else {
                piece1[n][pieceNumber[n]].setLayoutX(x[pos[n] % 52]);
                piece1[n][pieceNumber[n]].setLayoutY(y[pos[n] % 52]);
            }
        }

        //PLAYER 3
        else if(n == 2){
            if(pos[n]>81){
                if(pos[n]!=82)pos[n]-=die;//need exact number to go home
                else {
                    //System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[n][pieceNumber[n]].setLayoutX(432 - pieceNumber[n] * 2);
                    piece1[n][pieceNumber[n]].setLayoutY(316);

                    if (pieceNumber[n] < 3) {
                        //changing piece being used
                        pieceNumber[n]++; //since only 1 piece at a time

                        pos[n] = 25; //new piece start from point 0
                        pieceCanStart[n] = false;
                    } else {
                        //System.out.println("Player 3 has won");//ALERT BOX SHOULD COME HERE
                        if(socketName == null){
                            Alert.display("Player 3  Won");
                            SQLiteJDBC.haslost();
                        }
                        else{//multiplayer
                            if(playerNumber == turn) {
                                NewGame.sendMessage("won");
                                Alert.display("You  Won");
                                SQLiteJDBC.hasWon();
                            }
                        }
                        NewGame.ExitGame();
                    }
                }
            }
            else if(pos[n]>=77){
                piece1[n][pieceNumber[n]].setLayoutX(xHome[n][ pos[n]%53 -24]);
                piece1[n][pieceNumber[n]].setLayoutY(316);
            }
            else {
                piece1[n][pieceNumber[n]].setLayoutX(x[pos[n] % 52]);
                piece1[n][pieceNumber[n]].setLayoutY(y[pos[n] % 52]);
            }
        }


        //PLAYER 4
        else if(n == 3){
            if(pos[n]>94){
                if(pos[n]!=95)pos[n]-=die;//need exact number to go home
                else {
                    //System.out.println("Piece Home");
                    //setting pieces in home position
                    piece1[n][pieceNumber[n]].setLayoutX(390);
                    piece1[n][pieceNumber[n]].setLayoutY(355 - pieceNumber[n] * 2);

                    if (pieceNumber[n] < 3) {
                        //changing piece being used
                        pieceNumber[n]++; //since only 1 piece at a time

                        pos[n] = 38; //new piece start from point 0
                        pieceCanStart[n] = false;
                    } else {
                        //System.out.println("Player 4 has won");//ALERT BOX SHOULD COME HERE
                        if(socketName == null){
                            Alert.display("Player 4  Won");
                            SQLiteJDBC.haslost();
                        }
                        else{//multiplayer
                            if(playerNumber == turn) {
                                NewGame.sendMessage("won");
                                Alert.display("You  Won");
                                SQLiteJDBC.hasWon();
                            }
                        }
                        NewGame.ExitGame();
                    }
                }
            }
            else if(pos[n]>=90){//77
                piece1[n][pieceNumber[n]].setLayoutX(394);
                piece1[n][pieceNumber[n]].setLayoutY(yHome[n][ pos[n]%53 -37]);
            }
            else {
                piece1[n][pieceNumber[n]].setLayoutX(x[pos[n] % 52]);
                piece1[n][pieceNumber[n]].setLayoutY(y[pos[n] % 52]);
            }
        }

        turn = (die==6)? turn: (turn == 1)? 0:1;
    }

    private static int tempNumber;
    public static void pickColor(){
        tempNumber = getNumber();
        //System.out.println("pciking cole");
        NewGame.sendMessage("color:"+tempNumber);
    }

    public static int getNumber(){
        AtomicInteger number = new AtomicInteger();
            colorPicker = new ComboBox<>();
            colorPicker.getItems().addAll("Yellow","Blue","Green","Red");
            if(takenColors[0]<5){
                resetColors();
            }

            colorPicker.setPromptText("Pick a color");
            Stage colorPickStage = new Stage();
            colorPickStage.setX(Main.window.getX()+300);
            colorPickStage.setY(Main.window.getY()+450);
            colorPickStage.setTitle("Select Color");
            colorPickStage.initStyle(StageStyle.UNDECORATED);
            colorPickStage.initModality(Modality.APPLICATION_MODAL);

            HBox hBox = new HBox(10);
            hBox.setPadding(new Insets(10));
            Button ok = new Button("Ok");
            ok.setOnAction(e->{
                if(colorPicker.getValue() != null){
                    switch (colorPicker.getValue()){
                        case "Yellow":
                            number.set(0);
                            break;
                        case "Blue":
                            number.set(1);
                            break;
                        case "Green":
                            number.set(2);
                            break;
                        default:
                            number.set(3);
                    }
                    colorPickStage.close();
                }

            });

            hBox.getChildren().addAll(colorPicker,ok);
            hBox.setBackground(new Background(new BackgroundFill(Color.color(0.5,0.5,0.9,0.8),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            colorPickStage.setScene(new Scene(hBox));
            colorPickStage.showAndWait();
        return number.get();
    }

    public static int getTurn(){return turn;}

    public static Color getColorPosition(){
        //System.out.println(colorPosition[playerNumber]+","+playerNumber);
        switch (colorPosition[playerNumber]){
            case 0:
                return Color.YELLOW;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            default:
                return Color.RED;
        }
    }

    public static void setPlayerColors(int i, String s) {
        colorPosition[i] = Integer.parseInt(s);
        NewGame.playerNum = new Text("Player " + (playerNumber)+":");
    }

    public static void resetColors(){
        colorPicker.getItems().remove(takenColors[0]);
    }
}
