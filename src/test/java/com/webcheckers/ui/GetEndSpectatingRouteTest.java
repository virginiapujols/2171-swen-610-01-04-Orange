package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetEndSpectatingRouteTest {
    private GameCenter gameCenter = mock(GameCenter.class);
    private static final String USERNAME = "Andy";

    private Request request;
    private Response response;
    private Session session;

    private GetEndSpectatingRoute CuT;

    @Before
    public void setUp() {
        CuT = new GetEndSpectatingRoute(gameCenter);

        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor() {
        try {
            CuT = new GetEndSpectatingRoute(null);
        } catch (NullPointerException e) {
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }

    @Test (expected = spark.HaltException.class)
    public void testWhenUsernameisValid() throws Exception {
        when(session.attribute(USERNAME)).thenReturn(USERNAME);
        when(request.session()).thenReturn(session);

        final ModelAndView result = CuT.handle(request, response);
        verify(response).redirect("/");
        assertNull(result);
    }

    @Test (expected = spark.HaltException.class)
    public void testWhenUsernameisInvalid() throws Exception {
        when(session.attribute(USERNAME)).thenReturn(null);
        when(request.session()).thenReturn(session);

        final ModelAndView result = CuT.handle(request, response);
        verify(response).redirect("/");
        assertNull(result);
    }
}
