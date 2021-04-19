package com.diploma.client;

import com.diploma.client.data.model.User;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class Client extends User {
    public ArrayList<Integer> favouriteArtsitsIDs;

    public Client(String login, String password, String mail, String name, String profilePictureUrl) {
        super(login, password, mail, name, profilePictureUrl);
    }
}
