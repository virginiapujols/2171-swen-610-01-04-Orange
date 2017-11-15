package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static com.webcheckers.ui.PostLoginRoute.USERNAME_PARAM;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class PostCheckTurnRouteTest {

    // Atributes
    private GameCenter gameCenter = mock(GameCenter.class);
    private Game game = mock(Game.class);
    private String _username = "Niharika";
    private Request request;
    private Response response;
    private Session session;

    /**
     * The component under test(CuT)
     */
    private PostCheckTurnRoute CuT = new PostCheckTurnRoute(gameCenter);

    @Before
    public void setUp(){
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
    }

    @Test
    public void testHandle() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.getGame(_username)).thenReturn(game);
        when(game.isMyTurn(_username)).thenReturn(true);
        assertTrue((boolean)CuT.handle(request, response));

        when(gameCenter.getGame(_username)).thenReturn(null);
        assertFalse((boolean)CuT.handle(request, response));
    }
}