package com.diploma.client.data.model;



/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {
    public String login;
    public String password;
    public String mail;
    public String name;
    public String profilePictureUrl;

    public User(String login, String password, String mail, String name, String profilePictureUrl) {
        this.login = login;
        this.password = password;
        this.mail = mail;
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
    }
}