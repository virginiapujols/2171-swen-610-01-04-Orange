package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveTest {
    Coordinate _start = mock(Coordinate.class);
    Coordinate _end = mock(Coordinate.class);
    Move CuT = new Move(_start, _end);

    @Test
    public void testGetRowsMoved() throws Exception {
        when(_end.getRow()).thenReturn(3);
        when(_start.getRow()).thenReturn(5);
        assertEquals(2,CuT.getRowsMoved());
    }

    @Test
    public void isMoveUp() throws Exception {
        when(_end.getRow()).thenReturn(5);
        when(_start.getRow()).thenReturn(3);
        assertTrue(CuT.isMoveUp());

        when(_end.getRow()).thenReturn(3);
        when(_start.getRow()).thenReturn(5);
        assertFalse(CuT.isMoveUp());
    }
    
      @Test                                           
    public void testGetCellsMoved() throws Exception {
        when(_start.getCell()).thenReturn(2);       
        when(_end.getCell()).thenReturn(3);         
        assertEquals(1,CuT.getCellsMoved());
    }                                               
     @Test                                          
    public void testGetJumpedCoordinate() throws Exception {
        when(_start.getRow()).thenReturn(2);        
        when(_start.getCell()).thenReturn(0);       
        when(_end.getRow()).thenReturn(4);          
        when(_end.getCell()).thenReturn(2);         
                                                    
        Coordinate c1 = new Coordinate(3,1);        
        assertEquals(c1,CuT.getJumpedCoordinate());
    }                                               
                                                    
}
