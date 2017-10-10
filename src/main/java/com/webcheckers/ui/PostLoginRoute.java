package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateViewRoute;

import com.webcheckers.model.Player;

import static spark.Spark.halt;

/**
 * The Web Controller for the Login page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostLoginRoute implements TemplateViewRoute {

    // Attributes
    private final GameCenter gameCenter;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code POST /Login} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    PostLoginRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        //

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login!");

        //TESTING
        //gameCenter.addPlayer(request.session(),"Andy");
        //gameCenter.addPlayer(request.session(), "Ashok");
        //gameCenter.addPlayer(request.session(),"CheckersFan1334");
        //

        String username = request.queryParams("username");
        if(gameCenter.usernameTaken(username)) {
            vm.put("message", "That username is already taken");
            vm.put("messageType", "error");
        } else {
            gameCenter.addPlayer(request.session(), username);
            request.session().attribute("username", username);
            vm.put("username", username);

            //vm.put("usernames", gameCenter.getUsernames());

            response.redirect("/");
            halt();
            //return new ModelAndView(vm , "home.ftl");
        }

        return new ModelAndView(vm , "login.ftl");
    }
}