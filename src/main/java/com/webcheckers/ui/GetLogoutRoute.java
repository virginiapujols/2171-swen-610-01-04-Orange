package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lenovo on 10/9/2017.
 */
public class GetLogoutRoute implements TemplateViewRoute{
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code GET /Logout} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    GetLogoutRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login!");
        final Session session = request.session();
        final String currentPlayer = session.attribute("username");

        // Removing a player from the player list.
        if(gameCenter.isInGame(currentPlayer)) {
            Game game = gameCenter.getGame(currentPlayer);
            game.resign(currentPlayer);
            //response.redirect("/game");
        }

        session.removeAttribute("username");
        gameCenter.removePlayer(currentPlayer);

        vm.put("username", null);

        return new ModelAndView(vm , GetHomeRoute.VIEW_NAME);
    }
}
