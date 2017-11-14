package com.webcheckers.model;

/**
 * A class that represents a move from one location to another.  Responsible for providing information about that specific move
 * (i.e. the move's direction, the move's magnitude, etc)
 */
public class Move {
    //Attributes
    private Coordinate start;
    private Coordinate end;

    /**
     * Parameterized Constructor for the Move Class
     * @param _start The starting coordinate of the Move
     * @param _end The ending coordinate of the Move
     */
    public Move(Coordinate _start, Coordinate _end) {
        this.start = _start;
        this.end = _end;
    }

    /**
     * Accessor for starting position
     * @return start The Starting Coordinate (row, cell)
     */
    public Coordinate getStart() {
        return start;
    }

    /**
     * Accessor for ending position
     * @return end The Ending Coordinate (row, cell)
     */
    public Coordinate getEnd() {
        return end;
    }

    /**
     * A method to calculate the number of rows moved by a piece in a Move
     * @return The number of rows the ending Coordinate is away from the starting space
     */
    public int getRowsMoved() {
        return Math.abs(start.getRow() - end.getRow());
    }

    /**
     * A method to calculate the number of cells moved by a piece in a Move
     * @return The number of cells the ending Coordinate is away from the starting space
     */
    public int getCellsMoved() {
        return Math.abs(start.getCell() - end.getCell());
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


