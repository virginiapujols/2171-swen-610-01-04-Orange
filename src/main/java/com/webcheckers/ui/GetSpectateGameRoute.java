package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Row;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Spectate Game page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetSpectateGameRoute implements TemplateViewRoute {

    //Constants
    public static final String PLAYER1_NAME = "player1Name";
    public static final String WHOSE_TURN = "whoseTurn";
    public static final String PLAYER2_NAME = "player2Name";
    public static final String CURR_PLAYER = "currentPlayer";
    public static final String GAME_BOARD = "board";
    public static final String VIEW_NAME = "spectateGame.ftl";

    // Attributes
    private final GameCenter gameCenter;
    private Game game;

    /**
     * The constructor for the {@code GET /spectateGame} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
    GetSpectateGameRoute(final GameCenter gameCenter) {
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public ModelAndView handle(Request request, Response response) {
        Map<String, Object> vm = new HashMap<>();
        Session session = request.session();
        String username = session.attribute(PostLoginRoute.USERNAME_PARAM);
        vm.put("title", "Play a game!");
        vm.put("playerName", username);

        //Get the name of Player 1 in the game and use it to get the game to spectate
        final String p1Username = request.queryParams("player1");
        game = gameCenter.getGame(p1Username);

        if(game != null) { //If they are in game:
            int gameResult = game.isGameOver();

            if(gameResult != -1) { //If the game result is not -1, meaning a player has won, redirect the spectator to the home page
                gameCenter.endSpectating(username);
                response.redirect("/");
                halt();
                return null;
            }

            //Make sure they don't get added to the spectator list over and over again
            if(!gameCenter.isSpectating(username))
                gameCenter.markAsSpectating(username);

            //Add Game Information to the ViewModel
            vm.put(PLAYER1_NAME, game.getPlayer1().getUsername());
            vm.put(PLAYER2_NAME, game.getPlayer2().getUsername());
            vm.put(CURR_PLAYER, true);

            if(game.isMyTurn(p1Username)) {
                vm.put(WHOSE_TURN, 0);
            } else {
                vm.put(WHOSE_TURN, 1);
            }
            vm.put(GAME_BOARD, game.getBoard());


            return new ModelAndView(vm , VIEW_NAME);
        } else { //If there is no game for that player, redirect them to the home page
            gameCenter.endSpectating(username);
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}