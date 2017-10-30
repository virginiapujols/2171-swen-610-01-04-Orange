package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Login page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostLoginRoute implements TemplateViewRoute {

    // Constants
    static final String USERNAME_PARAM = "username";
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

    public GameCenter getGameCenter(){
        return this.gameCenter;
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

        if(gameCenter.isUsernameTaken(reqUsername)) {
            return error(vm, USER_EXIST_MESSAGE);
        } else {
            gameCenter.addPlayer(request.session(), reqUsername);
            session.attribute(USERNAME_PARAM, reqUsername);
            vm.put(USERNAME_PARAM, reqUsername);
            response.redirect("/");
//            halt();
        }

        vm.put(USERNAME_PARAM, reqUsername);
        return new ModelAndView(vm , GetHomeRoute.VIEW_NAME);
    }
}