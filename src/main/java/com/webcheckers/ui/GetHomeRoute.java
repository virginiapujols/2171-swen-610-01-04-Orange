package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetHomeRoute implements TemplateViewRoute {

    // Constants
    static final String VIEW_NAME = "home.ftl";

    // Attributes
    private final GameCenter gameCenter;

    //
    // Constructor
    //

    /**
    * The constructor for the {@code GET /} route handler.
    *
    * @param gameCenter
    *    The {@link GameCenter} for the application.
    */
    GetHomeRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");
    return new ModelAndView(vm , VIEW_NAME);
  }

}