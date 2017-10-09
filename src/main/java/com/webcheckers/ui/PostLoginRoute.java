package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

import com.webcheckers.model.Player;

/**
 * The Web Controller for the Login page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostLoginRoute implements TemplateViewRoute {

    // Constants
    static final String USERNAME_PARAM = "username";
    static final String ONLINE_PLAYERS_ATTR = "players";
    static final String MESSAGE_ATTR = "message";
    static final String MESSAGE_TYPE_ATTR = "messageType";
    static final String ERROR_TYPE = "error";
    static final String VIEW_NAME = "login.ftl";

    static final String USER_EXIST_MESSAGE = "Username already taken. Pick another one to play.";

    // Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code POST /Login} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    PostLoginRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gameCenter = gameCenter;
    }

    //
    // Private methods
    //
    private ModelAndView error(final Map<String, Object> vm, final String message) {
        vm.put(MESSAGE_ATTR, message);
        vm.put(MESSAGE_TYPE_ATTR, ERROR_TYPE);
        return new ModelAndView(vm, VIEW_NAME);
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login!");

        // start building the View-Model, retrieve the requested username, and attempt to create the Player object
        final Session session = request.session();
        final String reqUsername = request.queryParams(USERNAME_PARAM);
        final Player player = new Player(reqUsername);

        ArrayList<Player> onlinePlayers = session.attribute(ONLINE_PLAYERS_ATTR);
        if (onlinePlayers == null) {
            onlinePlayers = new ArrayList<>();
        }

        if (onlinePlayers.contains(player)) {
            return error(vm, USER_EXIST_MESSAGE);
        } else {
            onlinePlayers.add(player);
        }

        session.attribute(ONLINE_PLAYERS_ATTR, onlinePlayers);

        return new ModelAndView(vm , GetHomeRoute.VIEW_NAME);
    }
}