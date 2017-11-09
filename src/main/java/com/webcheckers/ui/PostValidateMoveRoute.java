package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import spark.*;
import sun.rmi.runtime.Log;

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

        Move move = JsonUtils.fromJson(data, Move.class);

        Message message = board.validateMove(move);
        if(!message.getType().equals(Message.MESSAGE_ERROR)) {
            game.addMoveToList(move);
            game.removePieceIfCaptured(move);
            board.movePiece(move);
        }

        return message;
    }
}
