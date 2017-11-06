package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GetGameRouteTest {

    private Request request;
    private Session sessionPlayer1;
    private Session sessionPlayer2;
    private Response response;
    private GameCenter gameCenter = new GameCenter();

    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link GameCenter} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    private GetGameRoute CuT = new GetGameRoute(gameCenter);

    // Test Values
    private String USERNAME1 = "kat";
    private String USERNAME2 = "pedro";

    static final String PLAYER_NAME = "playerName";
    static final String PLAYER_COLOR = "playerColor";
    static final String MY_TURN = "isMyTurn";
    static final String OPP_NAME = "opponentName";
    static final String OPP_COLOR = "opponentColor";
    static final String CURR_PLAYER = "currentPlayer";
    static final String GAME_BOARD = "board";
    static final String RED = "RED";
    static final String WHITE = "WHITE";

    /**
     * Setup common test scenario.
     */
    @Before
    public void setUp() {
        response = mock(Response.class);
        request = mock(Request.class);
        sessionPlayer1 = mock(Session.class);
        sessionPlayer2 = mock(Session.class);
        when(request.session()).thenReturn(sessionPlayer1);
        when(request.session()).thenReturn(sessionPlayer2);
    }

    @Test
    public void test_existingGamePlayer1() {
        // Arrange the test scenario: The session starts with a Game.
        when(sessionPlayer1.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(sessionPlayer1);
        when(request.queryParams(GetStartGameRoute.CHALLENGED)).thenReturn(USERNAME2);

        gameCenter.addPlayer(sessionPlayer1, USERNAME1);
        gameCenter.addPlayer(sessionPlayer2, USERNAME2);

        gameCenter.startGame(USERNAME1, USERNAME2);

        // Invoke the test
        final ModelAndView result = CuT.handle(request, response);
        // Analyze the results:
        // 1 result is non-null
        assertNotNull(result);
        // 2 object is not null
        final Object model = result.getModel();
        assertNotNull(model);
        assertTrue(model instanceof Map);

        Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(USERNAME1, vm.get(PLAYER_NAME));
        assertEquals(USERNAME2, vm.get(OPP_NAME));
        assertEquals(RED, vm.get(PLAYER_COLOR));
        assertEquals(Boolean.TRUE, vm.get(MY_TURN));
    }

    @Test
    public void test_existingGamePlayer2() {
        // Arrange the test scenario: The session starts with a Game.
        when(sessionPlayer1.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(sessionPlayer1);
        when(request.queryParams(GetStartGameRoute.CHALLENGED)).thenReturn(USERNAME2);

        gameCenter.addPlayer(sessionPlayer1, USERNAME1);
        gameCenter.addPlayer(sessionPlayer2, USERNAME2);

        gameCenter.startGame(USERNAME2, USERNAME1);

        // Invoke the test
        final ModelAndView result = CuT.handle(request, response);
        // Analyze the results:
        // 1 result is non-null
        assertNotNull(result);
        // 2

        final Object model = result.getModel();
        assertNotNull(model);
        assertTrue(model instanceof Map);

        Map<String, Object> vm = (Map<String, Object>) model;
        assertEquals(USERNAME1, vm.get(PLAYER_NAME));
        assertEquals(USERNAME2, vm.get(OPP_NAME));
        assertEquals(WHITE, vm.get(PLAYER_COLOR));
        assertEquals(Boolean.FALSE, vm.get(MY_TURN));
    }

    @Test(expected = spark.HaltException.class)
    public void test_notInGame() {
        // Arrange the test scenario: The session starts with no game.
        when(sessionPlayer1.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(null);

        // Invoke the test
        final ModelAndView result = CuT.handle(request, response);

        // Analyze the results:
        // 1 result is null
        assertNull(result);
        fail("Redirects invoke halt excpetions.");

        // Analyze the results:
        //   * redirect to the Home view
        verify(response).redirect(WebServer.HOME_URL);
    }
}