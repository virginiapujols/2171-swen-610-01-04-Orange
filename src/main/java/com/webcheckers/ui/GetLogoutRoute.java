package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
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
     * The constructor for the {@code POST /Logout} route handler.
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

        // start building the View-Model, retrieve the requested username, and attempt to create the Player object
        // Removing a player from the player list.
        final Session session = request.session();
        final Player currentPlayer = session.attribute("loggedPlayer");
        ArrayList<Player> onlinePlayers = session.attribute(PostLoginRoute.ONLINE_PLAYERS_ATTR);
        onlinePlayers.remove(currentPlayer);

        vm.put("loggedPlayer", null);

        return new ModelAndView(vm , GetHomeRoute.VIEW_NAME);
    }
}
