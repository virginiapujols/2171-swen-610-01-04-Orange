package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import org.junit.Before;
import org.junit.Test;
import spark.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetHomeRouteTest {
    private static final String USERNAME = "username";
    private static final String USERNAME1 = "Andres";
    private static final String USERNAME2 = "Julia";

    private GetHomeRoute CuT;
    private GameCenter gameCenter = new GameCenter();
    private Request request;
    private Response response;
    private Session session;

    @Before
    public void setUp() throws Exception {
        CuT = new GetHomeRoute(gameCenter);
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);

        when(request.session()).thenReturn(session);

    }

    @Test
    public void testConstructor() throws Exception {
        try {
            GetHomeRoute test = new GetHomeRoute(null);
        } catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test(expected = spark.HaltException.class)
    public void testIsInGame() {
        gameCenter.addPlayer(session, USERNAME1);
        when(session.attribute(USERNAME)).thenReturn(USERNAME1);
        gameCenter.startGame(USERNAME1, USERNAME2);

        final ModelAndView result = CuT.handle(request, response);
        verify(response).redirect(WebServer.GAME_URL);
        assertNull(result);

    }

    @Test
    public void testNotInGame() {
        final ModelAndView result = CuT.handle(request, response);
        assertNotNull(result);
    }

}