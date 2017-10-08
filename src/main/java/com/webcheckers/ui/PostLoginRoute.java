package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

import com.webcheckers.model.Player;

/**
 * The Web Controller for the Login page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostLoginRoute implements TemplateViewRoute {

    // Attributes
    private final GameCenter gameCenter;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code POST /Login} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    PostLoginRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login!");

        gameCenter.addUsername("Andy");
        gameCenter.addUsername("Ashok");
        gameCenter.addUsername("CheckersFan1334");

        vm.put("usernames", gameCenter.getUsernames());

        // start building the View-Model, retrieve the requested username, and attempt to create the Player object
        final Session session = request.session();
        final String reqUsername = request.queryParams("username");
        final Player player = new Player(reqUsername);

        return new ModelAndView(vm , "home.ftl");
    }
}