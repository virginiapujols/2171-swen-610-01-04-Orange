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
    public void testAddPlayer() {
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
    public void testRemovePlayer() {
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
    public void testUsernameTaken() {
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
    public void testStartGame() {
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
    public void testIsInGame() {
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

    /**
     * Unit Test to validate if a player is spectating or playing
     */
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

    /**
     * Unit test to get a Game object based on a player' username
     */
    @Test
    public void testGetGame() {
        GameCenter test = new GameCenter();
        Session session = mock(Session.class);
        test.addPlayer(session, P1_USERNAME);
        test.addPlayer(session, P2_USERNAME);

        test.startGame(P1_USERNAME, P2_USERNAME);
        assertEquals(test.getGame(P1_USERNAME), test.getGame(P2_USERNAME));
        assertNull(test.getGame(P3_USERNAME));
    }

    /**
     * Unit test to remove a game from the stack of current games
     */
    @Test
    public void testRemoveGame(){
        CuT = new GameCenter();
        Session session = mock(Session.class);
        CuT.addPlayer(session, P1_USERNAME);
        CuT.addPlayer(session, P2_USERNAME);
        CuT.startGame(P1_USERNAME, P2_USERNAME);
        assertNotNull(CuT.getGame(P1_USERNAME));
        CuT.removeGame(CuT.getGame(P1_USERNAME));
        assertNull(CuT.getGame(P1_USERNAME));
    }

    /**
     * Unit test to validate the players availability
     */
    @Test
    public void testGetAvailablePlayers(){
        Session session = mock(Session.class);
        CuT = new GameCenter();
        CuT.addPlayer(session, P1_USERNAME);
        CuT.addPlayer(session, P2_USERNAME);
        CuT.addPlayer(session, P3_USERNAME);

        assertEquals(3, CuT.getAvailablePlayers().size());
        CuT.startGame(P1_USERNAME, P2_USERNAME);
        assertEquals(1, CuT.getAvailablePlayers().size());
    }

    /**
     * Unit Test to validate showing scores correctly
     */
    @Test
    public void testGetPlayerScores(){
        Session session = mock(Session.class);
        CuT = new GameCenter();
        CuT.addPlayer(session, P1_USERNAME);
        CuT.addPlayer(session, P2_USERNAME);

        List<String> playerScores = CuT.getPlayerScores();

        assertEquals(P1_USERNAME + " W: " + 0 + " - L: " + 0 + " ", playerScores.get(0));
        assertEquals(P2_USERNAME + " W: " + 0 + " - L: " + 0 + " ", playerScores.get(1));
    }

//    @Test
//    public void testRemovePlayer(){
//        GameCenter test = new GameCenter();
//        String _username = "Niharika";
//        Session session = mock(Session.class);
//        test.addPlayer(session, _username);
//        assertEquals(1, test.getAvailablePlayers().size());
//        test.removePlayer(_username);
//        assertEquals(0, test.getAvailablePlayers().size());
//    }
}