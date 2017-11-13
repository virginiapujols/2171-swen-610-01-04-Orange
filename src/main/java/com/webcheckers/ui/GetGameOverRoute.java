package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the gameOver page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetGameOverRoute implements TemplateViewRoute {

    // Constants
    static final String VIEW_NAME = "gameOver.ftl";

    // Attributes
    private final GameCenter gameCenter;
    private Game game;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code GET /GameOver} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    GetGameOverRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        final String currentUsername = request.session().attribute(PostLoginRoute.USERNAME_PARAM);
        game = gameCenter.getGame(currentUsername);

        if(game != null && game.getIsOver()) { //If the game exists and is one that has ended, show the end game messages
            Map<String, Object> vm = new HashMap<>();
            vm.put("title", "Game Over!");

            //Use Spark's .splat() to pull the value entered for the wildcard character in the URL
            String gameResult = request.splat()[0];

            if(gameResult.equals("won")) { //If the wildcard value was "won", they player won, otherwise ("lost"), they lost
                vm.put("resultMessage", "Congratulations! You win!");
            } else {
                vm.put("resultMessage", "You lost, better luck next time!");
            }

            vm.put("playerName", currentUsername);
            vm.put("board", game.getBoard());

            return new ModelAndView(vm , VIEW_NAME);
        } else {
            response.redirect("/");
            halt();
            return null;
        }
    }
}