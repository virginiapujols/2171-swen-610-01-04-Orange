package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Login!");

        // start building the View-Model, retrieve the requested username, and attempt to create the Player object
        final Session session = request.session();
        final String reqUsername = request.queryParams("username");
        final Player player = new Player(reqUsername);

        return new ModelAndView(vm , "home.ftl");
    }
}