package com.webcheckers.model;

public class Move {

    private int startRow;
    private int startCell;
    private int endRow;
    private int endCell;

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCell() {
        return startCell;
    }

    public void setStartCell(int startCell) {
        this.startCell = startCell;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getEndCell() {
        return endCell;
    }

    public void setEndCell(int endCell) {
        this.endCell = endCell;
    }

    /**
     * This is the function that moves a piece to the required co-ordinates.
     * @param _posX this is the X co-ordinate
     * @param _posY this is the Y co-ordinate
     */
    public void movePiece(int _posX, int _posY){

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

    public boolean isValidMoveForward() {
        return (endCell == (startCell + 1) && (endRow == (startRow-1) || endRow == (startRow+1)));
    }
}


