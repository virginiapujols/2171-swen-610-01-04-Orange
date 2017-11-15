package com.webcheckers.model;

import java.util.Objects;

/**
 * Value Object to represent a Coordinate pointing to a Space
 * A Coordinate is 2 ints representing a location on the board
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class Coordinate {
    //Attributes
    private int row;
    private int cell;

    /**
     * Default constructor for the class
     * @param _row The Row position the coordinate is pointing to
     * @param _cell The cell's position in the row
     */
    public Coordinate(int _row, int _cell) {
        this.row = _row;
        this.cell = _cell;
    }

    /**
     * Accessor for the row property
     * @return row The position of the row (row.index)
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Accessor for the cell property
     * @return cell The position of the cell (cell.cellIdx)
     */
    public int getCell() {
        return this.cell;
    }

    /**
     * Equality test method for the object
     * Evaluates equality on if the row & cell values match
     * @param coord A coordinate to check for comparison
     * @return
     */
    @Override
    public boolean equals(Object coord) {
        if(coord == this) return true;
        if(!(coord instanceof Coordinate)) return false;
        final Coordinate that = (Coordinate) coord;
        return this.row == that.row && this.cell == that.cell;
    }

    /**
     * A method to return a hash of the object
     * @return A hash of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }
}
