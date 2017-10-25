package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RowTest {
    private int NUMBER_ROWS = 8;

    @Before
    public void setUp() {
    }

    @Test
    public void test_createRowZero() {
        Row CuT = new Row(0);
        assertEquals(NUMBER_ROWS, CuT.getSpaces().size());

        assertEquals("white", CuT.getSpaces().get(0).getColor());
        assertEquals("black", CuT.getSpaces().get(1).getColor());
    }

    @Test
    public void test_createRowOne() {
        Row CuT = new Row(1);
        assertEquals(NUMBER_ROWS, CuT.getSpaces().size());
        assertEquals("black", CuT.getSpaces().get(0).getColor());
        assertEquals("white", CuT.getSpaces().get(1).getColor());
    }

    @Test
    public void test_getIndex() {
        Row CuT = new Row(0);
        assertEquals(0, CuT.getIndex());
    }

    @Test
    public void test_setIndex() {
        Row CuT = new Row(0);
        CuT.setIndex(3);
        assertEquals(3, CuT.getIndex());
    }
}