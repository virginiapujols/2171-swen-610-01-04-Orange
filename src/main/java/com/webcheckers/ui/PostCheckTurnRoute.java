package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

/**
 * The Web Controller for Checking who's turn it is
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 * @author <a href='mailto:ask5893@rit.edu'>Ashok Kesari</a>
 */
public class PostCheckTurnRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;
    private Game game;

    /**
     * The constructor for the {@code POST /checkTurn} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
    PostCheckTurnRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }


    @Override
    public Object handle(Request request, Response response)  {
        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        game = gameCenter.getGame(currentUsername);

        if(game != null) //If a game exists, return whether or not it is that user's turn
            return game.isMyTurn(currentUsername);
        else
            return false;
    }
}
