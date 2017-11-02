package com.webcheckers.model;

import org.junit.Test;
import org.mockito.Mock;


import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class BoardTest {
    @Test
    public void updateBoard() throws Exception {

    }

    @Test
    public void setRows() throws Exception {
        Board test = new Board();

        List<Row> rows = (List<Row>)test.getRows();
        assertEquals(rows,test.getRows());
    }

    @Test
    public void didMove() throws Exception {
        Board test = new Board();
        boolean _didMove = false;
        assertEquals(false,_didMove);

    }

    @Test
    public void setDidMove() throws Exception {

    }

    @Test
    public void movePiece() throws Exception {
        Board test= new Board();
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);

        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);
        assertFalse(test.didMove());
        test.movePiece(move);
        assertTrue(test.didMove());

    }

    @Test
    public void validateMoveIsValidRED() throws Exception {
        Board test = new Board();
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(2);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(3);
        when(c2.getCell()).thenReturn(0);

        Message message = test.validateMove(move);
       // System.out.println(message.getType());
       // System.out.println(message.getText());
         assertEquals("info",message.getType());
         assertEquals("Valid Move", message.getText());
    }

    @Test
    public void validateMoveIsInvalidRED() throws Exception {
        Board test = new Board();
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(0);
        when(c1.getCell()).thenReturn(1);
        when(c2.getRow()).thenReturn(0);
        when(c2.getCell()).thenReturn(0);

        Message message = test.validateMove(move);
        // System.out.println(message.getType());
        // System.out.println(message.getText());
        assertEquals("error",message.getType());
        assertEquals("You must move 1 row forward", message.getText());
    }

    @Test
    public void validateMoveIsValidWHITE() throws Exception {
        Board test = new Board();
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(0);
        when(c2.getRow()).thenReturn(5);
        when(c2.getCell()).thenReturn(1);

        Message message = test.validateMove(move);
        // System.out.println(message.getType());
        // System.out.println(message.getText());
        assertEquals("error",message.getType());
        assertEquals("You must move 1 row forward", message.getText());
    }

    @Test
    public void validateMoveIsInValidWHITE() throws Exception {
        Board test = new Board();
        Move move = mock(Move.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);

        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(0);
        when(c2.getRow()).thenReturn(4);
        when(c2.getCell()).thenReturn(1);

        Message message = test.validateMove(move);
        // System.out.println(message.getType());
        // System.out.println(message.getText());
        assertEquals("info",message.getType());
        assertEquals("Valid Move", message.getText());
    }

    @Test
    public void MoveMade(){
        Board test = new Board();
        Move move = mock(Move.class);
        Message message = mock(Message.class);
        Coordinate c1 = mock(Coordinate.class);
        Coordinate c2 = mock(Coordinate.class);
        when(move.getStart()).thenReturn(c1);
        when(move.getEnd()).thenReturn(c2);
        when(c1.getRow()).thenReturn(5);
        when(c1.getCell()).thenReturn(0);
        when(c2.getRow()).thenReturn(4);
        when(c2.getCell()).thenReturn(1);
        test.setDidMove(true);

        message =test.validateMove(move);
        //System.out.println(message.getText());
        //System.out.println(message.getType());

        assertEquals("error",message.getType());
        assertEquals("You have already made a move this turn",message.getText());
    }
    public void iterator() throws Exception {
    }

    @Test
    public void forEach() throws Exception {
    }

    @Test
    public void spliterator() throws Exception {
    }

}