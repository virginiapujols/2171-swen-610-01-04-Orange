package com.webcheckers.model;

public class Game{

    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    int turn;

    public Game(Player _player1, Player _player2){
        this.player1 = _player1;
        this.player2 = _player2;
        this.board = new Board();
        this.turn = 0;
        this.isOver = false;
    }

    public void startGame(){}

    public void endGame(){}

    private boolean isGameWon(){return true;}

    public Board getBoard() {
        return board;
    }

}
