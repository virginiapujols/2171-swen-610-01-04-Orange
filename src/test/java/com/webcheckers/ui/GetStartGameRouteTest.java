package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.PieceColor;
import com.webcheckers.ui.GetGameRoute;
import com.webcheckers.ui.GetStartGameRoute;
import org.junit.Before;
import org.junit.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test for GetStartGameRoute
 */
public class GetStartGameRouteTest {
    //Constants
    private static final String CHALLENGED = "challengedplayer";
    private static final String VIEW_NAME = "game.ftl";
    private static final String USERNAME = "username";
    private static final String USERNAME1 = "Andy";
    private static final String USERNAME2 = "Steve";

    //Attributes
    private Request request;
    private Response response;
    private Session p1Session;
    private Session p2Session;
    private GameCenter gameCenter = new GameCenter();
    /**
     * The component under test(CuT)
     */
    private GetStartGameRoute CuT = new GetStartGameRoute(gameCenter);

    /**
     * Setup() method to configure request, session, & response info
     */
    @Before
    public void setup() {
        request = mock(Request.class);
        p1Session = mock(Session.class);
        p2Session = mock(Session.class);
        when(request.session()).thenReturn(p1Session);
        when(request.session()).thenReturn(p2Session);

        response = mock(Response.class);
    }

    /**
     * Test for handle() when a challenged player is already in a game
     * Expected result is HaltException called from halt()
     */
    @Test(expected = spark.HaltException.class)
    public void test_playerInGame() {
        //Set Mock Objects
        when(p1Session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(p1Session);
        when(request.queryParams(CHALLENGED)).thenReturn(USERNAME2);

        //Create Players & Start Game
        gameCenter.addPlayer(p1Session, USERNAME1);
        gameCenter.addPlayer(p2Session, USERNAME2);
        gameCenter.startGame(USERNAME1, USERNAME2);

        //Call handle()
        CuT = new GetStartGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);
    }

    /**
     * Test for handle() when challenged player is not in a game
     */
    @Test
    public void test_playerNotInGame() {
        //Set Mock Objects
        when(p1Session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(p1Session);
        when(request.queryParams(CHALLENGED)).thenReturn(USERNAME2);

        //Add players
        gameCenter.addPlayer(p1Session, USERNAME1);
        gameCenter.addPlayer(p2Session, USERNAME2);

        //Call handle()
        CuT = new GetStartGameRoute(gameCenter);
        final ModelAndView result = CuT.handle(request, response);

        //-----TESTING-----

        //Result is not null
        assertNotNull(result);

        //Model is non-null map
        final Object model = result.getModel();
        assertNotNull(model);
        assertTrue(model instanceof Map);

        //Model contains all necessary View-Model data
        final Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(USERNAME1, vm.get(GetGameRoute.PLAYER_NAME));
        assertEquals(PieceColor.RED, vm.get(GetGameRoute.PLAYER_COLOR));
        assertEquals(Boolean.TRUE, vm.get(GetGameRoute.MY_TURN));
        assertEquals(USERNAME2, vm.get(GetGameRoute.OPP_NAME));
        assertEquals(PieceColor.WHITE, vm.get(GetGameRoute.OPP_COLOR));
        assertEquals(Boolean.TRUE, vm.get(GetGameRoute.CURR_PLAYER));

        //Test View Name
        assertEquals(VIEW_NAME, GetStartGameRoute.VIEW_NAME);
    }

    /**
     * Unit Test to handle no player object being set
     * Should result in halt() being called
     */
    @Test(expected = spark.HaltException.class)
    public void test_noPlayers() {
        //Call handle without setting any mock session data
        final ModelAndView result = CuT.handle(request, response);
    }
}
