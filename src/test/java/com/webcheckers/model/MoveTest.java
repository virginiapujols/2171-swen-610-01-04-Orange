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
    
      @Test                                           
    public void testGetCellsMoved() throws Exception {  
        Coordinate _start = mock(Coordinate.class); 
        Coordinate _end = mock(Coordinate.class);   
        Move test = new Move(_start, _end);         
        when(_start.getCell()).thenReturn(2);       
        when(_end.getCell()).thenReturn(3);         
        assertEquals(1,test.getCellsMoved());       
    }                                               
     @Test                                          
    public void testGetJumpedCoordinate() throws Exception {
        Coordinate _start = mock(Coordinate.class); 
        Coordinate _end = mock(Coordinate.class);   
        Move test = new Move(_start,_end);          
        when(_start.getRow()).thenReturn(2);        
        when(_start.getCell()).thenReturn(0);       
        when(_end.getRow()).thenReturn(4);          
        when(_end.getCell()).thenReturn(2);         
                                                    
        Coordinate c1 = new Coordinate(3,1);        
        assertEquals(c1,test.getJumpedCoordinate());
    }                                               
                                                    

}
