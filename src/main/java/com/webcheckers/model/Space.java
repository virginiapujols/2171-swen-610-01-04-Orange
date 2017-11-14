package com.webcheckers.model;

/**
 * A class that represents a location on the game board
 * This class holds a piece and provides information about itself and gives access to its piece
 */
public class Space {
    // Constants
    public static final String  SPACE_COLOR_BLACK = "black";
    public static final String SPACE_COLOR_WHITE = "white";

    // Attributes
    private String color;
    private Piece piece;
    private int cellIdx;

    /**
     * This is the constructor for the Space class
     * @param _color is the color of the space
     * @param _piece is the piece in the space
     * @param _cellIdx is the x-id of the space
     */
    public Space(String _color, Piece _piece, int _cellIdx) {
        this.color = _color;
        this.piece = _piece;
        this.cellIdx = _cellIdx;
    }

    /**
     * This function is the accessor of Color
     * @return this is the color of the space
     */
    public String getColor() {
        return this.color;
    }

    /**
     * This function is the accessor of the piece
     * @return this returns the piece in the space
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * This function is the accessor for the cell ID
     * @return the cell ID
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * This is the mutator for the color
     * @param _color this sets the color of the space
     */
    public void setColor(String _color) {
        this.color = _color;
    }

    /**
     * This is the mutator of the piece
     * @param _piece sets the value of piece in the space
     */
    public void setPiece(Piece _piece) {
        this.piece = _piece;
    }

    /**
     * This is the mutator of the cell ID
     * @param _cellIdx this sets the cell ID
     */
    public void setCellIdx(int _cellIdx) {
        this.cellIdx = _cellIdx;
    }

    /**
     * This function returns if it is valid or nor
     * @return a Boolean "true" or "false"
     */
    public boolean isValid() {
        return this.piece == null && this.color.equals(SPACE_COLOR_BLACK);
    }

    public void removeCapturedPiece() {
        piece = null;
    }
}
