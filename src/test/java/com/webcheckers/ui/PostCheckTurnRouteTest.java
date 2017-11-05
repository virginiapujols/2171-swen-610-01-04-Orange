package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
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
    @Test
    public void testHandle() throws Exception {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session session = mock(Session.class);
        GameCenter gameCenter = mock(GameCenter.class);
        String username = "niharika";
        Game game = mock(Game.class);
        PostCheckTurnRoute test = new PostCheckTurnRoute(gameCenter);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(username);
        when(gameCenter.getGame(username)).thenReturn(game);
        when(game.isMyTurn(username)).thenReturn(true);

        assertTrue((boolean)test.handle(request, response));

        when(gameCenter.getGame(username)).thenReturn(null);
        assertFalse((boolean)test.handle(request, response));

    }
}