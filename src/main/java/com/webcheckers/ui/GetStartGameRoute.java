package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetStartGameRoute implements TemplateViewRoute {

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
    GetStartGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Play a game!");

        String player1 =  request.session().attribute("username");
        String player2 = request.queryParams("challengedplayer");
        Game game = gameCenter.startGame(player1, player2);

        vm.put("playerName", player1);
        vm.put("playerColor", "red");
        vm.put("isMyTurn", true);
        vm.put("opponentName", player2);
        vm.put("opponentColor", "white");
        vm.put("currentPlayer", true);
        vm.put("board", game.getBoard());


        return new ModelAndView(vm , "game.ftl");
    }

}