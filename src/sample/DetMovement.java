package sample;




public class DetMovement {

    
    private static ColourPiece colour = new ColourPiece();
    private static int pieceLeft = colour.getNumOfPiece();
   
    private static void makeMovement(int play){
        if(play == 6 & pieceLeft == 4){
            startMovement();
        }else{
            updateMovement(play);
        }
    
    }
    public  static void startMovement(){



    }
    private static void updateMovement(int plays){
        colour.movePiece(plays);
    }
  
    
    
}