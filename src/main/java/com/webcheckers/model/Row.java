
package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Row implements Iterable<Space>{
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
                //black square with black piece
                spaces.add(new Space("black", new Piece("SINGLE", "WHITE"), i));
            } else if(yCoord == 1 && i % 2 == 0) {
                //black square with black piece
                spaces.add(new Space("black", new Piece("SINGLE", "WHITE"), i));
            } else if((yCoord == 3 && i % 2 == 0) || (yCoord == 4 && i % 2 == 1)) {
                //Black square with no pieces
                spaces.add(new Space("black", null, i));
            } else if((yCoord == 5 || yCoord == 7) && i % 2 == 0) {
                //black square with white Piece
                spaces.add(new Space("black", new Piece("SINGLE", "RED"), i));
            } else if(yCoord == 6 && i % 2 == 1) {
                //black square with white Piece
                spaces.add(new Space("black", new Piece("SINGLE", "RED"), i));
            } else {
                //white square with no piece
                spaces.add(new Space("white", null, i));
            }
        }
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
}
