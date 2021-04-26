package com.diploma.client;

import com.diploma.client.data.model.User;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class Client extends User {
    public ArrayList<Integer> favouriteArtsitsIDs;

    public Client(int user_id) throws IOException, JSONException {
        super(user_id);
    }
}
