package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveTest {
    @Test
    public void getRowsMoved() throws Exception {
        Coordinate start = mock(Coordinate.class);
        Coordinate end = mock(Coordinate.class);
        Move test = new Move(start, end);
        when(end.getRow()).thenReturn(3);
        when(start.getRow()).thenReturn(5);
        assertEquals(2, test.getRowsMoved());
    }

    @Test
    public void isMoveUp() throws Exception {
        Coordinate start = mock(Coordinate.class);
        Coordinate end = mock(Coordinate.class);
        Move test = new Move(start, end);
        when(end.getRow()).thenReturn(5);
        when(start.getRow()).thenReturn(3);
        assertTrue(test.isMoveUp());

        when(end.getRow()).thenReturn(3);
        when(start.getRow()).thenReturn(5);
        assertFalse(test.isMoveUp());
    }

}