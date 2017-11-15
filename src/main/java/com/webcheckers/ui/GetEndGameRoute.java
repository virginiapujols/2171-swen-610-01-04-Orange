package com.webcheckers.ui;

import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for Ending the Game and returning to the home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetEndGameRoute implements TemplateViewRoute {
    // Attributes
    private final GameCenter gameCenter;
    private Game game;

    /**
     * The constructor for the {@code GET /endGame} route handler.
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

        if(game != null && game.getIsOver()) { //If the game exists and is over, remove the game from the list of games
            gameCenter.removeGame(game);
        }

        response.redirect("/");
        halt();
        return null;
    }
}