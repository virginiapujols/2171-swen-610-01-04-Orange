package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetGameRoute implements TemplateViewRoute {

    //Constants
    static final String PLAYER_NAME = "playerName";
    static final String PLAYER_COLOR = "playerColor";
    static final String MY_TURN = "isMyTurn";
    static final String OPP_NAME = "opponentName";
    static final String OPP_COLOR = "opponentColor";
    static final String CURR_PLAYER = "currentPlayer";
    static final String GAME_BOARD = "board";
    static final String RED = "red";
    static final String WHITE = "white";
    static final String VIEW_NAME = "game.ftl";

    // Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code GET /startGame} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
    GetGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Play a game!");

        //Get the session and get any game that the user (from the session data) is in
        final Session session = request.session();
        Game game = gameCenter.getGame(session.attribute(PostLoginRoute.USERNAME_PARAM));

        if(game != null) { //If they are in game:
            if(game.getPlayer1().getUsername()
                    .equals(session.attribute(PostLoginRoute.USERNAME_PARAM))) { //Check if they're player 1 & populate the ViewModel
                vm.put(PLAYER_NAME, game.getPlayer1().getUsername());
                vm.put(PLAYER_COLOR, RED);
                vm.put(MY_TURN, true);
                vm.put(OPP_NAME, game.getPlayer2().getUsername());
                vm.put(OPP_COLOR, WHITE);
                vm.put(CURR_PLAYER, true);
                vm.put(GAME_BOARD, game.getBoard());
            } else { //Check if they're player 2 & populate the ViewModel
                vm.put(PLAYER_NAME, game.getPlayer2().getUsername());
                vm.put(PLAYER_COLOR, WHITE);
                vm.put(MY_TURN, false);
                vm.put(OPP_NAME, game.getPlayer1().getUsername());
                vm.put(OPP_COLOR, RED);
                vm.put(CURR_PLAYER, false);
                vm.put(GAME_BOARD, game.getBoard());
            }

            return new ModelAndView(vm , VIEW_NAME);
        } else { //If there is no game for that player, redirect them to the home page
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}