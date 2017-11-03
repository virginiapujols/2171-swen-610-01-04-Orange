package com.webcheckers.model;

public class Move {

    //Attributes
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

    public int getRowsMoved() {
        return Math.abs(start.getRow() - end.getRow());
    }

    public boolean isMoveUp() {
        return end.getRow() > start.getRow();
    }

    public Coordinate getJumpedCoordinate() {
        int rowVal = (start.getRow() + end.getRow())/2;
        int cellVal = (start.getCell() + end.getCell())/2;

        return new Coordinate(rowVal, cellVal);
    }

    public String toString() {
        return "Move: (Initial - R: " + start.getRow() + ", C: " + start.getCell() + ", Ending - R: " + end.getRow() + ", C: " + end.getCell() + ")";
    }
}


