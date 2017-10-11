    package com.webcheckers.model;

public class Piece {
    private String type;
    private String color;

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
     * This function denotes the transformation of a "plain" piece to a "king" piece
     */
    public void bceomeKing(){}

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
     * This function is the mutator of type
     * @param _type is the parameter that sets the type attribute
     */
    public void setType(String _type) {
        this.type = _type;
    }
}


