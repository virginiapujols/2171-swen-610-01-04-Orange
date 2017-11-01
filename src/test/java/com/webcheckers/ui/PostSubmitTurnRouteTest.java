package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class PostSubmitTurnRouteTest {
    @Test
    public void testPostSubmitTurnRoute() throws Exception {
        try {
            PostSubmitTurnRoute test = new PostSubmitTurnRoute(null);
        }catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test
    public void testHandle() throws Exception {
        Session session = mock(Session.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        GameCenter gameCenter = mock(GameCenter.class);
        Game test = mock(Game.class);
        PostLoginRoute postLoginRoute = new PostLoginRoute(gameCenter);
        String _player1 = "niharika";
        String _player2 = "virginia";

        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_player1);
        when(gameCenter.getGame(request.session().attribute(PostLoginRoute.USERNAME_PARAM))).thenReturn(test);
        when(test.changeTurn()).thenReturn(1);

        assertNotNull(postLoginRoute.handle(request, response));
    }
}