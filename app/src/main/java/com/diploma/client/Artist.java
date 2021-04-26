package com.diploma.client;

import com.diploma.client.data.model.User;

import org.json.JSONException;

import java.io.IOException;
import java.util.Dictionary;
import java.util.List;

public class Artist extends User {
    public String nickname;
    public String VkUrl;
    public String ArtStationUrl;
    public String OtherUrl;

    public boolean available;


    public List<Artwork.Genre> genres;
    public List<Artwork.Style> styles;

    public String exclusions;

    public Dictionary<Artwork.Type, Integer> price;

    public Artist(int user_id, String nickname, String vkUrl, String artStationUrl, String otherUrl,
                  boolean available,
                  List<Artwork.Genre> genres, List<Artwork.Style> styles,
                  String exclusions)
            throws IOException, JSONException {

        super(user_id);

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
