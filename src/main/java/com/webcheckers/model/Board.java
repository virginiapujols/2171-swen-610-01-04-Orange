package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Board implements Iterable<Row>{

    private List<Row> rows;
    private boolean didMove = false;
    private boolean didJump = false;


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
     * Accessor for didJump attribute
     * @return didJump Whether or not the user has jumped
     */
    public boolean getDidJump() {
        return didJump;
    }

    /**
     * Mutator for didJump attribute
     * @param _didJump Whether or not the user has jumped
     */
    public void setDidJump(boolean _didJump) {
        this.didMove = _didJump;
    }

    /**
     * Method to take a piece from the starting location in a move and place it at the ending location
     * @param _move A Data Object showing the starting & ending locations of a Piece when it is moved
     */
    public void movePiece(Move _move) {
        //Get starting and ending spaces & piece (using coordinates)
        Space startSpace = getSpaceByCoordinate(_move.getStart());
        Space endSpace = getSpaceByCoordinate(_move.getEnd());
        Piece piece = startSpace.getPiece();

        //Empty the starting space, put the piece in the end space, & signify that the user has moved
        startSpace.setPiece(null);
        endSpace.setPiece(piece);
        didMove = true;

        if(_move.getRowsMoved() == 2) {
            didJump = true;
            Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
            Space jumpedSpace = getSpaceByCoordinate(jumpedCoordinate);
            jumpedSpace.removeCapturedPiece();
        }
    }

    /**
     * A method to validate if a move is a legal move or not: illegal moves are prevented from occuring
     * @param _move The move that is being made
     * @return A message with text and type "info" it's a valid move or type "error" if it's an invalid move
     */
    public Message validateMove(Move _move) {
        Space startSpace = getSpaceByCoordinate(_move.getStart());
        Space endSpace = getSpaceByCoordinate(_move.getEnd());
        Piece piece = startSpace.getPiece();

        if(!didMove || (didMove && didJump)) { //If the user hasn't already moved OR has only made jumps
            if(endSpace.getPiece() == null) { //If there's already a piece in the ending space
                if (piece.getColor().equals("RED")) { //If the piece is red (i.e. if the piece moves "up" on the board)
                    if (_move.getRowsMoved() == 1 && !_move.isMoveUp() && !didJump) { //Ensure that a move is only 1 row "up"
                        return new Message("Valid Move", "info");
                    } else if(_move.getRowsMoved() == 2 && !_move.isMoveUp()) { //Check for a Jump, but it still has to be "up"
                        return validateJump(_move, piece);
                    } else {
                        String messageText = didJump ? "You cannot make a regular move after jumping!" : "You must move 1 row forward";

                        return new Message(messageText, "error");
                    }
                } else if (piece.getColor().equals("WHITE")) { //If the piece is white (i.e. the piece moves "down" on the board
                    if (_move.getRowsMoved() == 1 && _move.isMoveUp() && !didJump) { //Ensure that a move is only one row "down")
                        return new Message("Valid Move", "info");
                    } else if(_move.getRowsMoved() == 2 && _move.isMoveUp()) { //Check for a Jump, but it still has to be "down"
                        return validateJump(_move, piece);
                    } else {
                        String messageText = didJump ? "You cannot make a regular move after jumping!" : "You must move 1 row forward";

                        return new Message(messageText, "error");
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

    public Message validateJump(Move _move, Piece _jumpingPiece) {
        Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
        Space jumpedSpace = getSpaceByCoordinate(jumpedCoordinate);
        Piece jumpedPiece = jumpedSpace.getPiece();

        if(jumpedPiece != null) {
            if(!jumpedPiece.getColor().equals(_jumpingPiece.getColor())) {
                return new Message("Valid Move", "info");
            } else {
                return new Message("You cannot jump your own piece!", "error");
            }
        } else {
            return new Message("You cannot jump an empty square!", "error");
        }
    }

    /**
     * Method to undo a move that has been completed on a turn that hasn't been submitted yet
     * @param _move The move to be undone
     */
    public void undoMove(Move _move) {
        Space startSpace = getSpaceByCoordinate(_move.getStart());
        Space endSpace = getSpaceByCoordinate(_move.getEnd());
        Piece piece = endSpace.getPiece();

        //Set the piece back in the starting square and empty the ending square.  Indicate that the user now hasn't completed a move this turn
        startSpace.setPiece(piece);
        endSpace.setPiece(null);
        didMove = false;
    }

    public void undoCapture(Space _jumpedSpace, Piece _capturedPiece, int _stillCapturedCount) {
        _jumpedSpace.setPiece(_capturedPiece);

        if(_stillCapturedCount == 0) {
            didJump = false;
        }
    }

    public Space getSpaceByCoordinate(Coordinate _coord) {
        return rows.get(_coord.getRow()).getSpaces().get(_coord.getCell());
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
