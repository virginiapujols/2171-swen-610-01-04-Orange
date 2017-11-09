package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {
    @Test
    public void setColor() throws Exception {
        String _type = "plain";
        String _color = "black";
        Piece test = new Piece(_type, _color);
        assertEquals(_color, test.getColor());
    }

    /*@Test
    public void setType() throws Exception {
        String _type = "plain";
        String _color = "black";
        Piece test = new Piece(_type, _color);
        assertEquals(_type, test.getType());
    }*/

}