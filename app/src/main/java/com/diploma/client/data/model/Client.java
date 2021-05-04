package com.diploma.client.data.model;

import com.diploma.client.data.model.User;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class Client extends User {
    public int client_id;
    public ArrayList<Integer> favouriteArtsitsIDs;

    public Client(int user_id, int client_id) throws IOException, JSONException {
        super(user_id);
        this.client_id = client_id;
    }
}
