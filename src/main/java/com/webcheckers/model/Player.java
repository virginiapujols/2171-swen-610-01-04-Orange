package com.webcheckers.model;

/**
 * An instance of a Player using the application
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 * @author <a href='mailto:nrd8504@rit.edu'>Niharika Dalal</a>
 * @author <a href='mailto:vp2532@rit.edu'>Virginia Pujols</a>
 * @author <a href='mailto:ask5893@rit.edu'>Ashok Kesari</a>
 */
public class Player {
    //Class Attributes
    private String username;
    private int gamesLost = 0;
    private int gamesWon = 0;

    /**
     * Parameterized Constructor for the Player Class
     * @param _username The username for the player
     */
    public Player(String _username) {
        this.username = _username;
    }
    
    /**
     * Accessor for Username
     * @return username The player's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Accessor for the number of games lost by a player
     * @return gamesLost The # of games lost this session
     */
    public int getGamesLost() {
        return gamesLost;
    }

    /**
     * Accessor for the number of games won by a player
     * @return gamesWon The # of games won this session
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Method to increment loss count by 1
     */
    public void updateGamesLost() {
        this.gamesLost += 1;
    }

    /**
     * Method to increment win count by 1
     */
    public void updateGamesWon() {
        this.gamesWon += 1;
    }

    /**
     * Equality function to match Player objects based on username
     * @param obj The object being tested for equality
     * @return whether or not the objects match
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Player) {
            Player current = (Player)obj;
            return current.getUsername().equals(this.username);
        }
        return false;
    }

    /**
     * A method to return a hash based on the username
     * @return an integer value representing a hash based on the username
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
