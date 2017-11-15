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

/**
 * The Web Controller for Resigning a game.
 *
 * @author <a href='mailto:vp2532@rit.edu'>Virginia Pujols</a>
 */
public class PostResignGameRoute implements TemplateViewRoute {
    // Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code POST /resignGame} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
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
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;
    }
}
