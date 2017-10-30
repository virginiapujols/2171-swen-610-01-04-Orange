package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import spark.*;

import java.util.Objects;

public class PostValidateMoveRoute implements Route {
    public static final String MESSAGE_ERROR = "error";
    public static final String MESSAGE_INFO = "info";

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
        JsonObject element = JsonUtils.fromJson(data, JsonObject.class);

        //Move move = JsonUtils.fromJson(data, Move.class); //TODO: Make it load from Json
        Move move = new Move();
        move.setStartCell(element.getAsJsonObject("start").get("cell").getAsInt());
        move.setStartRow(element.getAsJsonObject("start").get("row").getAsInt());
        move.setEndCell(element.getAsJsonObject("end").get("cell").getAsInt());
        move.setEndRow(element.getAsJsonObject("end").get("row").getAsInt());

        Message message;
        if (move.isValidMoveForward())
            message = new Message("Valid movement!", MESSAGE_INFO);
        else
            message = new Message("This movement is invalid!!", MESSAGE_ERROR);

        return message;
    }
}
