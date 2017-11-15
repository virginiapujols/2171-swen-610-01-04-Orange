package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;

public class PieceTest {

    Piece CuT = new Piece(PieceType.SINGLE, PieceColor.WHITE);

    @Test
    public void testSetColor() throws Exception {
        CuT.setColor(PieceColor.WHITE);
        assertEquals(PieceColor.WHITE, CuT.getColor());
        CuT.setColor(PieceColor.RED);
        assertEquals(PieceColor.RED, CuT.getColor());
    }

    @Test
    public void testMakeSingle() throws Exception {
        CuT.makeSingle();
        assertEquals(PieceType.SINGLE,CuT.getType());
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