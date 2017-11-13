package com.webcheckers.model;

/**
 * An instance of a Player using the application
 */
public class Player {
    //Class Attributes
    private String username;
    private boolean isPlaying;
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
     * This function denotes a player logging in
     * @param _username this is the username of the player
     * @return a Boolean "true" or "false"
     */
    public boolean login(String _username) {
        return true;
    }


    //Accessors & Mutators

    /**
     * Accessor for Username
     * @return username The player's username
     */
    public String getUsername() {
        return this.username;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void updateGamesLost() {
        this.gamesLost += 1;
    }

    public void updateGamesWon() {
        this.gamesWon += 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Player) {
            Player current = (Player)obj;
            return current.getUsername().equals(this.username);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}
