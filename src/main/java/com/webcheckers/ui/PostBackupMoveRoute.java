package com.webcheckers.ui;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class PostBackupMoveRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;

    PostBackupMoveRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        Game game = gameCenter.getGame(currentUsername);

        return game.backupMove();
    }
}
