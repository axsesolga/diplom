package com.diploma.client.data.model;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Artist extends User {
    public int artist_id;
    public String nickname;
    public String VkUrl;
    public String ArtStationUrl;
    public String OtherUrl;

    public boolean available;


    public List<Artwork.Genre> genres = new ArrayList<>();
    public List<Artwork.Style> styles = new ArrayList<>();;

    public String exclusions;

    public HashMap<Artwork.Type, Integer> price = new HashMap<>();

    public Artist(int user_id, int artist_id, String nickname, String vkUrl, String artStationUrl, String otherUrl,
                  boolean available,
                  List<Artwork.Genre> genres, List<Artwork.Style> styles,
                  String exclusions)
            throws IOException, JSONException {

        super(user_id);
        this.artist_id = artist_id;
        this.nickname = nickname;
        this.VkUrl = vkUrl;
        this.ArtStationUrl = artStationUrl;
        this.OtherUrl = otherUrl;
        this.available = available;
        this.genres = genres;
        this.styles = styles;
        this.exclusions = exclusions;
    }

}
