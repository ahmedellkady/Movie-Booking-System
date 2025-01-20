package com.moviebooking.model;

import java.util.List;

public class Movie {
    private int movieId;
    private String name;
    private String description;
    private String dateFrom;
    private String dateTo;
    private byte[] poster;
    private List<String> times;

    private static Movie selectedMovie;

    // Constructor
    public Movie(int movieId, String name, String description, String dateFrom, String dateTo, byte[] poster) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.poster = poster;
    }

    public Movie(String name, String description, String dateFrom, String dateTo, byte[] poster, List<String> times) {
        this.name = name;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.poster = poster;
        this.times = times;
    }

    // Getters
    public int getMovieId() {
        return movieId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPoster() {
        return poster;
    }

    public static Movie getSelectedMovie() {
        return selectedMovie;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public static void setSelectedMovie(Movie selectedMovie) {
        Movie.selectedMovie = selectedMovie;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    // Display movie details
    @Override
    public String toString() {
        return "Movie ID: " + movieId + ", Name: " + name + ", Description: " + description + ", Date From: " + dateFrom + ", Date To: " + dateTo;
    }
}
