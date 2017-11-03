package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Board implements Iterable<Row>{

    private List<Row> rows;
    private boolean didMove = false;


    /**
     * This is the constructor for the "Board" class.
     */
    public Board() {
        rows = new ArrayList<>();
        for(int i=0; i<=7; i++) {
            rows.add(new Row(i));
        }
    }
   
    /**
     * This function updates the board after moves
     * @return the updated board
     */
    public Board updateBoard() {
        Board board = new Board();
        return board;
    }

    /**
     * Accessor for rows attribute
     * @return rows The Rows on the board
     */
    public List<Row> getRows() {
        return this.rows;
    }

    /**
     * Mutator for rows attribute
     * @param _rows The rows on the board
     */
    public void setRows(List<Row> _rows) {
        this.rows = _rows;
    }

    /**
     * Accessor for didMove attribute
     * @return didMove Whether or not the user has moved
     */
    public boolean didMove() {
        return didMove;
    }

    /**
     * Mutator for didMove attribute
     * @param _didMove Whether or not the user has moved
     */
    public void setDidMove(boolean _didMove) {
        this.didMove = _didMove;
    }

    /**
     * Method to take a piece from the starting location in a move and place it at the ending location
     * @param move A Data Object showing the starting & ending locations of a Piece when it is moved
     */
    public void movePiece(Move move) {
        //Get starting & ending coordinates
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        //Get starting and ending spaces & piece (using coordinates)
        Space startSpace = rows.get(startRow).getSpaces().get(startCell);
        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Piece piece = startSpace.getPiece();

        //Empty the starting space, put the piece in the end space, & signify that the user has moved
        startSpace.setPiece(null);
        endSpace.setPiece(piece);
        didMove = true;
    }

    /**
     * A method to validate if a move is a legal move or not: illegal moves are prevented from occuring
     * @param move The move that is being made
     * @return A message with text and type "info" it's a valid move or type "error" if it's an invalid move
     */
    public Message validateMove(Move move) {
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Space startSpace = rows.get(startRow).getSpaces().get(startCell);
        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Piece piece = startSpace.getPiece();

        if(!didMove) { //If the user hasn't already moved:
            if(endSpace.getPiece() == null) { //If there's already a piece in the ending space
                if (piece.getColor().equals("RED")) { //If the piece is red (i.e. if the piece moves "up" on the board)
                    if (endRow == startRow - 1) { //Ensure that a move is only 1 row "up"
                        return new Message("Valid Move", "info");
                    } else {
                        return new Message("You must move 1 row forward", "error");
                    }
                } else if (piece.getColor().equals("WHITE")) { //If the piece is white (i.e. the piece moves "down" on the board
                    if (endRow == startRow + 1) { //Ensure that a move is only one row "down")
                        return new Message("Valid Move", "info");
                    } else {
                        return new Message("You must move 1 row forward", "error");
                    }
                }
            } else { //If there's a piece in the ending location, show an error message
                return new Message("A Piece is already in that Square", "error");
            }
        } else { //If they have already moved, show an error message
            return new Message("You have already made a move this turn", "error");
        }

        //catch all that returns a generic error message
        return new Message("Invalid Move", "error");
    }

    /**
     * Method to undo a move that has been completed on a turn that hasn't been submitted yet
     * @param move The move to be undone
     */
    public void undoMove(Move move) {
        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Space startSpace = rows.get(startRow).getSpaces().get(startCell);
        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Piece piece = endSpace.getPiece();

        //Set the piece back in the starting square and empty the ending square.  Indicate that the user now hasn't completed a move this turn
        startSpace.setPiece(piece);
        endSpace.setPiece(null);
        didMove = false;
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    @Override
    public void forEach(Consumer<? super Row> action) {

    }

    @Override
    public Spliterator<Row> spliterator() {
        return null;
    }
}
