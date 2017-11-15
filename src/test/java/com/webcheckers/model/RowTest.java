package com.webcheckers.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RowTest {
    private int NUMBER_ROWS = 8;

    Row CuT = new Row(0);

    @Test
    public void test_createRowZero() {
        assertEquals(NUMBER_ROWS, CuT.getSpaces().size());
        assertEquals(Space.SPACE_COLOR_WHITE, CuT.getSpaces().get(0).getColor());
        assertEquals(Space.SPACE_COLOR_BLACK, CuT.getSpaces().get(1).getColor());
    }

    @Test
    public void test_createRowOne() {
        CuT = new Row(1);
        assertEquals(NUMBER_ROWS, CuT.getSpaces().size());
        assertEquals(Space.SPACE_COLOR_BLACK, CuT.getSpaces().get(0).getColor());
        assertEquals(Space.SPACE_COLOR_WHITE, CuT.getSpaces().get(1).getColor());
    }

    @Test
    public void test_getIndex() {
        assertEquals(0, CuT.getIndex());
    }

    @Test
    public void test_setIndex() {
        CuT.setIndex(3);
        assertEquals(3, CuT.getIndex());
    }
}