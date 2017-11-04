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

    /**
     * A method to detect whether or not a move is "up"
     * "Up" is defined as the ending row having a higher value than the starting row, so that is towards the red side
     * @return Whether or not the end row is greater than the starting row
     */
    public boolean isMoveUp() {
        return end.getRow() > start.getRow();
    }

    /**
     * A method to get the coordinate that is jumped over when a Jump occurs
     * @return The coordinate of the jumped space
     */
    public Coordinate getJumpedCoordinate() {
        int rowVal = (start.getRow() + end.getRow())/2;
        int cellVal = (start.getCell() + end.getCell())/2;

        return new Coordinate(rowVal, cellVal);
    }

    public String toString() {
        return "Move: (Initial - R: " + start.getRow() + ", C: " + start.getCell() + ", Ending - R: " + end.getRow() + ", C: " + end.getCell() + ")";
    }
}


