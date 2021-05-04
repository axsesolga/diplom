package com.diploma.client.data.model;

import android.content.res.Resources;

import java.util.ArrayList;

public class Artwork {
    public static class Genre {
        public static ArrayList<Genre> allGenres;

        private int id;
        private String name;
        private String description;

        public Genre(int id, String name, String description) {
            this.id = id;
            this.description = description;
            this.name = name;
        }

        public static Genre getById(int id) {
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

    public static class Type // тип в ценах. Фул, халф, пр...
    {
        public static ArrayList<Type> allTypes;

        private int id;
        private String name;
        private String description;

        public Type(int id, String name, String description) {
            this.id = id;
            this.description = description;
            this.name = name;
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

    public static class Style {
        public static ArrayList<Style> allStyles;

        private int id;
        private String name;
        private String description;

        public Style(int id, String name, String description) {
            this.id = id;
            this.description = description;
            this.name = name;
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
