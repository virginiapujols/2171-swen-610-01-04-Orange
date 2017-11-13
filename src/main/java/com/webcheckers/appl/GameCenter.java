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
    private List<String> spectators = new ArrayList<>();

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
    public boolean isUsernameTaken(String username) {
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
     * A method to remove a game from the list of games when it is completed
     * @param _game The game to be removed
     */
    public void removeGame(Game _game) {
        games.remove(_game);
    }

    /**
     * A method to return a List of all Players who are not currently in a game or spectating
     * @return available A List<String> of all players currently not in games or spectating
     */
    public List<String> getAvailablePlayers() {
        List<String> available = new ArrayList<>();
        for(String username : players.keySet()) { //Loop through each username registered with the application
            if(!isInGame(username) && !isSpectating(username)) //If they aren't in a game or spectating, add to the list to be returned
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

    /**
     * A method to return a printed list of games ("Player1 vs. Player2") to allow for spectating
     * @return A List of strings showing what games are being played
     */
    public List<String> printAvailableGames() {
        List<String> available = new ArrayList<>();

        for(Game game : games) {
            available.add(game.getPlayer1().getUsername() + " vs. " + game.getPlayer2().getUsername());
        }

        return available;
    }

    /**
     * Function to mark a user as spectating a game
     * @param _username The username of the Player spectating a game
     */
    public void markAsSpectating(String _username) {
        spectators.add(_username);
    }

    /**
     * Function to mark a user as no longer spectating
     * @param _username The username of the player who is no longer spectating
     */
    public void endSpectating(String _username) {
        spectators.remove(_username);
    }

    /**
     * Function to signal whether or not a user is spectating a game
     * @param _username The username of the Player we're checking to see if they're spectating
     * @return Boolean indicating whether or not they're spectating
     */
    public boolean isSpectating(String _username) {
        return spectators.contains(_username);
    }
}