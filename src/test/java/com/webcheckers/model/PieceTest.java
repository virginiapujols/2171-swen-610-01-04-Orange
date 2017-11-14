package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    String _type = "plain";
    String _color = "black";
    Piece CuT = new Piece(PieceType.SINGLE, PieceColor.WHITE);

    @Test
    public void setColor() throws Exception {
        assertEquals(_color, CuT.getColor());
    }

    @Test
    public void setType() throws Exception {
        assertEquals(_type, CuT.getType());
    }

}