package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.PieceColor;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetStartGameRoute implements TemplateViewRoute {
    //Constants
    public static final String CHALLENGED = "challengedplayer";
    public static final String VIEW_NAME = "game.ftl";

    // Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code GET /startGame} route handler.
     *
     * @param gameCenter The {@link GameCenter} for the application.
     */
    public GetStartGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Play a game!");

        //Get challenging player from session data & challenged player from query parameters
        final Session session = request.session();
        String player1 =  session.attribute(PostLoginRoute.USERNAME_PARAM);
        String player2 = request.queryParams(CHALLENGED);

        if(gameCenter.isInGame(player2) || player1 == null || !gameCenter.getPlayers().containsKey(player2)) { //If the challenged player is in a game or doesn't exist, redirect the challenger to the home page
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        } else { //If both players are available, start a game and populate the view model
            Game game = gameCenter.startGame(player1, player2);

            vm.put(GetGameRoute.PLAYER_NAME, player1);
            vm.put(GetGameRoute.PLAYER_COLOR, PieceColor.RED);
            vm.put(GetGameRoute.MY_TURN, true);
            vm.put(GetGameRoute.OPP_NAME, player2);
            vm.put(GetGameRoute.OPP_COLOR, PieceColor.WHITE);
            vm.put(GetGameRoute.CURR_PLAYER, true);
            vm.put(GetGameRoute.GAME_BOARD, game.getBoard());

            return new ModelAndView(vm, VIEW_NAME);
        }
    }

}