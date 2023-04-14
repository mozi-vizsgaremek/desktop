package com.example.cinemaapp.Movie;

import java.util.Optional;

public class Movie {
    public String id, title, subtitle, description;
    public int durationMins;

    public Movie(String title, String subtitle, String description, int durationMins) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.durationMins = durationMins;
    }

    @Override
    public String toString() {
        return title;
    }
}
