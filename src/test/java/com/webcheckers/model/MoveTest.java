package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveTest {
    @Test
    public void testIsValidMoveForward1() throws Exception {
        Coordinate _start = mock(Coordinate.class);
        Coordinate _end = mock(Coordinate.class);
        Move test = new Move(_start, _end);
        when(_end.getCell()).thenReturn(2);
        when(_end.getRow()).thenReturn(3);
        when(_start.getCell()).thenReturn(1);
        when(_start.getRow()).thenReturn(2);
        assertTrue(test.isValidMoveForward());
    }

    @Test
    public void testIsValidMoveForware2(){
        Coordinate _start = mock(Coordinate.class);
        Coordinate _end = mock(Coordinate.class);
        Move test = new Move(_start, _end);
        when(_end.getCell()).thenReturn(4);
        when(_end.getRow()).thenReturn(3);
        when(_start.getCell()).thenReturn(1);
        when(_start.getRow()).thenReturn(2);
        assertFalse(test.isValidMoveForward());
    }
}