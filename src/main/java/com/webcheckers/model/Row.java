
package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A class to represent one row of a Board.  This class instantiates a set of Squares with pieces in the row
 * The class implements Iterable<Space>
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 * @author <a href='mailto:ask5893@rit.edu'>Ashok Kesari</a>
 */
public class Row implements Iterable<Space>{
    //Attributes
    private List<Space> spaces;
    private int index;

    /**
     * This function is the constructor for the row class
     * @param yCoord this is they Y co-ordinate of the row
     */
    public Row(int yCoord) {
        spaces = new ArrayList<>();
        this.index = yCoord;

        for(int i=0; i<=7; i++) {
            if((yCoord == 0 || yCoord == 2) && i % 2 == 1) {
                //black square with white piece
                spaces.add(new Space(Space.SPACE_COLOR_BLACK, new Piece(PieceType.SINGLE, PieceColor.WHITE), i));
            } else if(yCoord == 1 && i % 2 == 0) {
                //black square with white piece
                spaces.add(new Space(Space.SPACE_COLOR_BLACK, new Piece(PieceType.SINGLE, PieceColor.WHITE), i));
            } else if((yCoord == 3 && i % 2 == 0) || (yCoord == 4 && i % 2 == 1)) {
                //Black square with no pieces
                spaces.add(new Space(Space.SPACE_COLOR_BLACK, null, i));
            } else if((yCoord == 5 || yCoord == 7) && i % 2 == 0) {
                //black square with red Piece
                spaces.add(new Space(Space.SPACE_COLOR_BLACK, new Piece(PieceType.SINGLE, PieceColor.RED), i));
            } else if(yCoord == 6 && i % 2 == 1) {
                //black square with red Piece
                spaces.add(new Space(Space.SPACE_COLOR_BLACK, new Piece(PieceType.SINGLE, PieceColor.RED), i));
            } else {
                //white square with no piece
                spaces.add(new Space(Space.SPACE_COLOR_WHITE, null, i));
            }
        }
    }

    /**
     * Overloaded parameterized constructor that accepts a predefined List of Spaces and a index value
     * @param _spaces A List of Space objects that comprise the row
     * @param yCoord The index of the row
     */
    public Row(List<Space> _spaces, int yCoord) {
        this.spaces = _spaces;
        this.index = yCoord;
    }

    /**
     * This is the accessor for the Index
     * @return the value of the index (specifies the row position)
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * this is the mutator for the Index
     * @param _index sets the index value
     */

    public void setIndex(int _index) {
        this.index = _index;
    }

    /**
     * This function is the accessor for Spaces
     * @return the spaces value
     */
    public List<Space> getSpaces() {
        return this.spaces;
    }

    /**
     * This function is the mutator for spaces
     * @param _spaces sets the value of spaces
     */
    public void setSpaces(List<Space> _spaces) {
        this.spaces = _spaces;
    }

    /**
     * A function to horizontally reverse the cells in a row
     * Used to provide different visual displays to each player
     * @return A new "flipped" row
     */
    public Row flipRow() {
        List<Space> cells = new ArrayList<>();

        //Loop through the row backwards and add each Space to the new List ("flipping" the row)
        for (int i = spaces.size()-1; i >= 0; i--) {
            cells.add(spaces.get(i));
        }

        return new Row(cells, this.index);
    }

    @Override
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    @Override
    public void forEach(Consumer<? super Space> action) {

    }

    @Override
    public Spliterator<Space> spliterator() {
        return null;
    }

}
