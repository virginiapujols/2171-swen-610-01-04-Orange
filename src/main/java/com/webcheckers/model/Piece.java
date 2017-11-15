package com.webcheckers.model;

/**
 * A class that represents a movable token used to play Checkers
 * The class maintains and returns information on the pieces type and color
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 * @author <a href='mailto:nrd8504@rit.edu'>Niharika Dalal</a>
 * @author <a href='mailto:vp2532@rit.edu'>Virginia Pujols</a>
 * @author <a href='mailto:ask5893@rit.edu'>Ashok Kesari</a>
 */
public class Piece {
    //Attributes
    private PieceType type;
    private PieceColor color;
    private boolean justKinged;

     /**
     * This is the constructor for the "piece" class
     * @param _type denotes if the piece is a "plain" piece or a "king" piece
     * @param _color denotes if the piece is red or white
     */
    public Piece(PieceType _type, PieceColor _color) {
        this.type = _type;
        this.color = _color;
    }
    
    /**
     * This function is the accessor of color
     * @return the color of the piece
     */
    public PieceColor getColor() {
        return this.color;
    }
    
    /**
     * This function is the accessor of type
     * @return the type of piece (plain or king)
     */
    public PieceType getType() {
        return this.type;
    }
    
    /**
     * This fuction is the mutator of color
     * @param _color is the parameter that sets the color attribute
     */
    public void setColor(PieceColor _color) {
        this.color = _color;
    }

    /**
     * Mutator to set Piece type to SINGLE
     */
    public void makeSingle() {
        this.type = PieceType.SINGLE;
    }

    /**
     * Mutator to set Piece type to KING
     */
    public void makeKing() {
        this.type = PieceType.KING;
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


