package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostSubmitTurnRouteTest {
    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link PostSubmitTurnRoute} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    PostSubmitTurnRoute CuT;

    GameCenter gameCenter = mock(GameCenter.class);

    @Before
    public void setup () {
        Game game = new Game(new Player("niharika"), new Player("virginia"));
        doReturn(game).when(gameCenter).getGame("niharika");
        CuT = new PostSubmitTurnRoute(gameCenter);
    }

    @Test
    public void testPostSubmitTurnRoute() throws Exception {
        try {
            CuT = new PostSubmitTurnRoute(null);
        }catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test(expected = spark.HaltException.class)
    public void testHandle() throws Exception {
        Session session = mock(Session.class);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Game test = mock(Game.class);

        String _player1 = "niharika";

        when(request.session()).thenReturn(session);
        when(session.attribute(PostLoginRoute.USERNAME_PARAM)).thenReturn(_player1);
        when(gameCenter.getGame(request.session().attribute(PostLoginRoute.USERNAME_PARAM))).thenReturn(test);
        when(test.changeTurn()).thenReturn(1);

        assertNotNull(CuT.handle(request, response));
    }
}