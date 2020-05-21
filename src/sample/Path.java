package sample;

public class Path {
    
    static int[][] initialx=// x coordinate for Player pawns at their home position 
    {
        {3,4,3,4},//red x coordinates
        {11,12,11,12}//blue x coordinates

    };

    static int[][] initialy=// y coordinates for Player pawns at their home position
    {
        {2,2,1,1},//red y coordinates
        {29,9,10,10}//blue y coordinates
    };

    static int[][] xmove=
    {
        {2,3,4,5,6,6,6,6,6,7,8,8,8,8,8,9,10,11,12,13,13,13,12,11,10,9,8,8,8,8,8,7,6,6,6,6,6,5,4,3,2,1,1,2,3,4,5},//red
        {12,11,10,9,8,8,8,8,8,7,6,6,6,6,6,5,4,3,2,1,1,1,2,3,4,5,6,6,6,6,6,7,8,8,8,8,8,9,10,11,12,13,13,12,11,10,9}//blue
    };

    static int[][] ymove=
    {
        {7,7,7,7,7,8,9,10,11,11,11,10,8,9,7,7,7,7,7,7,6,5,5,5,5,5,5,4,3,2,1,1,1,2,3,4,5,5,5,5,5,5,6,6,6,6,6},
        {5,5,5,5,5,4,3,2,1,1,1,2,3,4,5,5,5,5,5,5,6,7,7,7,7,7,7,8,9,10,11,11,11,10,9,8,7,7,7,7,7,7,6,6,6,6,6}
    };
}