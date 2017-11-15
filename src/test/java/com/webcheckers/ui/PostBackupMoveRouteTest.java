package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.jar.Attributes;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostBackupMoveRouteTest {
    private GameCenter gameCenter = mock(GameCenter.class);
    private String _username = "Niharika";
    private Game game;
    private Request request;
    private Response response;
    private Session session;
    private Message msg;

    /**
     * The component under test(CuT)
     */
    private PostBackupMoveRoute CuT = new PostBackupMoveRoute(gameCenter);

    @Before
    public void setUp(){
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        msg = mock(Message.class);
    }


    @Test
    public void testHandle() throws Exception {
        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.getGame(_username)).thenReturn(game);
        when(game.backupMove()).thenReturn(msg);

        assertEquals(msg, CuT.handle(request, response));
    }

}