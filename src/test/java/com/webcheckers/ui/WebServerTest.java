package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import org.junit.Test;
import spark.TemplateEngine;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;

public class WebServerTest {
    private GameCenter gameCenter = new GameCenter();
    private TemplateEngine templateEngine = mock(TemplateEngine.class);

    @Test
    public void initTest() {
        WebServer CuT = new WebServer(gameCenter, templateEngine);
        CuT.initialize();
        assertNotNull(CuT);
    }
}
