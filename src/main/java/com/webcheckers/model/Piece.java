package com.webcheckers.model;

/**
 * A class that represents a movable token used to play Checkers
 * The class maintains and returns information on the pieces type and color
 */
public class Piece {
    //Constants
    public static final String PIECE_RED = "RED";
    public static final String PIECE_WHITE = "WHITE";

    //Attributes
    private String type;
    private String color;
    private boolean justKinged;

     /**
     * This is the constructor for the "piece" class
     * @param _type denotes if the piece is a "plain" piece or a "king" piece
     * @param _color denotes if the piece is red or white
     */
    public Piece(String _type, String _color) {
        this.type = _type;
        this.color = _color;
    }

    /**
     * This function denotes if the piece is captured or not
     * @param _posX this is the X co-ordinate of the piece
     * @param _posY this is the Y co-ordinate of the piece
     * @return a Boolean "true" or "false"
     */
    public  boolean isCapture(int _posX, int _posY ){return true;}
    
    /**
     * This function is the accessor of color
     * @return the color of the piece
     */
    public String getColor() {
        return this.color;
    }
    
    /**
     * This function is the accessor of type
     * @return the type of piece (plain or king)
     */
    public String getType() {
        return this.type;
    }
    
    /**
     * This fuction is the mutator of color
     * @param _color is the parameter that sets the color attribute
     */
    public void setColor(String _color) {
        this.color = _color;
    }

    /**
     * Mutator to set Piece type to SINGLE
     */
    public void makeSingle() {
        this.type = "SINGLE";
    }

    /**
     * Mutator to set Piece type to KING
     */
    public void makeKing() {
        this.type = "KING";
        this.justKinged = true;
    }

    /**
     * Accessor for justKinged attribute
     * @return justKinged If the Piece was Kinged this turn
     */
    public boolean getJustKinged() {
        return this.justKinged;
    }

    /**
     * Mutator for justKinged attribute
     * @param _justKinged Whether or not the Piece was Kinged this turn
     */
    public void setJustKinged(boolean _justKinged) {
        this.justKinged = _justKinged;
    }
}


