package com.diploma.client.network;

import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.Client;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model.User;
import com.diploma.client.solo_activities.chat.UserChatMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidParameterException;
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


    public static ArrayList<UserChatMessage> getAllChatMessages() throws IOException, SecurityException {
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
        String requesUrl = Network.homeUrl + "job-type/all/";
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

    public static void createNewPicture(Picture picture) throws IOException {
        String requesUrl = Network.homeUrl + "picture/create/";

        StringBuilder genres = new StringBuilder();
        genres.append("[");
        for (Artwork.Genre genre : picture.genres) {
            genres.append(genre.id);
            genres.append(",");
        }
        genres.deleteCharAt(genres.length() - 1).toString();
        genres.append("]");

        StringBuilder styles = new StringBuilder();
        styles.append("[");
        for (Artwork.Style style : picture.styles) {
            styles.append(style.id);
            styles.append(",");
        }
        styles.deleteCharAt(styles.length() - 1).toString();
        styles.append("]");

        String json = getJson(new HashMap<String, String>() {{
            put("image", picture.base64string);
            put("description", picture.description);
            put("artist_id", Integer.toString(picture.artist_id));
            put("list_of_genres", "GENRES_LIST");
            put("list_of_styles", "STYLES_LIST");
        }});

        json = json.replace("\"GENRES_LIST\"", genres.toString());
        json = json.replace("\"STYLES_LIST\"", styles.toString());

        if (!Network.doPostRequest(requesUrl, json).contains("\"id\":"))
            throw new IOException("ivalid data, can not create image");
    }


    public static void sendMessage(String text, int senderId, int reciverId) throws IOException {
        String requesUrl = Network.homeUrl + "chat-message/create/";

        String json = getJson(new HashMap<String, String>() {{
            put("receiver_id", Integer.toString(reciverId));
            put("sender_id", Integer.toString(senderId));
            put("text", text);
        }});

        if (!Network.doPostRequest(requesUrl, json).contains("\"departure_time\":"))
            throw new IOException("ivalid data, can not send message");
    }


    public static void createAdvert(Advert advert) throws IOException {
        String requesUrl = Network.homeUrl + "advertisement/create/";

        StringBuilder genres = new StringBuilder();
        genres.append("[");
        for (Artwork.Genre genre : advert.genres) {
            genres.append(genre.id);
            genres.append(",");
        }
        genres.deleteCharAt(genres.length() - 1).toString();
        genres.append("]");

        StringBuilder styles = new StringBuilder();
        styles.append("[");
        for (Artwork.Style style : advert.styles) {
            styles.append(style.id);
            styles.append(",");
        }
        styles.deleteCharAt(styles.length() - 1).toString();
        styles.append("]");


        String json = getJson(new HashMap<String, String>() {{
            put("title", advert.title);
            put("description", advert.description);
            put("client_id", Integer.toString(advert.client_id));
            put("list_of_genres", "GENRES_LIST");
            put("list_of_styles", "STYLES_LIST");
            put("additional_information", advert.additional_information);
            put("desired_value", String.valueOf(advert.desired_value));
        }});

        json = json.replace("\"GENRES_LIST\"", genres.toString());
        json = json.replace("\"STYLES_LIST\"", styles.toString());

        if (!Network.doPostRequest(requesUrl, json).contains("\"id\":"))
            throw new IOException("ivalid data, can not create advert");

    }

    public static void updateBaseUser(Client client) throws IOException {

        String requesUrl = Network.homeUrl + "base-user/update/" + String.valueOf(client.client_id) + "/";

        String json = getJson(new HashMap<String, String>() {{
            put("email", client.mail);
            put("full_name", client.name);
            put("picture_url", client.profilePictureUrl);
            put("username", client.login);
            put("user_type", client.userType);
        }});

        String res = Network.doPostRequest(requesUrl, json); // if ok no exception thrown
    }



    public static void updateClient(Client client) throws IOException {

        String requesUrl = Network.homeUrl + "user/update/" + String.valueOf(client.client_id) + "/";

        String json = getJson(new HashMap<String, String>() {{
            //put("list_of_featured_artists", client.favouriteArtsitsIDs);
            put("user_id", String.valueOf(client.user_id));
        }});

        String res = Network.doPostRequest(requesUrl, json); // if ok no exception thrown
    }

    public static void updateArtist(Artist artist) throws IOException {
        String requesUrl = Network.homeUrl + "artist-profile/update/" + String.valueOf(artist.artist_id) + "/";

        StringBuilder genres = new StringBuilder();
        genres.append("[");
        for (Artwork.Genre genre : artist.genres) {
            genres.append(genre.id);
            genres.append(",");
        }
        genres.deleteCharAt(genres.length() - 1).toString();
        genres.append("]");

        StringBuilder styles = new StringBuilder();
        styles.append("[");
        for (Artwork.Style style : artist.styles) {
            styles.append(style.id);
            styles.append(",");
        }
        styles.deleteCharAt(styles.length() - 1).toString();
        styles.append("]");

        String json = getJson(new HashMap<String, String>() {{
            put("user_id", String.valueOf(artist.user_id));
            put("nickname", artist.nickname);
            put("url_to_VK", artist.VkUrl);
            put("url_to_ArtStation", artist.ArtStationUrl);
            put("url_to_Other", artist.OtherUrl);
            put("is_available", artist.available ? "true":"false");
            put("list_of_genres", "GENRES_LIST");
            put("list_of_styles", "STYLES_LIST");
            put("description_of_forbidden_topics", artist.exclusions);
            put("description_of_additional_conditions", artist.exclusions);
            put("type_to_price", "PRICE_LIST");
        }});

        json = json.replace("\"GENRES_LIST\"", genres.toString());
        json = json.replace("\"STYLES_LIST\"", styles.toString());
        json = json.replace("\"PRICE_LIST\"", "[]");

        String res = Network.doPostRequest(requesUrl, json); // if ok no exception thrown
    }
}