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

        GetLoginRoute test = new GetLoginRoute();

        Request request = mock(Request.class);
        Response response = mock(Response.class);


        ModelAndView test2 = test.handle(request, response);
        String viewName = test2.getViewName();
        Map<String, Object> testVm = (HashMap) test2.getModel();
        assertEquals(PostLoginRoute.VIEW_NAME,viewName);

    }

}