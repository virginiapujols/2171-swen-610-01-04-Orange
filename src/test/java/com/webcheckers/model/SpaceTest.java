package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

public class SpaceTest {

    Piece _piece = mock(Piece.class);
    int _cellIdx = 3;

    Space CuT;

    @Before
    public void setUp() throws Exception {
        CuT = new Space(Space.SPACE_COLOR_BLACK, _piece, _cellIdx);
    }

    @Test
    public void testSetColor() throws Exception {
        assertEquals(Space.SPACE_COLOR_BLACK, CuT.getColor());
    }

    @Test
    public void testSetPiece() throws Exception{
        assertEquals(_piece, CuT.getPiece());
    }

    @Test
    public void testSetCellIdx() throws Exception{
        assertEquals(_cellIdx, CuT.getCellIdx());
    }

    @Test
    public void testIsValid() throws Exception {
        assertFalse(CuT.isValid());
        CuT = new Space(Space.SPACE_COLOR_BLACK, null, _cellIdx);
        assertTrue(CuT.isValid());
    }

    @Test
    public void testRemoveCapturedPiece(){
        CuT = new Space(Space.SPACE_COLOR_BLACK, _piece, 1);
        assertNotNull(CuT.getPiece());
        CuT.removeCapturedPiece();
        assertNull(CuT.getPiece());
    }
}