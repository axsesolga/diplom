package com.diploma.client.network;

import com.diploma.client.Artist;
import com.diploma.client.Client;
import com.diploma.client.data.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class API {

    private static final String homeUrl = "http://192.168.31.207:8000/api/";

    public static String getAuthJsonPost(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getRegisterJsonPost(String username, String password, String mail, String name, String isArtist) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("email", mail);
            jsonObject.put("full_name", name);
            jsonObject.put("user_type", isArtist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Response response = new OkHttpClient().newCall(request).execute();
        if (((int) response.code()) == 200 || ((int) response.code())  == 201) { // 201 = creation
            return response.body().string();
        } else if (((int) response.code()) == 403) {
            throw new SecurityException("invalid login data");
        } else
            throw new IOException(response.message().toString());
    }

    public static String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response = new OkHttpClient().newCall(request).execute();
        if (((int) response.code()) == 200) {
            return response.body().string();
        } else if (((int) response.code()) == 403) {
            throw new SecurityException("invalid login data");
        } else
            throw new IOException(response.message().toString());
    }


    public static User login(String username, String password) throws Exception {
        String requesUrl = homeUrl + "login/";
        String json = getAuthJsonPost(username, password);
        Integer userId = Integer.parseInt(doPostRequest(requesUrl, json));
        // login successful, lets get user data
        for (User user : getAllArtists()) {
            if (user.user_id == userId)
                return user;
        }

        for (User user : getAllClients()) {
            if (user.user_id == userId)
                return user;
        }

        throw new InvalidParameterException("No such username found");
    }

    public static User register(String username, String password, String mail, String name, String userType) throws Exception {
        String requesUrl = homeUrl + "register/";
        String json = getRegisterJsonPost(username, password,mail,name,userType);
        String res = doPostRequest(requesUrl, json); // if ok no exception thrown

        return login(username, password);
    }


    public static List<? extends User> getAllClients() {
        String requesUrl = homeUrl + "client-profile/all/";
        try {
            String resStr = doGetRequest(requesUrl);
            return JSONParser.parseClientArray(resStr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<? extends User> getAllArtists() {
        String requesUrl = homeUrl + "artist-profile/all/";
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            String resStr = doGetRequest(requesUrl);
            return JSONParser.parseArtistArray(resStr);

        } catch (IOException  e) {
            e.printStackTrace();
        }
        return artists;
    }



    /*
    Used only in Client/Artist creation
     */
    public static User getBaseUser(int user_id) throws IOException, JSONException {
        String requesUrl = homeUrl + "base-user/info/" + user_id;

            String resStr = doGetRequest(requesUrl);
            return JSONParser.parseUser(resStr);
    }
}
