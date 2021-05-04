package com.diploma.client.network;

import com.diploma.client.data.model.Advert;
import com.diploma.client.data.model.Artist;
import com.diploma.client.data.model.Artwork;
import com.diploma.client.data.model.ChatMessage;
import com.diploma.client.data.model.Client;
import com.diploma.client.data.model.Picture;
import com.diploma.client.data.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JSONParser {
    public static Client parseClient(String json) throws JSONException, IOException {
        JSONObject obj = new JSONObject(json);
        int user_id = Integer.parseInt(obj.getString("user_id"));
        int client_id = Integer.parseInt(obj.getString("id"));
        ArrayList<Integer> list_of_featured_artists = new ArrayList<>();
        JSONArray listOfFeaturedArtistsJSON = obj.getJSONArray("list_of_featured_artists");
        for (int i = 0; i < listOfFeaturedArtistsJSON.length(); i++) {
            Integer artistID = Integer.parseInt(listOfFeaturedArtistsJSON.getString(i));
            list_of_featured_artists.add(artistID);
        }
        Client client = new Client(user_id, client_id);
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
        int artist_id = Integer.parseInt(obj.getString("id"));
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
            int genreId = Integer.parseInt(list_of_genresJSON.getString(i));
            list_of_genres.add(Artwork.Genre.getById(genreId));
        }

        ArrayList<Artwork.Style> list_of_styles = new ArrayList<>();
        JSONArray list_of_stylesJSON = obj.getJSONArray("list_of_styles");
        for (int i = 0; i < list_of_stylesJSON.length(); i++) {
            int styleId = Integer.parseInt(list_of_genresJSON.getString(i));
            list_of_styles.add(Artwork.Style.getById(styleId));
        }


        Artist artist = new Artist(user_id, artist_id, nickname, url_to_VK, url_to_ArtStation, url_to_Other,
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


    public static ChatMessage parseChatMessage(String json) throws JSONException, ParseException {
        JSONObject obj = new JSONObject(json);
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'"); //todo should make local time for user?
        Date serverReceivedTime = sdf.parse(obj.getString("departure_time"));

        String text = obj.getString("text");
        Integer receiverId = Integer.parseInt(obj.getString("receiver_id"));
        Integer senderId = Integer.parseInt(obj.getString("sender_id"));

        return new ChatMessage(receiverId, senderId, serverReceivedTime, text);
    }

    public static ArrayList<ChatMessage> parseChatMessageList(String json) {
        ArrayList<ChatMessage> messages = new ArrayList<>();
        try {
            JSONArray messageArray = new JSONArray(json);
            for (int i = 0; i < messageArray.length(); i++) {
                messages.add(parseChatMessage(messageArray.getJSONObject(i).toString()));
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return messages;
    }


    public static Artwork.Genre parseGenre(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int id = Integer.parseInt(obj.getString("id"));
        String name = obj.getString("name");
        String description = obj.getString("description");
        return new Artwork.Genre(id, name, description);
    }

    public static ArrayList<Artwork.Genre> parseGenreList(String json) {
        ArrayList<Artwork.Genre> resList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                resList.add(parseGenre(jsonArray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return resList;
    }

    public static Artwork.Style parseStyle(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int id = Integer.parseInt(obj.getString("id"));
        String name = obj.getString("name");
        String description = obj.getString("description");
        return new Artwork.Style(id, name, description);
    }

    public static ArrayList<Artwork.Style> parseStyleList(String json) {
        ArrayList<Artwork.Style> resList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                resList.add(parseStyle(jsonArray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return resList;
    }

    public static Artwork.Type parseType(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int id = Integer.parseInt(obj.getString("id"));
        String name = obj.getString("name");
        String description = obj.getString("description");
        return new Artwork.Type(id, name, description);
    }

    public static ArrayList<Artwork.Type> parseTypeList(String json) {
        ArrayList<Artwork.Type> resList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                resList.add(parseType(jsonArray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return resList;
    }

    public static Advert parseAdvert(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int id = Integer.parseInt(obj.getString("id"));
        int desired_value = Integer.parseInt(obj.getString("desired_value"));
        String title = obj.getString("title");
        String description = obj.getString("description");
        String additional_information = obj.getString("additional_information");
        int client_id = Integer.parseInt(obj.getString("client_id"));

        ArrayList<Artwork.Style> list_of_styles = new ArrayList<>();
        JSONArray list_of_stylesJSON = obj.getJSONArray("list_of_styles");
        for (int i = 0; i < list_of_stylesJSON.length(); i++) {
            int styleId = Integer.parseInt(list_of_stylesJSON.getString(i));
            list_of_styles.add(Artwork.Style.getById(styleId));
        }

        ArrayList<Artwork.Genre> list_of_genres = new ArrayList<>();
        JSONArray list_of_genresJSON = obj.getJSONArray("list_of_genres");
        for (int i = 0; i < list_of_genresJSON.length(); i++) {
            int genreId = Integer.parseInt(list_of_genresJSON.getString(i));
            list_of_genres.add(Artwork.Genre.getById(genreId));
        }

        return new Advert(id, desired_value,title, description,additional_information,client_id,list_of_genres, list_of_styles);
    }

    public static ArrayList<Advert> parseAdvertList(String json) {
        ArrayList<Advert> resList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                resList.add(parseAdvert(jsonArray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return resList;
    }

    public static Picture parsePicture(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        int id = Integer.parseInt(obj.getString("id"));
        String image = obj.getString("image");
        String description = obj.getString("description");
        int artistId = Integer.parseInt(obj.getString("artist_id"));

        ArrayList<Artwork.Style> list_of_styles = new ArrayList<>();
        JSONArray list_of_stylesJSON = obj.getJSONArray("list_of_styles");
        for (int i = 0; i < list_of_stylesJSON.length(); i++) {
            int styleId = Integer.parseInt(list_of_stylesJSON.getString(i));
            list_of_styles.add(Artwork.Style.getById(styleId));
        }

        ArrayList<Artwork.Genre> list_of_genres = new ArrayList<>();
        JSONArray list_of_genresJSON = obj.getJSONArray("list_of_genres");
        for (int i = 0; i < list_of_genresJSON.length(); i++) {
            int genreId = Integer.parseInt(list_of_genresJSON.getString(i));
            list_of_genres.add(Artwork.Genre.getById(genreId));
        }

        return new Picture(id, image, description,artistId, list_of_genres, list_of_styles);
    }

    public static ArrayList<Picture> parsePictureList(String json) {
        ArrayList<Picture> resList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                resList.add(parsePicture(jsonArray.getJSONObject(i).toString()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //todo logs
            String errMessage = String.format("Error while parse json: \n %s", json);
        }
        return resList;
    }
}


