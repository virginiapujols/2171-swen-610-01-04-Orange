package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetEndGameRoute implements TemplateViewRoute {

    // Constants
    static final String VIEW_NAME = "gameOver.ftl";

    // Attributes
    private final GameCenter gameCenter;
    private Game game;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code GET /} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    GetEndGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        game = gameCenter.getGame(currentUsername);

        if(game != null && game.getIsOver()) {
            gameCenter.removeGame(game);
        }

        response.redirect("/");
        halt();
        return null;
    }
}