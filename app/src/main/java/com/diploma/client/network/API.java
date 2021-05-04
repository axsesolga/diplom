package com.diploma.client.network;

import android.content.Context;

import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.ChatMessage;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class API {
    // create json for http request
    private static String getJson(HashMap<String, String> values) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : values.keySet()) {
                jsonObject.put(key, values.get(key));
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Can not create JSON with selected values.");
        }
    }


    public static User login(String username, String password) throws IOException {
        String requesUrl = Network.homeUrl + "login/";
        String json = getJson(new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
        }});

        Integer userId = Integer.parseInt(Network.doPostRequest(requesUrl, json));

        // login successful, lets get user data
        // todo сделать новый post запрос на сервере чтобы получить всю инфу о юзере
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

    public static User register(String username, String password, String mail, String name, String userType) throws IOException, SecurityException {
        String requesUrl = Network.homeUrl + "register/";
        String json = getJson(new HashMap<String, String>() {{
            put("username", username);
            put("password", password);
            put("email", mail);
            put("full_name", name);
            put("user_type", userType);
        }});
        String res = Network.doPostRequest(requesUrl, json); // if ok no exception thrown

        return login(username, password);
    }


    public static List<? extends User> getAllClients() throws IOException, SecurityException {
        String requesUrl = Network.homeUrl + "client-profile/all/";

        return JSONParser.parseClientArray(Network.doGetRequest(requesUrl));
    }

    public static List<? extends User> getAllArtists() throws IOException {
        String requesUrl = Network.homeUrl + "artist-profile/all/";

        return JSONParser.parseArtistArray(Network.doGetRequest(requesUrl));
    }


    public static List<ChatMessage> getAllChatMessages() throws IOException, SecurityException {
        String requesUrl = Network.homeUrl + "chat-message/user/all/";
        return JSONParser.parseChatMessageList(Network.doGetRequest(requesUrl));
    }

    public static ArrayList<Artwork.Genre> getAllGenres() throws IOException {
        String requesUrl = Network.homeUrl + "genre/all/";
        return JSONParser.parseGenreList(Network.doGetRequest(requesUrl));
    }

    public static ArrayList<Artwork.Style> getAllStyles() throws IOException {
        String requesUrl = Network.homeUrl + "style/all/";
        String resStr = Network.doGetRequest(requesUrl);
        return JSONParser.parseStyleList(resStr);
    }

    public static ArrayList<Artwork.Type> getAllTypes() throws IOException {
        String requesUrl = Network.homeUrl + "type/all/";
        return JSONParser.parseTypeList(Network.doGetRequest(requesUrl));
    }

    /*
    Used only in Client/Artist instance creation
     */
    public static User getBaseUser(int user_id) throws IOException, JSONException {
        String requesUrl = Network.homeUrl + "base-user/info/" + user_id;
        return JSONParser.parseUser(Network.doGetRequest(requesUrl));
    }

    public static void logout() throws IOException {
        String requesUrl = Network.homeUrl + "logout";
        Network.doGetRequest(requesUrl);
    }

    public static ArrayList<Advert> getAllAdverts() throws IOException {
        String requesUrl = Network.homeUrl + "advertisement/all/";
        return JSONParser.parseAdvertList(Network.doGetRequest(requesUrl));
    }

    public static ArrayList<Picture> getAllPictures() throws IOException {
        String requesUrl = Network.homeUrl + "picture/all/";
        return JSONParser.parsePictureList(Network.doGetRequest(requesUrl));
    }

    public static void createNewPicture(Picture picture, Context context) throws IOException {
        String requesUrl = Network.homeUrl + "picture/create/";

        String json = getJson(new HashMap<String, String>() {{
            put("image", picture.base64string);
            put("description", picture.description);
            put("artist_id", Integer.toString(picture.artist_id));
        }});
        json = removeLastChar(json) + ",";

        json = json + "\"list_of_genres\":[2],\"list_of_styles\":[2]}";
        Network.doPostRequest(requesUrl, json);
    }
    public static String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }
}
