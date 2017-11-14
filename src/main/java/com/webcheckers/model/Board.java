package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A class that represents a game board.  This class enforces move logic and tracks piece locations
 */
public class Board implements Iterable<Row>{
    //Constants
    public static final String JUMP_AFTER_MOVE_ERROR  = "You cannot make a regular move after jumping!";
    public static final String INVALID_ROW_OR_CELL_ERROR = "You must move diagonally 1 cell forward";
    public static final String VALID_MOVE = "Valid Move";

    //Class Attributes
    private List<Row> rows;
    private boolean didMove = false; //whether or not the user has moved this turn
    private boolean didJump = false; //whether or not the user has jumped this turn

    //Public Methods


    /**
     * Constructor for the "Board" class.
     */
    public Board() {
        rows = new ArrayList<>();
        for(int i=0; i<=7; i++) {
            rows.add(new Row(i));
        }
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
        this.didJump = _didJump;
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

        //If the move is a jump, set didJump to true, move the piece to it's new space, and delete the captured piece
        if(_move.getRowsMoved() == 2) {
            didJump = true;
            Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
            Space jumpedSpace = getSpaceByCoordinate(jumpedCoordinate);
            jumpedSpace.removeCapturedPiece();
        }

        //If the move results in a piece reaching the last row, make it a king
        if(reachedLastRow(_move.getEnd(), piece)) {
            piece.makeKing();
        }
    }

    /**
     * A method to validate if a move is a legal move or not: illegal moves are prevented from occuring
     * @param _move The move that is being made
     * @return A message with text and type "info" it's a valid move or type "error" if it's an invalid move
     */
    public Message validateMove(Move _move) {
        //Get the spaces and piece involved in the move
        Space startSpace = getSpaceByCoordinate(_move.getStart());
        Space endSpace = getSpaceByCoordinate(_move.getEnd());
        Piece piece = startSpace.getPiece();

        //Check if the user has a jump available, requiring them to take it if one exists
        if(_move.getRowsMoved() == 1 && piece.getColor().equals(PieceColor.RED) && checkForAvailableJumps(PieceColor.RED)) {
            return new Message("You have a jump that you must take", MessageStatus.error);
        } else if(_move.getRowsMoved() == 1 && piece.getColor().equals(PieceColor.WHITE) && checkForAvailableJumps(PieceColor.WHITE)) {
            return new Message("You have a jump that you must take", MessageStatus.error);
        }

        //If they don't have any available jumps, allow them to make a move and evaluate it's validity
        if(!didMove || (didMove && didJump)) { //If the user hasn't already moved OR has only made jumps
            if(endSpace.getPiece() == null) { //If there's already a piece in the ending space
                if(piece.getType().equals(PieceType.SINGLE)) { //If the piece is a single piece
                    if (piece.getColor().equals(PieceColor.RED)) { //If the piece is red (i.e. if the piece moves "up" on the board)
                        if (_move.getRowsMoved() == 1 && _move.getCellsMoved() == 1 && !_move.isMoveUp() && !didJump) { //Ensure that a move is only 1 row "up"
                            return new Message(VALID_MOVE, MessageStatus.info);
                        } else if (_move.getRowsMoved() == 2 && !_move.isMoveUp()) { //Check for a Jump, but it still has to be "up"
                            return validateJump(_move, piece);
                        } else { //Otherwise return an error message
                            String messageText = didJump ? JUMP_AFTER_MOVE_ERROR : INVALID_ROW_OR_CELL_ERROR;
                            return new Message(messageText, MessageStatus.error);
                        }
                    } else if (piece.getColor().equals(PieceColor.WHITE)) { //If the piece is white (i.e. the piece moves "down" on the board
                        if (_move.getRowsMoved() == 1 && _move.getCellsMoved() == 1 && _move.isMoveUp() && !didJump) { //Ensure that a move is only one row "down"
                            return new Message(VALID_MOVE, MessageStatus.info);
                        } else if (_move.getRowsMoved() == 2 && _move.isMoveUp()) { //Check for a Jump, but it still has to be "down"
                            return validateJump(_move, piece);
                        } else { //Otherwise return an error message
                            String messageText = didJump ? JUMP_AFTER_MOVE_ERROR : INVALID_ROW_OR_CELL_ERROR;
                            return new Message(messageText, MessageStatus.error);
                        }
                    }
                } else if(piece.getType().equals(PieceType.KING)) { //If the piece is a King Piece, it has special move logic
                    if(_move.getRowsMoved() == 1 && _move.getCellsMoved() == 1 && !didJump) { //A king piece can move 1 row in any direction
                        return new Message(VALID_MOVE, MessageStatus.info);
                    } else if(_move.getRowsMoved() == 2) { //A king piece can jump 2 rows in any direction, return jump validation
                        return validateJump(_move, piece);
                    } else { //Otherwise return an error message
                        String messageText = didJump ? JUMP_AFTER_MOVE_ERROR : INVALID_ROW_OR_CELL_ERROR;
                        return new Message(messageText, MessageStatus.error);
                    }
                }
            } else { //If there's a piece in the ending location, show an error message
                return new Message("A Piece is already in that Square", MessageStatus.error);
            }
        } else { //If they have already moved, show an error message
            return new Message("You have already made a move this turn", MessageStatus.error);
        }

        //catch all that returns a generic error message
        return new Message("Invalid Move", MessageStatus.error);
    }

    /**
     * A method to validate whether or not a jump is legal
     * This method is called by validateMove when it detects that a jump has occured
     * @param _move The move being made
     * @param _jumpingPiece The piece object that is performing the move
     * @return A Message indicating the Move's Validity
     */
    public Message validateJump(Move _move, Piece _jumpingPiece) {
        //Get the space & piece of the move
        Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
        Space jumpedSpace = getSpaceByCoordinate(jumpedCoordinate);
        Piece jumpedPiece = jumpedSpace.getPiece();

        if(jumpedPiece != null) { //If the jumped piece isn't null, check that it's the opposing color and return a corresponding message
            if(!jumpedPiece.getColor().equals(_jumpingPiece.getColor())) {
                return new Message("Valid Move", MessageStatus.info);
            } else {
                return new Message("You cannot jump your own piece!", MessageStatus.error);
            }
        } else {
            return new Message("You cannot jump an empty square!", MessageStatus.error);
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

    /**
     * A method to undo a the capture of a piece (used when a piece is captured and then a move is undone)
     * @param _jumpedSpace The space that was jumped in the move
     * @param _capturedPiece The piece that was captured in the jump
     * @param _stillCapturedCount The count of pieces captured this turn that are still captured (i.e. If 3 were captured and 1 capture was undone, it would be 2)
     */
    public void undoCapture(Space _jumpedSpace, Piece _capturedPiece, int _stillCapturedCount) {
        _jumpedSpace.setPiece(_capturedPiece);

        if(_stillCapturedCount == 0) { //If undoing the move means they haven't captured any pieces, set "didJump" to false
            didJump = false;
        }
    }

    /**
     * A method to get a Space by a given coordinate
     * @param _coord The coordinate that points at Space
     * @return The Space that corresponds to the coordinate
     */
    public Space getSpaceByCoordinate(Coordinate _coord) {
        return rows.get(_coord.getRow()).getSpaces().get(_coord.getCell());
    }

    /**
     * A method to loop through every piece of an inputted color on the board and check for available jumps
     * @param _pieceColor The color of the Current Player's pieces
     * @return Return a boolean indicating if any of the player's pieces have a jump they can take
     */
    public boolean checkForAvailableJumps(PieceColor _pieceColor) {
        PieceColor jumpedColor = _pieceColor.equals(PieceColor.RED) ? PieceColor.WHITE : PieceColor.RED; //Set the color of the jumped pieces (opposite the piece Color)

        for(Row row: rows) { //Loop through all rows & spaces, getting each piece if it exists
            for (Space space : row) {
                Piece piece = space.getPiece();

                if(piece != null && piece.getType().equals(PieceType.SINGLE)) { //If the piece is a Single, Red piece (and we are checking the red pieces)
                    if (piece.getColor().equals(_pieceColor) && piece.getColor().equals(PieceColor.RED)) {
                        //Check only jumps that are "Up," as that's the only direction red single pieces can move
                        boolean jumpLeftExists = checkJumpInOneDirection(row, space, -1, -1, PieceColor.WHITE);
                        boolean jumpRightExists = checkJumpInOneDirection(row, space, -1, 1, PieceColor.WHITE);

                        if (jumpLeftExists || jumpRightExists)
                            return true;
                    } else if (piece.getColor().equals(_pieceColor) && piece.getColor().equals(PieceColor.WHITE)) { //Check for Single, White Pieces
                        //Check only jumps that are "Down," as that's the only direction single white pieces can move
                        boolean jumpLeftExists = checkJumpInOneDirection(row, space, 1, -1, PieceColor.RED);
                        boolean jumpRightExists = checkJumpInOneDirection(row, space, 1, 1, PieceColor.RED);

                        if (jumpLeftExists || jumpRightExists)
                            return true;
                    }
                } else if(piece != null && piece.getType().equals(PieceType.KING)) { //Check for King Pieces of the appropriate color
                    if(piece.getColor().equals(_pieceColor)) {
                        //Check in all 4 directions, as a King can move in any direction
                        boolean jumpExistsUpLeft = checkJumpInOneDirection(row, space, -1, -1, jumpedColor);
                        boolean jumpExistsUpRight = checkJumpInOneDirection(row, space, -1, 1, jumpedColor);
                        boolean jumpExistsDownLeft = checkJumpInOneDirection(row, space, 1, -1, jumpedColor);
                        boolean jumpExistsDownRight = checkJumpInOneDirection(row, space, 1, 1, jumpedColor);

                        if (jumpExistsUpLeft || jumpExistsUpRight || jumpExistsDownLeft || jumpExistsDownRight)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * A function to check if the pieces of a given color have any available moves (1 square)
     * @param _pieceColor The color of the pieces for the player we are checking
     * @return Whether or not any moves exists for pieces of that color
     */
    public boolean checkForAvailableMoves(PieceColor _pieceColor) {
        for(Row row : rows) { //Loop through all rows & spaces, getting a piece if it exists
            for(Space space : row) {
                Piece piece = space.getPiece();

                if(piece != null && piece.getType().equals(PieceType.SINGLE)) { //Check moves for all Single Red Pieces (provided the color we're checking is red)
                    if (piece.getColor().equals(_pieceColor) && piece.getColor().equals(PieceColor.RED)) {
                        //Only check "Up" for red pieces
                        boolean moveLeftExists = checkMoveInOneDirection(row, space, -1, -1);
                        boolean moveRightExists = checkMoveInOneDirection(row, space, -1, 1);

                        if (moveLeftExists || moveRightExists)
                            return true;
                    } else if (piece.getColor().equals(_pieceColor) && piece.getColor().equals(PieceColor.WHITE)) { //Check for all single white pieces
                        //Only check "Down" for white pieces
                        boolean moveLeftExists = checkMoveInOneDirection(row, space, 1, -1);
                        boolean moveRightExists = checkMoveInOneDirection(row, space, 1, 1);

                        if (moveLeftExists || moveRightExists)
                            return true;
                    }
                } else if(piece != null && piece.getType().equals(PieceType.KING)) { //Check moves for correctly colored king pieces
                    if(piece.getColor().equals(_pieceColor)) {
                        //Check all 4 directions
                        boolean moveExistsUpLeft = checkMoveInOneDirection(row, space, -1, -1);
                        boolean moveExistsUpRight = checkMoveInOneDirection(row, space, -1, 1);
                        boolean moveExistsDownLeft = checkMoveInOneDirection(row, space, 1, -1);
                        boolean moveExistsDownRight = checkMoveInOneDirection(row, space, 1, 1);

                        if (moveExistsUpLeft || moveExistsUpRight || moveExistsDownLeft || moveExistsDownRight)
                            return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * A function to check if there are pieces of an inputted color left
     * @param _pieceColor The color of the pice that was jumped
     * @return Whether or not their are pieces left
     */
    public boolean arePiecesLeft(PieceColor _pieceColor) {
        for(Row row : rows) { //Loop through each row & space
            for(Space space : row) {
                Piece piece = space.getPiece();

                //If a square has an appropriately colored piece, return true and stop searching
                if(piece != null && piece.getColor().equals(_pieceColor))
                    return true;
            }
        }

        return false;
    }

    /**
     * Method to check each metric for if a game is over
     * @param _pieceColor The color of the pieces we're checking
     * @return Whether or not the game is over
     */
    public boolean isGameOver(PieceColor _pieceColor) {
        //If a player/color has no pieces left OR has no moves AND jumps left, the game is over
        return (!arePiecesLeft(_pieceColor) || (!checkForAvailableMoves(_pieceColor) && !checkForAvailableJumps(_pieceColor)));
    }

    /**
     * A method to check if a valid jump exists in one particular direction for a given piece
     * @param _row The Row the piece is in
     * @param _space The space the piece is in
     * @param _rowDirection The "direction" the piece is moving for rows (1 or -1)
     * @param _cellDirection The "direction" the piece is moving for "columns" (1 or -1)
     * @param _jumpedPieceColor The color of the piece being jumped in the move
     * @return A boolean indicating if the jump is valid
     */
    private boolean checkJumpInOneDirection(Row _row, Space _space, int _rowDirection, int _cellDirection, PieceColor _jumpedPieceColor) {
        try { //Add a try/catch, because ArrayIndexOutOfBounds Exceptions can occur here (which indicates an invalid jump)
            Space squareToJump = getSpaceByCoordinate(new Coordinate(_row.getIndex() + _rowDirection, _space.getCellIdx() + _cellDirection));
            Space landingSquare = getSpaceByCoordinate(new Coordinate(_row.getIndex() + (_rowDirection * 2), _space.getCellIdx() + (_cellDirection * 2)));

            Piece jumpedPiece = squareToJump.getPiece();
            Piece landingSquarePiece = landingSquare.getPiece();

            //If there is a piece of the opposite color being jumped and the landing space is empty, it is a valid jump
            if (jumpedPiece != null && jumpedPiece.getColor().equals(_jumpedPieceColor) && landingSquarePiece == null) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * A method to check if a a valid move in one particular direction exists for a given piece
     * @param _row The row the piece is in
     * @param _space The space the piece is in
     * @param _rowDirection The "direction" in rows the piece is moving (1 or -1)
     * @param _cellDirection The "direction" in cells the piece is moving (1 or -1)
     * @return A boolean indicating if the move is valid
     */
    private boolean checkMoveInOneDirection(Row _row, Space _space, int _rowDirection, int _cellDirection) {
        try { //Try/Catch to handle ArrayIndexOutOfBounds Exceptions, which indicate an invalid move
            Space landingSquare = getSpaceByCoordinate(new Coordinate(_row.getIndex() + _rowDirection, _space.getCellIdx() + _cellDirection));

            return landingSquare.getPiece() == null;
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * A method to check if a move has put a piece in the last row on the board (meaning it should become a king)
     * @param _endSpace The landing space of a move or jump
     * @param _piece The piece being moved
     * @return A boolean indicating if the piece is now in the last row
     */
    private boolean reachedLastRow(Coordinate _endSpace, Piece _piece) {
        int rowValue = _endSpace.getRow();

        //If a RED piece is in Row 0 (White's starting row) or a White Piece is in Row 8 7 (Red's starting row), it should become a king
        if((rowValue == 0 && _piece.getColor().equals(PieceColor.RED)) || (rowValue == 7 && _piece.getColor().equals(PieceColor.WHITE))) {
            return true;
        } else {
            return false;
        }
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
