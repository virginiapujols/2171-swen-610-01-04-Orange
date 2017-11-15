package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private static final String USERNAME1 = "Virgi";

    Player CuT = new Player(USERNAME1);

    @Test
    public void testGetUsername() throws Exception {
        assertEquals(USERNAME1, CuT.getUsername());
    }

    @Test
    public void testUpdateGamesLost() {
        int gamesLost = 0;
        assertEquals(gamesLost, CuT.getGamesLost());
        CuT.updateGamesLost();
        assertEquals(gamesLost+1, CuT.getGamesLost());
    }

    @Test
    public void testUpdateGamesWon() {
        int gamesWon = 0;
        assertEquals(gamesWon, CuT.getGamesWon());
        CuT.updateGamesWon();
        assertEquals(gamesWon+1, CuT.getGamesWon());
    }

    @Test
    public void testEquals() throws Exception {
        assertNotEquals(CuT, new Object());
    }
}