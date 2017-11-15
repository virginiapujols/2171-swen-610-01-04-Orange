package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.PostLoginRoute.VIEW_NAME;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetGameOverRouteTest {
    private static final String USERNAME = "username";
    private static final String USERNAME1 = "Virginia";
    private GameCenter gameCenter = mock(GameCenter.class);

    private Request request;
    private Response response;
    private Session session;

    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link GetGameOverRoute} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    private GetGameOverRoute CuT;

    @Before
    public void setUp() throws Exception {
        CuT = new GetGameOverRoute(gameCenter);

        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor() {
        try {
            CuT = new GetGameOverRoute(null);
        } catch (NullPointerException e) {
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test (expected = spark.HaltException.class)
    public void testGameNotOver() throws Exception {
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(session);

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(false);
        when(gameCenter.getGame(USERNAME1)).thenReturn(game);

        final ModelAndView result = CuT.handle(request, response);
        verify(response).redirect("/");
        assertNull(result);
    }

    @Test
    public void testGameOverWin() throws Exception {
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(session);
        when(request.splat()).thenReturn(new String[]{"won"});

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(true);
        when(gameCenter.getGame(USERNAME1)).thenReturn(game);

        final ModelAndView result = CuT.handle(request, response);

        // Getting the result value from error function
        Map<String, Object> testVm = (HashMap) result.getModel();

        // Comparing the expected and actual values
        assertEquals("Congratulations! You win!", testVm.get("resultMessage"));
        assertNotNull(result);
    }

    @Test
    public void testGameOverLose() throws Exception {
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(session);
        when(request.splat()).thenReturn(new String[]{"lost"});

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(true);
        when(gameCenter.getGame(USERNAME1)).thenReturn(game);

        final ModelAndView result = CuT.handle(request, response);

        // Getting the result value from error function
        Map<String, Object> testVm = (HashMap) result.getModel();

        // Comparing the expected and actual values
        assertEquals("You lost, better luck next time!", testVm.get("resultMessage"));
        assertNotNull(result);
    }
}