package com.webcheckers.model;

/**
 * An instance of a Player using the application
 */
public class Player {
    //Class Attributes
    private String username;
    private boolean isPlaying;

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
    
    /**
     * This function logs the player out of the game
     */
    public void logOut() {

    }
    
    /**
     * This function challenges another user to a game
     * @param _username
     */
    public void challengeUser(String _username) {

    }

    /**
     * This function accepts a challenge
     */
    public void acceptChallenge() {

    }
    
    /**
     * This function declines a challenges
     */
    public void declineChallenge() {

    }

    //Accessors & Mutators

    /**
     * Accessor for Username
     * @return username The player's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Accessor for the isPlaying boolean
     * @return isPlaying Whether or not the user is currently playing a game
     */

    public boolean getIsPlaying() {
        return this.isPlaying;
    }

    /**
     * Mutator for isPlaying boolean
     * @param _isPlaying Whether or not the user is playing a game (the state the user will be entering)
     */
    public void setIsPlaying(boolean _isPlaying) {
        this.isPlaying = _isPlaying;
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
