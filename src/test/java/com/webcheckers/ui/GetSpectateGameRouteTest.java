package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSpectateGameRouteTest {
    private static final String USERNAME = "username";
    private static final String USERNAME0 = "Sandra";
    private static final String USERNAME1 = "Virginia";
    private static final String USERNAME2 = "Steve";

    private GetSpectateGameRoute CuT;
    private GameCenter gameCenter = mock(GameCenter.class);

    private Request request;
    private Response response;
    private Session session;

    @Before
    public void setUp() throws Exception {
        CuT = new GetSpectateGameRoute(gameCenter);
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

    }

    @Test
    public void testGetSpectateGameRoute(){
        // Checking if we send a null gameCenter, it gives an appropriate message
        try {
            CuT = new GetSpectateGameRoute(null);
        }
        catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test(expected = spark.HaltException.class)
    public void testSpectatingWiningGame() {
        when(session.attribute(USERNAME)).thenReturn(USERNAME0);
        when(request.session()).thenReturn(session);
        when(request.queryParams("player1")).thenReturn(USERNAME1);

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(true);
        when(game.getPlayer1()).thenReturn(new Player(USERNAME1));
        when(game.getPlayer2()).thenReturn(new Player(USERNAME2));

        when(gameCenter.getGame(USERNAME1)).thenReturn(game);

        CuT = new GetSpectateGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);
        assertNull(result);
    }


    @Test
    public void testSpectatingInProgressGame() {
        when(session.attribute(USERNAME)).thenReturn(USERNAME0);
        when(request.session()).thenReturn(session);
        when(request.queryParams("player1")).thenReturn(USERNAME1);

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(true);
        when(game.getPlayer1()).thenReturn(new Player(USERNAME1));
        when(game.getPlayer2()).thenReturn(new Player(USERNAME2));
        when(gameCenter.getGame(USERNAME1)).thenReturn(game);
        when(game.isGameOver()).thenReturn(-1);

        CuT = new GetSpectateGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);

        // Getting the result value from error function
        Map<String, Object> testVm = (HashMap) result.getModel();

        assertEquals(true, testVm.get(GetSpectateGameRoute.CURR_PLAYER));
        assertEquals(USERNAME0, testVm.get("playerName"));
        assertNotNull(result);
    }

    @Test(expected = spark.HaltException.class)
    public void testGameOver() {
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(session);

        Game game = mock(Game.class);
        when(game.isGameOver()).thenReturn(1);

        final ModelAndView result = CuT.handle(request, response);
        assertNull(result);
    }
}