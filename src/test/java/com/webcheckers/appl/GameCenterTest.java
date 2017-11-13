package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Test;

import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for GameCenter class
 */
public class GameCenterTest {
    //Constants
    private static final String PLAYER = "player";
    private static final String P1_USERNAME = "p1";
    private static final String P2_USERNAME = "p2";
    private static final String P3_USERNAME = "p3";

    //Attributes
    private Session p1Session = mock(Session.class);
    private Session p2Session = mock(Session.class);
    private Session p3Session = mock(Session.class);
    private Player p1 = new Player("p1");
    private Player p2 = new Player("p2");
    private Player p3 = new Player("p3");

    /**
     * The component under test (CuT)
     */
    private GameCenter CuT;

    /**
     * Test for Adding a Player (i.e. on log in)
     */
    @Test
    public void test_addPlayer() {
        //Set Mock Data
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        //Create GameCenter & Add Player
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        List<String> players = new ArrayList<>();
        players.add(P1_USERNAME);

        //Assert ArrayList of Players is not null and has exactly P1 in it
        assertNotNull(CuT.getAvailablePlayers());
        assertEquals(players, CuT.getAvailablePlayers());
    }

    /**
     * Unit Test for removing a Player from the ArrayList of Players
     */
    @Test
    public void test_removePlayer() {
        //Set Mock Data
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        //Create GameCenter, then add & remove player
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.removePlayer(P1_USERNAME);

        //Create blank array list to match
        List<String> noUsernames = new ArrayList<>();

        //Assert created arraylist is empty and not null
        assertNotNull(CuT.getAvailablePlayers());
        assertEquals(noUsernames, CuT.getAvailablePlayers());
    }

    /**
     * Unit Test to check functionality of method to determine if username is taken
     */
    @Test
    public void test_usernameTaken() {
        //Set Mock Data
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        //Create GameCenter & Add Username
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);

        //Assert List of players is not null and that the "p1" username is taken
        assertNotNull(CuT.getAvailablePlayers());
        assertTrue(CuT.isUsernameTaken(P1_USERNAME));
    }

    /**
     * Unit Test to check functionality of method to start game
     */
    @Test
    public void test_startGame() {
        //Set Mock Data
        when(p1Session.attribute(PLAYER)).thenReturn(p1);
        when(p2Session.attribute(PLAYER)).thenReturn(p2);

        //Create GameCenter, Add Players, & Create Game
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.addPlayer(p2Session, P2_USERNAME);
        Game testGame = CuT.startGame(P1_USERNAME, P2_USERNAME);

        //Manually create game object
        Player player1 = new Player(P1_USERNAME);
        Player player2 = new Player(P2_USERNAME);
        Game game = new Game(player1, player2);

        //Assert that game is not null and matches create game
        assertNotNull(testGame);
        assertTrue(testGame.equals(game));
    }

    /**
     * Unit Test to test method telling if user is in game
     */
    @Test
    public void test_isInGame() {
        //Set Mock Data
        when(p1Session.attribute(PLAYER)).thenReturn(p1);
        when(p2Session.attribute(PLAYER)).thenReturn(p2);
        when(p3Session.attribute(PLAYER)).thenReturn(p3);

        //Create GameCenter, Add Players, & Make Game
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.addPlayer(p2Session, P2_USERNAME);
        CuT.addPlayer(p3Session, P3_USERNAME);
        Game testGame = CuT.startGame(P1_USERNAME, P2_USERNAME);

        //Assert P1 & P2 are in game, but P3 is not
        assertTrue(CuT.isInGame(P1_USERNAME));
        assertTrue(CuT.isInGame(P2_USERNAME));
        assertFalse(CuT.isInGame(P3_USERNAME));
    }

    @Test
    public void testSpectateGame() {
        //Create GameCenter, Add Players, & Create Game
        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.addPlayer(p2Session, P2_USERNAME);
        CuT.addPlayer(p2Session, P3_USERNAME);

        // Test adding/removing games and spectating them
        assertEquals(0, CuT.printAvailableGames().size());
        Game game = CuT.startGame(P1_USERNAME, P2_USERNAME);
        assertEquals(1, CuT.printAvailableGames().size());
        assertEquals(game, CuT.getGame(P1_USERNAME));

        // Test spectating
        CuT.markAsSpectating(P3_USERNAME);
        assertTrue(CuT.isSpectating(P3_USERNAME));
        CuT.endSpectating(P3_USERNAME);
        assertFalse(CuT.isSpectating(P3_USERNAME));

        CuT.removeGame(game);
        assertEquals(0, CuT.printAvailableGames().size());
    }

    public void testGetGame() {
        GameCenter test = new GameCenter();
        String player1 = "p1";
        String player2 = "p2";
        Session session = mock(Session.class);
        test.addPlayer(session, player1);
        test.addPlayer(session, player2);

        test.startGame(player1, player2);
        assertEquals(test.getGame(player1), test.getGame(player2));
        assertNull(test.getGame("dummyName"));
    }

    @Test
    public void testRemoveGame(){
        GameCenter test = new GameCenter();
        String player1 = "p1";
        String player2 = "p2";
        Session session = mock(Session.class);
        test.addPlayer(session, player1);
        test.addPlayer(session, player2);
        test.startGame(player1, player2);
        assertNotNull(test.getGame(player1));
        test.removeGame(test.getGame(player1));
        assertNull(test.getGame(player1));
    }

    @Test
    public void testGetAvailablePlayers(){
        String player1 = "a";
        String player2 = "b";
        String player3 = "c";

        Session session = mock(Session.class);

        GameCenter test = new GameCenter();

        test.addPlayer(session, player1);
        test.addPlayer(session, player2);
        test.addPlayer(session, player3);

        assertEquals(3, test.getAvailablePlayers().size());
        test.startGame(player1, player2);
        assertEquals(1, test.getAvailablePlayers().size());
    }

    @Test
    public void testGetPlayerScores(){
        String player1 = "p1";
        String player2 = "p2";
        String[] users = {"p1", "p2"};
        Session session = mock(Session.class);
        GameCenter test = new GameCenter();

        test.addPlayer(session, player1);
        test.addPlayer(session, player2);

        List<String> playerScores = test.getPlayerScores();

        assertEquals(player1 + " W: " + 0 + " - L: " + 0 + " ", playerScores.get(0));
        assertEquals(player2 + " W: " + 0 + " - L: " + 0 + " ", playerScores.get(1));
    }

    @Test
    public void testRemovePlayer(){
        GameCenter test = new GameCenter();
        String _username = "Niharika";
        Session session = mock(Session.class);
        test.addPlayer(session, _username);
        assertEquals(1, test.getAvailablePlayers().size());
        test.removePlayer(_username);
        assertEquals(0, test.getAvailablePlayers().size());
    }
}