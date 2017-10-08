package com.webcheckers.model;

public class Game{

    private Board board;
    private Player player1;
    private Player player2;
    private boolean isOver;
    int turn;

    public Game(Player player1, Player player2){

        this.player1=player1;
        this.player2=player2;
    }

    public void startGame(){}

    public void endGame(){}

    private boolean isGameWon(){return true;}



}
