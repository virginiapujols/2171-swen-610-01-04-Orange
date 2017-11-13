package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class PostResignGameRoute implements TemplateViewRoute {

    // Attributes
    private final GameCenter gameCenter;

    public PostResignGameRoute(GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        Game game = gameCenter.getGame(currentUsername);
        game.resign(currentUsername);
        response.redirect("/game");

        //halt();
        return null;
    }
}
