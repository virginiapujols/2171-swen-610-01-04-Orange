package com.webcheckers.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    @Test
    public void testLogin() throws Exception {
        String _username = "Messi";
        Player test = new Player(_username);
        test.login(_username);
        assertEquals("Messi",test.getUsername());

    }
/*
    @Test
    public void testLogOut() throws Exception {

    }

    @Test
    public void testChallengeUser() throws Exception {
    }

    @Test
    public void testAcceptChallenge() throws Exception {
    }

    @Test
    public void testDeclineChallenge() throws Exception {
    }
*/
    @Test
    public void testGetUsername() throws Exception {

        String _username = "Messi";
        Player test = new Player(_username);

        assertEquals("Messi",test.getUsername());
    }
/*
    @Test
    public void getIsPlaying() throws Exception {
    }

    @Test
    public void testSetIsPlaying() throws Exception {
    }
*/
    @Test
    public void testEquals() throws Exception {
        String _username = "Messi";
        String _username2 = "Messi";
        Player test = new Player(_username);
        Player test2 = new Player(_username2);

        assertTrue(test.equals(test2));


    }
/*
    @Test
    public void testHashCode() throws Exception {
    }
*/
}