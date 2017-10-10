package com.webcheckers.appl;

import com.webcheckers.model.Game;
import spark.Session;

import java.util.*;
import java.util.logging.Logger;
import com.webcheckers.model.Player;

/**
 * The object to coordinate the state of the Web Application.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GameCenter {
    // Attributes
    private Map<String, Player> players = new HashMap<>(); //<username, Player>
    private List<Game> games = new ArrayList<>();

    //
    // Public methods
    //
    public Set<String> getUsernames() {
        return players.keySet();
    }

    public boolean addPlayer(Session session, String username) {
        Player newPlayer = new Player(username);
        session.attribute("player", newPlayer);
        players.put(username, new Player(username));
        return true;
    }

    public boolean usernameTaken(String username) {
        return players.keySet().contains(username);
    }

    public Game startGame(String _player1, String _player2) {
        Player player1 = players.get(_player1);
        Player player2 = players.get(_player2);

        Game game = new Game(player1, player2);
        games.add(game);

        return game;
    }

    public boolean isInGame(String _username) {
        for(Game game : games) {
            if(game.getPlayer1().getUsername().equals(_username) || game.getPlayer2().getUsername().equals(_username)) {
                return true;
            }
        }

        return false;
    }

    public Game getGame(String _username) {
        for(Game game : games) {
            if(game.getPlayer1().getUsername().equals(_username) || game.getPlayer2().getUsername().equals(_username)) {
                return game;
            }
        }

        return null;
    }

    public List<String> getAvailablePlayers() {
        List<String> available = new ArrayList<>();
        for(String username : players.keySet()) {
            if(!isInGame(username))
                available.add(username);
        }

        return available;
    }

    public void removePlayer(String _username) {
        players.remove(_username);
    }
}