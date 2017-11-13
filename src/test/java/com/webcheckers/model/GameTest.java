package com.webcheckers.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class GameTest {

    Game CuT;
    Player _player1 = mock(Player.class);
    Player _player2 = mock(Player.class);

    @Before
    public void setup() {
        when(_player1.getUsername()).thenReturn("niharika");
        when(_player2.getUsername()).thenReturn("virginia");

        CuT = new Game(_player1, _player2);
    }

    @Test
    public void testSetBoard() throws Exception {
        Board _board = mock(Board.class);
        CuT.setBoard(_board);
        assertEquals(_board,CuT.getBoard());
    }

    @Test
    public void testSetPlayer1() throws Exception {
        CuT.setPlayer1(_player1);
        assertEquals(_player1, CuT.getPlayer1());
    }

    @Test
    public void testSetPlayer2() throws Exception {
        CuT.setPlayer2(_player2);
        assertEquals(_player2, CuT.getPlayer2());
    }

    @Test
    public void testSetIsOver() throws Exception {
        Boolean _isOver = true;
        CuT.setIsOver(_isOver);
        assertEquals(_isOver, CuT.getIsOver());
    }

    @Test
    public void testSetTurn() throws Exception {
        int _turn = 5;
        CuT.setTurn(_turn);
        assertEquals(_turn, CuT.getTurn());
    }

    @Test
    public void testEquals() throws Exception {
        Game test = new Game(_player1, _player2);
        Game test2 = new Game(_player1, _player2);
        assertTrue(test.equals(test2));
        assertFalse(test.equals(new Board()));
    }

    @Test
    public void testChangeTurn1(){
        CuT.setTurn(1);
        //System.out.println(test.changeTurn());
        //assertEquals(1, test.changeTurn());
        assertEquals(0, CuT.changeTurn());
    }
    @Test
    public void testChangeTurn0(){
        //test.setTurn(1);
        //System.out.println(test.changeTurn());
        assertEquals(1, CuT.changeTurn());
    }

    @Test
    public void testIsMyTurn(){
        String _username = "niharika";
        when(_player1.getUsername()).thenReturn(_username);
        assertTrue(CuT.isMyTurn(_username));
    }

    @Test
    public void testBackupMoveWithNoMoves() {
        Message result = CuT.backupMove();
        assertEquals("error", result.getType());
        assertEquals("No moves have been made!", result.getText());
    }

    @Test
    public void testBackupMove() {
        Coordinate startPos = mock(Coordinate.class);
        Coordinate endPos = mock(Coordinate.class);

        /* Test single move
        when(startPos.getRow()).thenReturn(5);
        when(startPos.getCell()).thenReturn(4);
        when(endPos.getRow()).thenReturn(4);
        when(endPos.getCell()).thenReturn(3);
        */

        // Test jump
        CuT.getCapturedPieces().add(new Piece("SINGLE", "WHITE"));
        when(startPos.getRow()).thenReturn(4);
        when(startPos.getCell()).thenReturn(3);
        when(endPos.getRow()).thenReturn(2);
        when(endPos.getCell()).thenReturn(1);

        Move move = new Move(startPos, endPos);
        CuT.addMoveToList(move);

        Message result = CuT.backupMove();
        assertEquals("info", result.getType());
        assertEquals("Move has been undone!", result.getText());
    }

    @Test
    public void testRemoveCapturedPieceFromBoard() {
        Coordinate startPos = mock(Coordinate.class);
        Coordinate endPos = mock(Coordinate.class);
        when(startPos.getRow()).thenReturn(4);
        when(startPos.getCell()).thenReturn(3);
        when(endPos.getRow()).thenReturn(2);
        when(endPos.getCell()).thenReturn(1);

        Move move = new Move(startPos, endPos);

        assertEquals(0, CuT.getCapturedPieces().size());
        CuT.removePieceIfCaptured(move);
    }

}
