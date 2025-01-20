package com.moviebooking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.model.Movie;
import com.moviebooking.util.DBConnectionUtil;

public class MovieDAO {

    public static List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM movies";

        try (Connection conn = DBConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("movie_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String dateFrom = rs.getString("date_from");
                String dateTo = rs.getString("date_to");
                byte[] poster = rs.getBytes("poster");

                movies.add(new Movie(id, name, description, dateFrom, dateTo, poster));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
     
    public synchronized static boolean addMovie(String name, String description, String dateFrom, String dateTo, byte[] poster,
            List<String> times) {
        String query = "INSERT INTO movies (name, description, date_from, date_to, poster) VALUES (?, ?, ?, ?, ?)";
        String query2 = "INSERT INTO times (movie_id, time) VALUES (?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmt2 = conn.prepareStatement(query2)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, dateFrom);
            stmt.setString(4, dateTo);
            stmt.setBytes(5, poster);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie added successfully!");

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int movieId = rs.getInt(1);

                        for (String time : times) {
                            stmt2.setInt(1, movieId);
                            stmt2.setString(2, time);
                            stmt2.executeUpdate();
                        }

                        System.out.println("Times added successfully!");
                    } else {
                        System.out.println("Failed to add movie times.");
                    }
                }
                return true;
            } else {
                System.out.println("Failed to add movie.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public synchronized static boolean deleteMovie(String name) {
        String query = "DELETE FROM movies WHERE name = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static Integer getMovieId(String name) {
        String query = "SELECT movie_id FROM movies WHERE name = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("movie_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
