package com.webcheckers.ui;

import static org.junit.Assert.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class GetLogoutRouteTest {
    GameCenter gameCenter = mock(GameCenter.class);
    Request request = mock(Request.class);
    Response response = mock(Response.class);
    Session session = mock(Session.class);
    Game game = mock(Game.class);

    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link GetLogoutRoute} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    GetLogoutRoute CuT;

    @Before
    public void setUp() throws Exception {
        CuT = new GetLogoutRoute(gameCenter);

        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
    }

    @Test
    public void testGetLogoutRoute() throws Exception {
        try {
            new GetLogoutRoute(null);
        } catch (NullPointerException e) {
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test
    public void testHandle(){
        when(request.session()).thenReturn(session);
        when(session.attribute("username")).thenReturn("ashok");
        gameCenter.addPlayer(session, "ashok");
        ModelAndView view = CuT.handle(request, response);
        Map<String, Object> vm =(Map<String, Object>) view.getModel();
        assertEquals("Login!",vm.get("title"));
        assertEquals(GetHomeRoute.VIEW_NAME, view.getViewName());
        assertEquals(null,vm.get("username"));
        assertNull(vm.get("username"));

        // Resigning
        when(gameCenter.isInGame("niharika")).thenReturn(true);
        when(gameCenter.getGame("niharika")).thenReturn(game);

        ModelAndView testModelAndView = CuT.handle(request, response);
        String viewName = testModelAndView.getViewName();
        assertEquals(GetHomeRoute.VIEW_NAME, viewName);

    }
}