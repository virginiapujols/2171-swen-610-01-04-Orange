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

    public boolean login(String _username) {
        return true;
    }

    public void logOut() {

    }

    public void challengeUser(String _username) {

    }

    public void acceptChallenge() {

    }

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
