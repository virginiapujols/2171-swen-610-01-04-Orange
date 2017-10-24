import com.sun.tracing.dtrace.Attributes;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void test_addPlayer() {
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        List<String> players = new ArrayList<>();
        players.add(P1_USERNAME);

        assertNotNull(CuT.getAvailablePlayers());
        assertEquals(players, CuT.getAvailablePlayers());
    }

    @Test
    public void test_removePlayer() {
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.removePlayer(P1_USERNAME);

        List<String> noUsernames = new ArrayList<>();

        assertNotNull(CuT.getAvailablePlayers());
        assertEquals(noUsernames, CuT.getAvailablePlayers());
    }

    @Test
    public void test_usernameTaken() {
        when(p1Session.attribute(PLAYER)).thenReturn(p1);

        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);

        assertNotNull(CuT.getAvailablePlayers());
        assertTrue(CuT.usernameTaken(P1_USERNAME));
    }

    @Test
    public void test_startGame() {
        when(p1Session.attribute(PLAYER)).thenReturn(p1);
        when(p2Session.attribute(PLAYER)).thenReturn(p2);

        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.addPlayer(p2Session, P2_USERNAME);
        Game testGame = CuT.startGame(P1_USERNAME, P2_USERNAME);

        Player player1 = new Player(P1_USERNAME);
        Player player2 = new Player(P2_USERNAME);
        Game game = new Game(player1, player2);

        assertNotNull(testGame);
        assertTrue(testGame.equals(game));
    }

    @Test
    public void test_isInGame() {
        when(p1Session.attribute(PLAYER)).thenReturn(p1);
        when(p2Session.attribute(PLAYER)).thenReturn(p2);
        when(p3Session.attribute(PLAYER)).thenReturn(p3);

        CuT = new GameCenter();
        CuT.addPlayer(p1Session, P1_USERNAME);
        CuT.addPlayer(p2Session, P2_USERNAME);
        CuT.addPlayer(p3Session, P3_USERNAME);

        Game testGame = CuT.startGame(P1_USERNAME, P2_USERNAME);

        assertTrue(CuT.isInGame(P1_USERNAME));
        assertTrue(CuT.isInGame(P2_USERNAME));
        assertFalse(CuT.isInGame(P3_USERNAME));
    }
}