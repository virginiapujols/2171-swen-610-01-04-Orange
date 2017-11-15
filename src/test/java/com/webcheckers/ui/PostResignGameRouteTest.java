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
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostResignGameRouteTest {

    // Atributes
    private GameCenter gameCenter = mock(GameCenter.class);
    private String _username = "Niharika";
    private Game game;
    private Request request;
    private Response response;
    private Session session;

    /**
     * The component under test(CuT)
     */
    private PostResignGameRoute CuT = new PostResignGameRoute(gameCenter);

    @Before
    public void setUp(){
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
    }

    @Test (expected = spark.HaltException.class)
    public void handle() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.getGame(_username)).thenReturn(game);
        assertNull(CuT.handle(request, response));
    }

}