package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    Piece CuT = new Piece(PieceType.SINGLE, PieceColor.WHITE);

    @Test
    public void setColor() throws Exception {
        assertEquals(PieceColor.WHITE, CuT.getColor());
    }

    @Test
    public void setType() throws Exception {
        assertEquals(PieceType.SINGLE, CuT.getType());
    }

}