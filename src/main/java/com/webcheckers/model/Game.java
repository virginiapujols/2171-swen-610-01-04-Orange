package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Game{

    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    int turn;
    private List<Move> moves = new ArrayList<Move>();
    private List<Piece> capturedPieces = new ArrayList<Piece>();

    /**
     * This is the constructor for the Game class
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
     * This is the function to start a game
     */
    public void startGame(){}
    
        /**
     * This is the function to end the game
     */
    public void endGame(){}

    /**
     * This function denotes if the game is won or not
     * @return a Boolean "true" or "false"
     */
    private boolean isGameWon(){return true;}
    
    /**
     * This is the accessor of board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * This is the accessor of player1
     * @return the player1
     */
    public Player getPlayer1() {
        return this.player1;
    }
    
    /**
     * This is the accessor of player2
     * @return the player2
     */
    public Player getPlayer2() {
        return this.player2;
    }
    
      /**
     * This function denotes if the game is over or not
     * @return a Boolean "true" or false
     */
    
    public boolean getIsOver() {
        return this.isOver;
    }
    
    /**
     *  This function is the accessor of turn
     * @return the turn (whose turn it is)
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     *This function is the mutator for board
     * @param _board denotes the board
     */
    public void setBoard(Board _board) {
        this.board = _board;
    }

    /**
     * This is the mutator for player1
     * @param _player1 denotes player1
     */
    public void setPlayer1(Player _player1) {
        this.player1 = _player1;
    }
    
    /**
     * This is the mutator for player2
     * @param _player2 denotes player2
     */
    public void setPlayer2(Player _player2) {
        this.player2 = _player2;
    }
    
      /**
     * This function is the mutator for IsOver (sets if the game si over or not)
     * @param _isOver denotes IsOver
     */
    public void setIsOver(Boolean _isOver) {
        this.isOver = _isOver;
    }
    
    /**
     * This is the mutator for turn (sets whose turn it is)
     * @param _turn denotes turn
     */
    public void setTurn(int _turn) {
        this.turn = _turn;
    }

    public int changeTurn() {
        this.turn = (this.turn == 0) ? 1 : 0;
        this.board.setDidMove(false);
        this.board.setDidJump(false);
        moves.clear();
        return this.turn;
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
            moves.remove(lastMovePosition);

            board.undoMove(moveToUndo);

            if(moveToUndo.getRowsMoved() == 2) {
                int lastPieceCapturedPosition = capturedPieces.size() - 1;
                Coordinate jumpedCoordinate = moveToUndo.getJumpedCoordinate();
                Space jumpedSpace = board.getSpaceByCoordinate(jumpedCoordinate);
                Piece pieceToRestore = capturedPieces.get(lastPieceCapturedPosition);
                capturedPieces.remove(lastPieceCapturedPosition);
                board.undoCapture(jumpedSpace, pieceToRestore, capturedPieces.size());
            }

            return new Message("Move has been undone!", "info");
        } else {
            return new Message("No moves have been made!", "error");
        }
    }

    public void removePieceIfCaptured(Move _move) {
        if(_move.getRowsMoved() == 2) {
            Coordinate jumpedCoordinate = _move.getJumpedCoordinate();
            Space jumpedSpace = board.getSpaceByCoordinate(jumpedCoordinate);
            Piece jumpedPiece = jumpedSpace.getPiece();

            if(jumpedPiece != null) {
                capturedPieces.add(jumpedPiece);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            Game current = (Game)obj;
            return (current.player1.getUsername() == ((Game) obj).player1.getUsername() && current.player2.getUsername() == ((Game) obj).player2.getUsername());
        }
        return false;
    }
}
