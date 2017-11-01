package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import spark.*;

import java.util.Objects;

public class PostValidateMoveRoute implements Route {
    //Attributes
    private final GameCenter gameCenter;

    PostValidateMoveRoute(final GameCenter gameCenter) {
        // validation
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");

        this.gameCenter = gameCenter;
    }

    @Override
    public Object handle(Request request, Response response) {
        Game game = gameCenter.getGame(request.session().attribute("username"));
        Board board = game.getBoard();
        String data = request.body();

        Message message;
        Move move = JsonUtils.fromJson(data, Move.class);

        if (move.isValidMoveForward() && !board.didMove()) {
            System.out.println(move.toString());
            board.movePiece(move);
            message = new Message("Valid Move", "info");
            board.setDidMove(true);
        } else {
            message = new Message("Invalid Move", "error");
        }

        return message;
    }
}
