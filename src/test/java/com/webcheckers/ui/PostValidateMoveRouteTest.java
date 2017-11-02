package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostValidateMoveRouteTest {
    @Test
    public void testPostValidateMoveRoute() throws Exception {
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
        String _player1 = "niharika";
        Board board = mock(Board.class);
        PostValidateMoveRoute postValidateMoveRoute = new PostValidateMoveRoute(gameCenter);

        when(request.body()).thenReturn("{\"start\":{\"row\":\"5\",\"cell\":\"0\"},\"end\":{\"row\":\"4\",\"cell\":\"1\"}}");
        when(request.session()).thenReturn(session);
        when(session.attribute("username")).thenReturn(_player1);
        when(gameCenter.getGame(request.session().attribute("username"))).thenReturn(test);
        when(test.getBoard()).thenReturn(board);

        Message message = (Message) postValidateMoveRoute.handle(request, response);

        assertEquals("info", message.getType());
        assertEquals("Hooray!", message.getText());
    }

}