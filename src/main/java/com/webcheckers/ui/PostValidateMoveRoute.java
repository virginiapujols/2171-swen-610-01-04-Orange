package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;
import spark.*;

import java.util.Objects;

/**
 * The Web Controller for the AJAX Call to validate a move.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class PostValidateMoveRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;

    /**
     * The constructor for the {@code POST /validateMove} route handler
     * @param gameCenter The {@link GameCenter} for the application.
     */
    PostValidateMoveRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        //Get the game and board
        Game game = gameCenter.getGame(request.session().attribute("username"));
        Board board = game.getBoard();

        //Get the Move from the request body
        String data = request.body();
        Move move = JsonUtils.fromJson(data, Move.class);

        //Validate the move and enact it if it is valid
        Message message = board.validateMove(move);
        if(!(message.getType() == MessageStatus.error)) {
            game.addMoveToList(move);
            game.removePieceIfCaptured(move);
            board.movePiece(move);
        }

        return message;
    }
}
