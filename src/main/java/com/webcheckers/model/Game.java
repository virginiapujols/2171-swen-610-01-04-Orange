package com.webcheckers.model;

public class Game{

    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    int turn;
    
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Game) {
            Game current = (Game)obj;
            return (current.player1.getUsername() == ((Game) obj).player1.getUsername() && current.player2.getUsername() == ((Game) obj).player2.getUsername());
        }
        return false;
    }
}
