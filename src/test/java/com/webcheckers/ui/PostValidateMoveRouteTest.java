package com.webcheckers.ui;

import org.junit.Test;
import sun.reflect.annotation.ExceptionProxy;

import static org.junit.Assert.*;

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

    }

}