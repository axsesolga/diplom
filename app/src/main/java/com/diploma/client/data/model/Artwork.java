package com.diploma.client.data.model;

import android.content.res.Resources;

import java.util.ArrayList;

public class Artwork {
    public static class ArtworkProperties
    {
        public int id;
        public String name;
        public String description;

        public ArtworkProperties(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }

    public static class Genre extends ArtworkProperties{
        public static ArrayList<Genre> allGenres;


        public Genre(int id, String name, String description) {
            super(id, name, description);
        }

        public Genre getById(int id) {
            for (Genre genre : allGenres) {
                if (genre.id == id)
                    return genre;
            }
            throw new Resources.NotFoundException("No genre with this name found. Update local repo of genres.");
        }


        @Override
        public String toString() {
            return "Genre{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static class Type extends ArtworkProperties // тип в ценах. Фул, халф, пр...
    {
        public static ArrayList<Type> allTypes;

        private int id;
        private String name;
        private String description;

        public Type(int id, String name, String description) {
            super(id, name, description);
        }

        public static Type getById(int id) {
            for (Type type : allTypes) {
                if (type.id == id)
                    return type;
            }
            throw new Resources.NotFoundException("No type with this name found. Update local repo of types.");
        }

        @Override
        public String toString() {
            return "Type{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public static class Style extends ArtworkProperties{
        public static ArrayList<Style> allStyles;

        public int id;
        private String name;
        private String description;

        public Style(int id, String name, String description) {
            super(id, name, description);
        }

        public static Style   getById(int id) {
            for (Style style : allStyles) {
                if (style.id == id)
                    return style;
            }
            throw new Resources.NotFoundException("No style with this name found. Update local repo of styles.");
        }

        @Override
        public String toString() {
            return "Style{" +
                    "name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
