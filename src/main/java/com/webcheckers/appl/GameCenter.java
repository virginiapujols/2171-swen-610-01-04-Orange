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
    private Map<String, Player> players = new HashMap<>(); //<username, Player>: All players in the application
    private List<Game> games = new ArrayList<>(); //All games in the application

    //
    // Public methods
    //

    /**
     * Returns a keyset of the players playlist (this is a unique list of Usernames registered with the application)
     * @return players.keySet() Every username that has been registered with the application
     */
    /*public Set<String> getUsernames() {
        return players.keySet();
    }*/

    /**
     * Method to add a Player to the application and set the "player" value for that user's session
     * @param session The session of the user submitting a username
     * @param username The requested username of the user
     */
    public void addPlayer(Session session, String username) {
        Player newPlayer = new Player(username);
        session.attribute("player", newPlayer);
        players.put(username, new Player(username));
    }

    /**
     * Method to check if a username already exists within the application
     * @param username The requested username to be validated
     * @return Boolean indicating if the username is in the Dictionary of usernames/players
     */
    public boolean usernameTaken(String username) {
        return players.keySet().contains(username);
    }

    /**
     * A method to Create a game between two players
     * @param _player1 Player 1 for the game
     * @param _player2 Player 2 for the game
     * @return game The created game
     */
    public Game startGame(String _player1, String _player2) {
        Player player1 = players.get(_player1);
        Player player2 = players.get(_player2);

        Game game = new Game(player1, player2);
        games.add(game);

        return game;
    }

    /**
     * A method to check if a given Player is in a game
     * @param _username The username of the Player we're checking for
     * @return boolean indicating if they're in a game
     */
    public boolean isInGame(String _username) {
        for(Game game : games) { //Loop through each game
            if(game.getPlayer1().getUsername().equals(_username) || game.getPlayer2().getUsername().equals(_username)) {
                //If the player's username matches player1 or player2 for the game, return true
                return true;
            }
        }

        return false;
    }

    /**
     * A method to retrieve a game given a username
     * @param _username The username of a Player in a game
     * @return The game (if it exists, otherwise null)
     */
    public Game getGame(String _username) {
        for(Game game : games) { //Loop through each game & return true if the username matches the game's Player1 or Player2 attribute
            if(game.getPlayer1().getUsername().equals(_username) || game.getPlayer2().getUsername().equals(_username)) {
                return game;
            }
        }

        return null;
    }

    /**
     * A method to return a List of all Players who are not currently in a game
     * @return available A List<String> of all players currently not in games
     */
    public List<String> getAvailablePlayers() {
        List<String> available = new ArrayList<>();
        for(String username : players.keySet()) { //Loop through each username registered with the application
            if(!isInGame(username)) //If they aren't in a game, add to the list to be returned
                available.add(username);
        }

        return available;
    }

    /**
     * A method to remove a player from the players dictionary (when they log out)
     * @param _username The username of the Player who is being removed/logged out
     */
    public void removePlayer(String _username) {
        players.remove(_username);
    }
}