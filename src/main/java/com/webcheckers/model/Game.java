package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent a Game between 2 players.  Responsible for handling game creation, ending, and tracking the state of the gaem
 */
public class Game{
    //Class Attributes
    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    private int turn;
    private List<Move> moves = new ArrayList<Move>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();
    private String resigningPlayer;

    /**
     * Parameterized constructor for the Game class
     * @param _player1 is the first player
     * @param _player2 is the second player
     */
    public Game(Player _player1, Player _player2){
        this.board = new Board();
        this.player1 = _player1;
        this.player2 = _player2;
        this.isOver = false;
        this.turn = 0;
    }
    
    /**
     * Accessor for Board Attribute
     * @return Board used to play the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Accessor for player1 attribute
     * @return player1 in the Game
     */
    public Player getPlayer1() {
        return this.player1;
    }
    
    /**
     * Accessor for player2
     * @return player2 in the Game
     */
    public Player getPlayer2() {
        return this.player2;
    }
    
    /**
     * Accessor for the attribute that indicates whether or not the game has ended
     * @return isOver boolean attribute (true = game is over, false = game is not over)
     */
    public boolean getIsOver() {
        return this.isOver;
    }
    
    /**
     *  Accessor for turn attribute
     * @return Int representing who's turn it is (0 = P1, 1 = P2)
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Mutator for Board attribute
     * @param _board Board the game is played in on
     */
    public void setBoard(Board _board) {
        this.board = _board;
    }

    /**
     * Mutator for player1 attribute
     * @param _player1 The Player being added as Player 1
     */
    public void setPlayer1(Player _player1) {
        this.player1 = _player1;
    }
    
    /**
     * Mutator for player2 attribute
     * @param _player2 The Player being added as Player 2
     */
    public void setPlayer2(Player _player2) {
        this.player2 = _player2;
    }
    
      /**
     * Mutator for isOver attribute
     * @param _isOver Whether or not the game is over
     */
    public void setIsOver(Boolean _isOver) {
        this.isOver = _isOver;
    }
    
    /**
     * Mutator for turn attribute
     * @param _turn Who's turn it is (0 = P1, 1 = P2)
     */
    public void setTurn(int _turn) {
        this.turn = _turn;
    }

    /**
     * Accessor for capturedPieces attribute
     * @return capturedPieces A list of pieces that have been captured this turn
     */
    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * Mutator for capturedPieces attribute
     * @param _capturedPieces A List of Pieces that have been captured this turn
     */
    public void setCapturedPieces(List<Piece> _capturedPieces) {
        this.capturedPieces = _capturedPieces;
    }

    /**
     * Method to change to the other player's turn when a player ends their turn
     * @return this.turn An integer representing whose turn it is (0 = player 1, 1 = player 2)
     */
    public int changeTurn() {
        this.turn = (this.turn == 0) ? 1 : 0; //If turn = 0 (i.e. Player 1's turn is finished), set it to 1 (player 2's turn), otherwise set it to 0
        this.board.setDidMove(false);
        this.board.setDidJump(false);
        moves.clear();
        return this.turn;
    }

    /**
     * A method to resign current
     */
    public void resign(String _resigningPlayer) {
        resigningPlayer = _resigningPlayer;

        // Change turn to refresh opponent's board and indicate he/she wins.
        this.changeTurn();
    }

    /**
     * Method to check if it is a given user's turn
     * @param _username The username of the user we are checking for
     * @return A boolean: true if it's that user's turn, false if it isn't
     */
    public boolean isMyTurn(String _username) {
        //Int showing player number based on a comparison between the inputted username & player 1's username
        int t = (player1.getUsername().equals(_username)) ? 0 : 1;

        return t == this.turn;
    }

    /**
     * Method to add a Move to the list of Moves completed on this turn
     * @param move The move that was just completed
     */
    public void addMoveToList(Move move) {
        moves.add(move);
    }

    /**
     * Method to undo one move when the user clicks "Backup one move"
     * @return A Message indicating if the undo was successful or not
     */
    public Message backupMove() {
        if(moves.size() > 0) { //Only undo a move if there are moves to be undone
            int lastMovePosition = moves.size() - 1;
            Move moveToUndo = moves.get(lastMovePosition);
            Piece movedPiece = board.getSpaceByCoordinate(moveToUndo.getEnd()).getPiece();

            moves.remove(lastMovePosition);

            board.undoMove(moveToUndo);

            //If the undone move is a jump, we need to undo the move and add the captured piece back to the board
            if(moveToUndo.getRowsMoved() == 2) {
                int lastPieceCapturedPosition = capturedPieces.size() - 1;
                Coordinate jumpedCoordinate = moveToUndo.getJumpedCoordinate();
                Space jumpedSpace = board.getSpaceByCoordinate(jumpedCoordinate);
                Piece pieceToRestore = capturedPieces.get(lastPieceCapturedPosition);

                capturedPieces.remove(lastPieceCapturedPosition);
                board.undoCapture(jumpedSpace, pieceToRestore, capturedPieces.size());
            }

            //If the undone move kinged a piece, we need to undo that and make the piece a SINGLE piece again
            if(movedPiece.getJustKinged()) {
                movedPiece.makeSingle();
                movedPiece.setJustKinged(false);
            }

            return new Message("Move has been undone!", "info");
        } else {
            return new Message("No moves have been made!", "error");
        }
    }

    /**
     * Method to remove a piece from the game if it is captured
     * @param _move A move that has been made and results in the capture of a piece
     */
    public void removePieceIfCaptured(Move _move) {
        if(_move.getRowsMoved() == 2) { //Verify that the move was a jump
            Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
            Space jumpedSpace = board.getSpaceByCoordinate(jumpedCoordinate);
            Piece jumpedPiece = jumpedSpace.getPiece();

            if(jumpedPiece != null) { //If a piece was captured:
                capturedPieces.add(jumpedPiece);
            }
        }
    }

    /**
     * A method to detect and return whether or not the game is over (and who won)
     * @return An int representing the game state & winner
     * 0 = Red Wins, 1 = White Wins, -1 = Game not Over
     */
    public int isGameOver() {
        if(board.isGameOver("RED")) {
            isOver = true;
            return 1;
        } else if(board.isGameOver("WHITE")) {
            isOver = true;
            return 0;
        } else {
            if(resigningPlayer != null) {
                isOver = true;
                return player1.getUsername().equals(resigningPlayer) ? 1 : 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * Method to check for equality between games on the bases if player1 & player2's usernames
     * @param obj The object being compared to this game
     * @return A boolean indicating equality
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            Game current = (Game)obj;
            return (current.player1.getUsername() == ((Game) obj).player1.getUsername() && current.player2.getUsername() == ((Game) obj).player2.getUsername());
        }
        return false;
    }
}
