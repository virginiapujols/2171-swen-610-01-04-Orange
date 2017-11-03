package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class PostCheckTurnRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;
    private Game game;

    PostCheckTurnRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }


    @Override
    public Object handle(Request request, Response response)  {
        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        game = gameCenter.getGame(currentUsername);

        if(game != null)
            return game.isMyTurn(currentUsername);
        else
            return false;
    }
}
