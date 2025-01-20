package com.moviebooking.service;

import java.util.List;

import com.moviebooking.dao.MovieDAO;
import com.moviebooking.model.Movie;

public class MovieService {
    public static List<Movie> loadAllMovies() {
        return MovieDAO.loadMovies();
    }

    public static boolean addMovie(String name, String description, String dateFrom, String dateTo, byte[] poster,
            List<String> times) {
        return MovieDAO.addMovie(name, description, dateFrom, dateTo, poster, times);
    }
    
    public static boolean deleteMovie(String name) {
        return MovieDAO.deleteMovie(name);
    }

    public static Integer getMovieById(String name) {
        return MovieDAO.getMovieId(name);
    }
}
