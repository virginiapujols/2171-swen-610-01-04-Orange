package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class PostSubmitTurnRouteTest {
    @Test
    public void testPostSubmitTurnRoute() throws Exception {
        try {
            PostSubmitTurnRoute test = new PostSubmitTurnRoute(null);
        }catch (NullPointerException e){
            assertEquals("gameCenter must not be null", e.getMessage());
        }
    }
}