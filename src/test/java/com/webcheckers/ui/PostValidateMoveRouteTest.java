package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostValidateMoveRouteTest {

    // Atributes
    private GameCenter gameCenter = mock(GameCenter.class);
    private Game game = mock(Game.class);
    private String _username = "Niharika";
    private Request request;
    private Response response;
    private Session session;
    private Message message;

    /**
     * The component under test(CuT)
     */
    private PostValidateMoveRoute CuT = new PostValidateMoveRoute(gameCenter);

    @Before
    public void setUp(){
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
    }

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
        when(request.body()).thenReturn("{\"start\":{\"row\":\"5\",\"cell\":\"0\"},\"end\":{\"row\":\"4\",\"cell\":\"1\"}}");
        when(request.session()).thenReturn(session);
        when(session.attribute("username")).thenReturn(_username);
        when(gameCenter.getGame(request.session().attribute("username"))).thenReturn(game);
        when(game.getBoard()).thenReturn(new Board());

        message = (Message) CuT.handle(request, response);

        assertEquals(MessageStatus.info, message.getType());
        assertEquals("Valid Move", message.getText());
    }

}
