package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 * @author <a href='mailto:nrd8504@rit.edu'>Niharika Dalal</a>
 * @author <a href='mailto:vp2532@rit.edu'>Virginia Pujols</a>
 * @author <a href='mailto:ask5893@rit.edu'>Ashok Kesari</a>
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
    String username = request.session().attribute("username");
    vm.put("title", "Welcome!");

    //State check to mark a user as not spectating a game if they access the home page (for any reason)
    if(gameCenter.isSpectating(username)) {
        gameCenter.endSpectating(username);
    }

    //Redirect user's in a game to their game
    if(gameCenter.isInGame(username)) {
        response.redirect(WebServer.GAME_URL);
        halt();
        return null;
    }

    vm.put("usernames", gameCenter.getAvailablePlayers());
    vm.put("games", gameCenter.printAvailableGames());
    vm.put("scores", gameCenter.getPlayerScores());

    if(request.session().attribute("username") != null) {
        vm.put("username", request.session().attribute("username"));
    }

    return new ModelAndView(vm , VIEW_NAME);
  }
}