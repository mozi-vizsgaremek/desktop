package com.example.cinemaapp.Movie;

import java.util.Optional;

public class Movie {
    public String id, title, subtitle, description, thumbnailUrl, bannerUrl;
    public int durationMins;

    public Movie(String title, String subtitle, String description, int durationMins) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.durationMins = durationMins;
    }
    public Movie(String title, String subtitle, String description, int durationMins, String thumbnailUrl, String bannerUrl) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.durationMins = durationMins;
        this.thumbnailUrl = thumbnailUrl;
        this.bannerUrl = bannerUrl;
    }

    @Override
    public String toString() {
        return title;
    }
}
