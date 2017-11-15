package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    Piece CuT = new Piece(PieceType.SINGLE, PieceColor.WHITE);

    @Test
    public void setColor() throws Exception {
        CuT.setColor(PieceColor.RED);
        assertEquals(PieceColor.RED, CuT.getColor());
    }

    @Test
    public void setType() throws Exception {
        assertEquals(PieceType.SINGLE, CuT.getType());
    }

    @Test
    public void testMakeKing() throws Exception{
        CuT.makeKing();
        assertTrue(CuT.getJustKinged());
    }

    @Test
    public void testSetJustKing() throws Exception{
        CuT.setJustKinged(true);
        assertTrue(CuT.getJustKinged());

        CuT.setJustKinged(false);
        assertFalse(CuT.getJustKinged());
    }
}