package com.webcheckers.model;

public class Space {

   private String color;
   private Piece piece;
   private int cellIdx;


   public Space(String _color, Piece _piece, int _cellIdx) {
       this.color = _color;
       this.piece = _piece;
       this.cellIdx = _cellIdx;
   }

   public String getColor() {
       return this.color;
   }

    public Piece getPiece() {
       return this.piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public void setColor(String _color) {
       this.color = _color;
    }

    public void setPiece(Piece _piece) {
       this.piece = _piece;
    }

    public void setCellIdx(int _cellIdx) {
       this.cellIdx = _cellIdx;
    }

    public boolean isValid() {
       return this.piece == null && this.color.equals("black");
    }
}
