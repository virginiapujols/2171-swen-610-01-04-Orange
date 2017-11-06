package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostBackupMoveRouteTest {
    @Test
    public void testHandle() throws Exception {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session session = mock(Session.class);
        GameCenter gameCenter = mock(GameCenter.class);
        String _username = "niharika";
        Game game = mock(Game.class);
        PostBackupMoveRoute test = new PostBackupMoveRoute(gameCenter);
        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.getGame(_username)).thenReturn(game);
        Message msg = mock(Message.class);
        when(game.backupMove()).thenReturn(msg);

        assertEquals(msg, test.handle(request, response));
    }

}