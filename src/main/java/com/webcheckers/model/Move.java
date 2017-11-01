package com.webcheckers.model;

public class Move {

    /*private int startX;
    private int startY;
    private int endX;
    private int endY;*/

    private Coordinate start;
    private Coordinate end;

    public Move(Coordinate _start, Coordinate _end) {
        this.start = _start;
        this.end = _end;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * This function captures a piece in the game
     * @param _pieceA this is the captured piece
     */
    public void capture(Piece _pieceA){

    }

    /**
     * This funtion is one which verifies if the move is forward or not
     * @param _posY is the Y co-ordinate that is being checked
     * @return
     */


    public boolean isMoveForward(int _posY){

        return true;
    }

    /**
     * This function verifies if the piece is captured or not
     * @param posX is the X co-ordinate of the piece
     * @param posY is the Y co-ordinate of the piece
     * @return
     */
    public boolean isCapture(int posX, int posY){

        return true;
    }

    public String toString() {
        return "Move: (Initial - R: " + start.getRow() + ", C: " + start.getCell() + ", Ending - R: " + end.getRow() + ", C: " + end.getCell() + ")";
    }
}


