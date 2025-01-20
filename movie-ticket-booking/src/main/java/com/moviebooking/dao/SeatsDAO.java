package com.moviebooking.dao;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import com.moviebooking.util.DBConnectionUtil;

public class SeatsDAO {
    public static Set<String> loadSeatsForMovieTime(String selectedDate, String selectedTime, String movieName) {
        Set<String> seats = new HashSet<>();
        String query = "SELECT s.seat_no FROM seats s"
                        + " JOIN bookings b on s.booking_id = b.booking_id"
                        + " JOIN times t on b.time_id = t.time_id"
                        + " JOIN movies m on t.movie_id = m.movie_id" 
                        + " WHERE m.name = ? AND t.time = ? AND b.booking_date = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, movieName);
            pstmt.setString(2, selectedTime);
            pstmt.setString(3, selectedDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String seat = rs.getString("seat_no");

                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

}