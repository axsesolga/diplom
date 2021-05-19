package com.diploma.client.data.model;

import java.util.ArrayList;

public class Advert {
    public int id;
    public int desired_value;
    public String title;
    public String description;
    public String additional_information;
    public int client_id;
    public ArrayList<Artwork.Genre> genres;
    public ArrayList<Artwork.Style> styles;

    public Advert(int id, int desired_value, String title, String description, String additional_information,
                  int client_id, ArrayList<Artwork.Genre> genres, ArrayList<Artwork.Style> styles)
    {
     this.id = id;
     this.desired_value = desired_value;
        this.title = title;
        this.description = description;
        this.additional_information = additional_information;
        this.client_id = client_id;
        this.genres = genres;
        this.styles = styles;
    }
}
