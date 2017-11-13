package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
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
    @Test
    public void handle() throws Exception {
        GameCenter gameCenter = mock(GameCenter.class);
        Game game = mock(Game.class);
        PostResignGameRoute test = new PostResignGameRoute(gameCenter);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Session session = mock(Session.class);
        HashMap<String, Object> vm = new HashMap<>();
        String _username = "Niharika";

        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_username);
        when(gameCenter.getGame(_username)).thenReturn(game);

        assertNull(test.handle(request, response));
    }

}