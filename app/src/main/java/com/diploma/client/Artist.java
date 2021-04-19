package com.diploma.client;

import com.diploma.client.data.model.User;

import java.util.Dictionary;
import java.util.List;

public class Artist extends User {
    private String nickname;
    private String VkUrl;
    private String ArtStationUrl;
    private String OtherUrl;

    private boolean available;


    List<Artwork.Genre> genres;
    List<Artwork.Style> styles;

    private String exclusions;

    Dictionary<Artwork.Type, Integer> price;

    public Artist(String login, String password, String mail, String name, String profilePictureUrl) {
        super(login, password, mail, name, profilePictureUrl);
    }
}
