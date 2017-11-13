package com.webcheckers.ui;

import static org.junit.Assert.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
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
    @Test
    public void testGetLogoutRoute() throws Exception {

        try {
            GetLogoutRoute test = new GetLogoutRoute(null);
        } catch (NullPointerException e) {
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test
    public void testHandle(){
        GameCenter gameCenter = mock(GameCenter.class);
        GetLogoutRoute test = new GetLogoutRoute(gameCenter);
        Request request = mock(Request.class);
        Session session = mock(Session.class);
        Game game = mock(Game.class);

        when(request.session()).thenReturn(session);
        when(session.attribute("username")).thenReturn("ashok");
        gameCenter.addPlayer(session, "ashok");
        Response response = mock(Response.class);
        ModelAndView view = test.handle(request, response);
        Map<String, Object> vm =(Map<String, Object>) view.getModel();
        assertEquals("Login!",vm.get("title"));
        assertEquals(GetHomeRoute.VIEW_NAME, view.getViewName());
        assertEquals(null,vm.get("username"));
        assertNull(vm.get("username"));

        // Resigning
        when(gameCenter.isInGame("niharika")).thenReturn(true);
        when(gameCenter.getGame("niharika")).thenReturn(game);

        ModelAndView testModelAndView = test.handle(request, response);
        String viewName = testModelAndView.getViewName();
        assertEquals(GetHomeRoute.VIEW_NAME, viewName);

    }
}