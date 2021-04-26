package com.diploma.client.data.model;


import com.diploma.client.BuildConfig;
import com.diploma.client.network.API;

import org.json.JSONException;

import java.io.IOException;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {
    public int user_id;
    public String login;
    public String mail;
    public String name;
    public String profilePictureUrl;
    public String userType;

    public boolean isClient()
    {
        return userType.equals("Client");
    }
    public boolean isArtist()
    {
        return userType.equals("Artist");
    }

    public User( int user_id, String login, String mail, String name, String profilePictureUrl, String userType) {
        this.user_id = user_id;
        this.login = login;
        this.mail = mail;
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;

        //todo remove
        if (BuildConfig.DEBUG && !(userType.equals("Client") || userType.equals("Artist"))) {
            throw new AssertionError("Assertion failed");
        }

        this.userType = userType;
    }

    public User(int user_id) throws IOException, JSONException {
        User user = API.getBaseUser(user_id);
        this.user_id = user.user_id;
        this.login = user.login;
        this.mail = user.mail;
        this.name = user.name;
        this.profilePictureUrl = user.profilePictureUrl;
        this.userType = user.userType;
    }
}