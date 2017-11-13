package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetEndGameRouteTest {
    private static final String USERNAME = "username";
    private static final String USERNAME1 = "Virgi";

    private GetEndGameRoute CuT;
    GameCenter gameCenter = mock(GameCenter.class);

    private Request request;
    private Response response;
    private Session session;

    @Before
    public void setUp() throws Exception {
        CuT = new GetEndGameRoute(gameCenter);

        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);

    }

    @Test
    public void testConstructor() {
        try {
            CuT = new GetEndGameRoute(null);
        } catch (NullPointerException e) {
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test (expected = spark.HaltException.class)
    public void testHandle() {
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        when(request.session()).thenReturn(session);

        Game game = mock(Game.class);
        when(game.getIsOver()).thenReturn(true);
        when(gameCenter.getGame(USERNAME1)).thenReturn(game);

        final ModelAndView result = CuT.handle(request, response);
        verify(response).redirect("/");
        assertNull(result);
    }
}