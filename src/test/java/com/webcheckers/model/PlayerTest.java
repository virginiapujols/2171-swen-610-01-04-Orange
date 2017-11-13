package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testGetUsername() throws Exception {

        String _username = "Messi";
        Player test = new Player(_username);

        assertEquals("Messi",test.getUsername());
    }

    @Test
    public void testUpdateGamesLost(){
        String _username = "niharika";
        Player test = new Player(_username);
        int gamesLost = 0;
        assertEquals(gamesLost, test.getGamesLost());
        test.updateGamesLost();
        assertEquals(gamesLost+1, test.getGamesLost());
    }

    @Test
    public void testUpdateGamesWon(){
        String _username = "niharika";
        Player test = new Player(_username);
        int gamesWon = 0;
        assertEquals(gamesWon, test.getGamesLost());
        test.updateGamesLost();
        assertEquals(gamesWon+1, test.getGamesLost());
    }

    @Test
    public void testEquals() throws Exception {
        String _username = "Messi";
        String _username2 = "Messi";
        Player test = new Player(_username);
        Player test2 = new Player(_username2);

        assertTrue(test.equals(test2));
    }
}