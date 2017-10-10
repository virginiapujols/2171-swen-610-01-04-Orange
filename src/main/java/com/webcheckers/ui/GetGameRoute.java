package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetGameRoute implements TemplateViewRoute {

    // Attributes
    private final GameCenter gameCenter;

    //
    // Constructor
    //

    /**
     * The constructor for the {@code GET /startGame} route handler.
     *
     * @param gameCenter
     *    The {@link GameCenter} for the application.
     */
    GetGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Play a game!");

        Game game = gameCenter.getGame(request.session().attribute("username"));

        if(game != null) {
            if(game.getPlayer1().getUsername().equals(request.session().attribute("username"))) {
                vm.put("playerName", game.getPlayer1().getUsername());
                vm.put("playerColor", "red");
                vm.put("isMyTurn", true);
                vm.put("opponentName", game.getPlayer2().getUsername());
                vm.put("opponentColor", "white");
                vm.put("currentPlayer", true);
                vm.put("board", game.getBoard());
            } else {
                vm.put("opponentName", game.getPlayer1().getUsername());
                vm.put("opponentColor", "red");
                vm.put("isMyTurn", false);
                vm.put("playerName", game.getPlayer2().getUsername());
                vm.put("playerColor", "white");
                vm.put("currentPlayer", false);
                vm.put("board", game.getBoard());
            }

            return new ModelAndView(vm , "game.ftl");
        } else {
            System.out.println("NO GAME");
            response.redirect("/");
            halt();
            return null;
        }
    }
}