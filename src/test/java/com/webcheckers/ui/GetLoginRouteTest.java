package com.webcheckers.ui;

import org.junit.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GetLoginRouteTest {
    Request request = mock(Request.class);
    Response response = mock(Response.class);

    /**
     * The component-under-test (CuT).
     *
     * <p>
     * This is a stateless component so we only need one.
     * The {@link GetLoginRoute} component is thoroughly tested so
     * we can use it safely as a "friendly" dependency.
     */
    GetLoginRoute CuT = new GetLoginRoute();

    @Test
    public void testHandle() throws Exception {
        ModelAndView result = CuT.handle(request, response);
        String viewName = result.getViewName();
        assertEquals(PostLoginRoute.VIEW_NAME,viewName);
    }
}