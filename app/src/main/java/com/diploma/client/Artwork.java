package com.diploma.client;

public class Artwork {
    public static class Genre
    {
        private String name;
        private String description;
        public Genre(String name)
        {
            this.description = name;
            this.name = name;
        }
    }
    public class Type
    {
        private String name;
        private String description;
    }
    public static class Style
    {
        private String name;
        private String description;
        public Style(String name)
        {
            this.description = name;
            this.name = name;
        }
    }


}
