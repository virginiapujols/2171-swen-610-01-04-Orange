package com.webcheckers.appl;

import spark.Session;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import com.webcheckers.model.Player;

/**
 * The object to coordinate the state of the Web Application.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public class GameCenter {
    // Attributes
    private List<String> usernames;

    //
    // Public methods
    //
    public List<String> getUsernames() {
        return usernames;
    }

    public boolean addUsername(String username) {
        String toSearch = username.toLowerCase();

        for(String str: usernames) {
            if(str.trim().contains(toSearch)) {
                return false;
            }
        }

        usernames.add(username);
        return true;
    }

}