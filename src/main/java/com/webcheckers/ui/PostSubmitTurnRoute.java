package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

import static spark.Spark.halt;

/**
 * The Web Controller for Submitting a Turn.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostSubmitTurnRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code POST /submitTurn} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
    PostSubmitTurnRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        //return message;
        Game game = gameCenter.getGame(request.session().attribute(PostLoginRoute.USERNAME_PARAM));
        game.changeTurn();
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;
    }
}
