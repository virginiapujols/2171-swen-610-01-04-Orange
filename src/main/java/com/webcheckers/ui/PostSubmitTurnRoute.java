package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

import static spark.Spark.halt;

public class PostSubmitTurnRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;

    PostSubmitTurnRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        //Message message = new Message("info", "Other player's turn!");

        //return message;
        Game game = gameCenter.getGame(request.session().attribute(PostLoginRoute.USERNAME_PARAM));
        game.changeTurn();
        response.redirect("/game");
        halt();
        return null;
    }
}
