package com.diploma.client.network;

import com.diploma.client.Artist;
import com.diploma.client.Artwork;
import com.diploma.client.Client;
import com.diploma.client.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public static Client parseClient(String json) throws JSONException, IOException {
        JSONObject obj = new JSONObject(json);
        int user_id = Integer.parseInt(obj.getString("user_id"));
        ArrayList<Integer> list_of_featured_artists = new ArrayList<>();
        JSONArray listOfFeaturedArtistsJSON = obj.getJSONArray("list_of_featured_artists");
        for (int i = 0; i < listOfFeaturedArtistsJSON.length(); i++) {
            Integer artistID = Integer.parseInt(listOfFeaturedArtistsJSON.getJSONObject(i).getString("status"));
            list_of_featured_artists.add(artistID);
        }
        Client client = new Client(user_id);
        client.favouriteArtsitsIDs = list_of_featured_artists;
        return client;
    }

    public static ArrayList<Client> parseClientArray(String json) {
        ArrayList<Client> clients = new ArrayList<>();
        try {
            JSONArray clientArray = new JSONArray(json);
            for (int i = 0; i < clientArray.length(); i++) {
                try {
                    clients.add(parseClient(clientArray.getJSONObject(i).toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                    //todo logs
                    String errMessage = String.format("Error while parse client from json: \n %s", clientArray.getJSONObject(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return clients;
    }

    public static Artist parseArtist(String json) throws JSONException, IOException {
        JSONObject obj = new JSONObject(json);
        int user_id = Integer.parseInt(obj.getString("user_id"));
        String nickname = obj.getString("nickname");
        String url_to_VK = obj.getString("url_to_VK");
        String url_to_ArtStation = obj.getString("url_to_ArtStation");
        String url_to_Other = obj.getString("url_to_Other");
        Boolean is_available = Boolean.parseBoolean(obj.getString("is_available"));
        String description_of_forbidden_topics = obj.getString("description_of_forbidden_topics");
        String description_of_additional_conditions = obj.getString("description_of_additional_conditions");
        String type_to_price = obj.getString("type_to_price");


        ArrayList<Artwork.Genre> list_of_genres = new ArrayList<>();
        JSONArray list_of_genresJSON = obj.getJSONArray("list_of_genres");
        for (int i = 0; i < list_of_genresJSON.length(); i++) {
            String genre = list_of_genresJSON.getString(i);
            list_of_genres.add(new Artwork.Genre(genre));
        }

        ArrayList<Artwork.Style> list_of_styles = new ArrayList<>();
        JSONArray list_of_stylesJSON = obj.getJSONArray("list_of_styles");
        for (int i = 0; i < list_of_stylesJSON.length(); i++) {
            String style = list_of_stylesJSON.getString(i);
            list_of_styles.add(new Artwork.Style(style));
        }


        Artist artist = new Artist(user_id, nickname, url_to_VK, url_to_ArtStation, url_to_Other,
                is_available, list_of_genres, list_of_styles,
                description_of_forbidden_topics);
        return artist;
    }

    public static ArrayList<Artist> parseArtistArray(String json) {
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            JSONArray clientArray = new JSONArray(json);
            for (int i = 0; i < clientArray.length(); i++) {
                try {
                    artists.add(parseArtist(clientArray.getJSONObject(i).toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                    //todo logs
                    String errMessage = String.format("Error while parse artists from json: \n %s", clientArray.getJSONObject(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return artists;
    }


    public static User parseUser(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int user_id = Integer.parseInt(obj.getString("id"));
        String full_name = obj.getString("full_name");
        String username = obj.getString("username");
        String email = obj.getString("email");
        String avatar = obj.getString("avatar");
        String user_type = obj.getString("user_type");

        return new User(user_id, username, email, full_name, avatar, user_type);
    }
}


