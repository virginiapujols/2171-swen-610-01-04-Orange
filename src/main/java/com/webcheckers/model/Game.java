package com.webcheckers.model;

public class Game{

    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    int turn;

    public Game(Player _player1, Player _player2){
        this.board = new Board();
        this.player1 = _player1;
        this.player2 = _player2;
        this.isOver = false;
        this.turn = 0;
    }

    public void startGame(){}

    public void endGame(){}

    private boolean isGameWon(){return true;}

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public boolean getIsOver() {
        return this.isOver;
    }

    public int getTurn() {
        return this.turn;
    }

    public void setBoard(Board _board) {
        this.board = _board;
    }

    public void setPlayer1(Player _player1) {
        this.player1 = _player1;
    }

    public void setPlayer2(Player _player2) {
        this.player2 = _player2;
    }

    public void setIsOver(Boolean _isOver) {
        this.isOver = _isOver;
    }

    public void setTurn(int _turn) {
        this.turn = _turn;
    }
}
