package com.webcheckers.model;

import java.util.Objects;

public class Coordinate {
    private int row;
    private int cell;

    public Coordinate(int _row, int _cell) {
        this.row = _row;
        this.cell = _cell;
    }

    public int getRow() {
        return this.row;
    }

    public int getCell() {
        return this.cell;
    }

    @Override
    public boolean equals(Object coord) {
        if(coord == this) return true;
        if(!(coord instanceof Coordinate)) return false;
        final Coordinate that = (Coordinate) coord;
        return this.row == that.row && this.cell == that.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, cell);
    }
}
