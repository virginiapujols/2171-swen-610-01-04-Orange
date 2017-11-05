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
    @Test
    public void testHandle() throws Exception {

        GetLoginRoute CuT = new GetLoginRoute();
        Request request = mock(Request.class);
        Response response = mock(Response.class);

        ModelAndView result = CuT.handle(request, response);
        String viewName = result.getViewName();
        Map<String, Object> testVm = (HashMap) result.getModel();
        assertEquals(PostLoginRoute.VIEW_NAME,viewName);

    }

}