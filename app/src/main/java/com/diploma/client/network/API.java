package com.diploma.client.network;

import com.diploma.client.Artist;
import com.diploma.client.Client;
import com.diploma.client.data.model.User;


public class API {
    private static final String homeUrl = "";

    public static boolean verifyUserExists(String log) {
        String reguesUrl = homeUrl + "/" + "verifyUser=";
        return true;
    }

    public static User getUserClient() {
        return new Client("TestClient", "Test", "Test", "TestClient", "Test");
    }


    public static User getUserArtist() {
        return new Artist("TestArtist", "Test", "Test", "TestArtist", "Test");
    }


    public static boolean verifyUserPassword(String username, String password) {
        return true;
    }

    public static User getUser(String username) {
        return getUserClient();
    }

    public static boolean verifyMailAvailable(String mail) {
        return true;
    }

    public static User createUser(String username, String password, String mail, boolean isArtist) {
        if (isArtist)
            return getUserArtist();
        else
            return getUserClient();

    }
}
