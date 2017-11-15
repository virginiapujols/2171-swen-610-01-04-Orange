package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    String _type = "plain";
    String _color = "black";
    Piece CuT = new Piece(_type, _color);

    @Test
    public void setColor() throws Exception {
        assertEquals(_color, CuT.getColor());
    }

    @Test
    public void setType() throws Exception {
        assertEquals(_type, CuT.getType());
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