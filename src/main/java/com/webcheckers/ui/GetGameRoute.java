package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Row;
import spark.*;

import static spark.Spark.halt;

/**
 * The Web Controller for the Home page.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GetGameRoute implements TemplateViewRoute {

    //Constants
    public static final String PLAYER_NAME = "playerName";
    public static final String PLAYER_COLOR = "playerColor";
    public static final String MY_TURN = "isMyTurn";
    public static final String OPP_NAME = "opponentName";
    public static final String OPP_COLOR = "opponentColor";
    public static final String CURR_PLAYER = "currentPlayer";
    public static final String GAME_BOARD = "board";
    public static final String RED = "RED";
    public static final String WHITE = "WHITE";
    public static final String VIEW_NAME = "game.ftl";

    // Attributes
    private final GameCenter gameCenter;
    private Game game;

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
        final String currentUsername = session.attribute(PostLoginRoute.USERNAME_PARAM);
        game = gameCenter.getGame(currentUsername);

        if(game != null) { //If they are in game:
            int gameResult = game.isGameOver();

            if(gameResult != -1) { //If the game result is not -1, meaning a player has won, redirect to the game over screens
                //If game result is 0, player 1 is the winner, otherwise (gameResult == 1) player 2 is the winner
                String winningUsername = gameResult == 0 ? game.getPlayer1().getUsername() : game.getPlayer2().getUsername();

                if(currentUsername.equals(winningUsername)) {
                    response.redirect("/gameOver/won");
                } else {
                    response.redirect("/gameOver/lost");
                }

                halt();
                return null;
            }

            if(game.getPlayer1().getUsername().equals(currentUsername)) { //Check if they're player 1 & populate the ViewModel
                vm.put(PLAYER_NAME, game.getPlayer1().getUsername());
                vm.put(PLAYER_COLOR, RED);
                vm.put(MY_TURN, true);
                vm.put(OPP_NAME, game.getPlayer2().getUsername());
                vm.put(OPP_COLOR, WHITE);
                vm.put(CURR_PLAYER, true);
            } else { //Check if they're player 2 & populate the ViewModel
                vm.put(PLAYER_NAME, game.getPlayer2().getUsername());
                vm.put(PLAYER_COLOR, WHITE);
                vm.put(MY_TURN, false);
                vm.put(OPP_NAME, game.getPlayer1().getUsername());
                vm.put(OPP_COLOR, RED);
                vm.put(CURR_PLAYER, false);
            }

            vm.put(MY_TURN, game.isMyTurn(currentUsername));
            vm.put(GAME_BOARD, getPlayerBoard(currentUsername));


            return new ModelAndView(vm , VIEW_NAME);
        } else { //If there is no game for that player, redirect them to the home page
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }

    /**
     * UI Helper method to make use of the flipRow method to visually flip the board for Player 2
     * This way, both player see the board as if they are sitting at their end (moving their pieces from the bottom of the screen to the top)
     * @param _username The username of the player who will be seeing the board
     * @return A board item to be shown to the Player
     */
    private Board getPlayerBoard(String _username) {
        if(game.getPlayer1().getUsername().equals(_username)) { //Player 1 sees a normal board
            return game.getBoard();
        } else { //Player 2 sees a flipped board
            Board flippedBoard = new Board();

            List<Row> rows = new ArrayList<>(game.getBoard().getRows());
            List<Row> flippedRows = new ArrayList<>();

            for (int i = rows.size() - 1; i >= 0; i--) { //Add each row backwards to the board, flipping it horizontally as it's added
                flippedRows.add(rows.get(i).flipRow());
            }
            flippedBoard.setRows(flippedRows);

            return flippedBoard;
        }
    }
}