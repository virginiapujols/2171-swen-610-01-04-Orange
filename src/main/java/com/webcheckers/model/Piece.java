package com.webcheckers.model;

public class Piece {
    private String type;
    private String color;

    public Piece(String _type, String _color) {
        this.type = _type;
        this.color = _color;
    }

    public void movePiece(int _posX, int _posY){ }

    public void capture(Piece _pieceA){ }

    public void bceomeKing(){}

    public boolean isMoveForward(int _posY){return true;}

    public  boolean isCapture(int _posX, int _posY ){return true;}

    public String getColor() {
        return this.color;
    }

    public String getType() {
        return this.type;
    }

    public void setColor(String _color) {
        this.color = _color;
    }

    public void setType(String _type) {
        this.type = _type;
    }
}


