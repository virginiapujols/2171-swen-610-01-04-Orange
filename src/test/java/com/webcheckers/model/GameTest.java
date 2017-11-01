package com.webcheckers.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class GameTest {

    @Test
    public void testSetBoard() throws Exception {
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        Board _board = mock(Board.class);
        Game test = new Game(_player1, _player2);
        test.setBoard(_board);
        assertEquals(_board,test.getBoard());
    }

    @Test
    public void testSetPlayer1() throws Exception {
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        Game test = new Game(_player1, _player2);
        test.setPlayer1(_player1);
        assertEquals(_player1, test.getPlayer1());
    }

    @Test
    public void testSetPlayer2() throws Exception {
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        Game test = new Game(_player1, _player2);
        test.setPlayer2(_player2);
        assertEquals(_player2, test.getPlayer2());
    }

    @Test
    public void testSetIsOver() throws Exception {
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        Game test = new Game(_player1, _player2);
        Boolean _isOver = true;
        test.setIsOver(_isOver);
        assertEquals(_isOver, test.getIsOver());
    }

    @Test
    public void testSetTurn() throws Exception {
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        int _turn = 5;
        Game test = new Game(_player1, _player2);
        test.setTurn(_turn);
        assertEquals(_turn, test.getTurn());
    }

    @Test
    public void testEquals() throws Exception{
        Player _player1 = mock(Player.class);
        Player _player2 = mock(Player.class);
        Game test = new Game(_player1, _player2);
        assertEquals(_player1, test.getPlayer1());
        assertEquals(_player2, test.getPlayer2());
    }
}