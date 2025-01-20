package com.moviebooking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.moviebooking.model.Bookings;
import com.moviebooking.util.DBConnectionUtil;

public class BookingsDAO {
    public static List<Bookings> getAllBookings() {
        List<Bookings> bookingsList = new ArrayList<>();
        String query = "SELECT t.ticket_id, u.username, m.name, t.booking_date, ti.time, s.seat_no, t.status"
                + " FROM tickets t"
                + " JOIN times ti ON t.time_id = ti.time_id"
                + " JOIN movies m ON ti.movie_id = m.movie_id"
                + " JOIN users u ON t.user_id = u.id"
                + " JOIN seats s ON s.ticket_id = t.ticket_id";

        try (Connection conn = DBConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int ticketId = rs.getInt("ticket_id");
                String username = rs.getString("username");
                String movieTitle = rs.getString("name");
                String bookingDate = rs.getString("booking_date");
                String time = rs.getString("time");
                String seatNo = rs.getString("seat_no");
                String status = rs.getString("status");
                Bookings booking = new Bookings(ticketId, username, movieTitle, bookingDate, time, seatNo, status);
                bookingsList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingsList;
    }
    
    public synchronized static boolean cancelBooking(int ticketId) {
        String query = "UPDATE tickets SET status = 'cancelled' WHERE ticket_id = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ticketId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

//    public synchronized static  boolean  addBooking(String selectedDate, String selectedTime, int timeId, int userId, Set<String> selectedSeats) throws SQLException {       
//        String query = "INSERT INTO bookings (booking_date, time_id, user_id, seat_no) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DBConnectionUtil.getConnection();
//                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setString(1, selectedDate);
//            stmt.setInt(2, timeId);
//            stmt.setInt(3, userId);
//            stmt.setString(4, String.join(",", selectedSeats)); // جمع كل المقاعد في صف واحد
//            stmt.executeUpdate();
//            int bookingId = 0;
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    bookingId = generatedKeys.getInt(1);
//                }
//            for (String seatNo : selectedSeats) {
//                query = "INSERT INTO seats (seat_no, booking_id, booking_date, time_id) VALUES (?, ?, ?, ?)";
//
//                try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
//                    stmt2.setString(1, seatNo);
//                    stmt2.setInt(2, bookingId);
//                    stmt2.setString(3, selectedDate);
//                    stmt2.setInt(4, timeId);
//                    stmt2.executeUpdate();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    }
    public synchronized static boolean addBooking(String selectedDate, String selectedTime, int timeId, int userId, Set<String> selectedSeats) throws SQLException {
    // Query to insert a new booking
    String query = "INSERT INTO bookings (booking_date, time_id, user_id, seat_no) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnectionUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        
        // Insert the booking for each selected seat
        for (String seatNo : selectedSeats) {
            // Set parameters for booking insertion (insert each seat in a separate row)
            stmt.setString(1, selectedDate);
            stmt.setInt(2, timeId);
            stmt.setInt(3, userId);
            stmt.setString(4, seatNo); // Insert the seat_no for this booking row

            // Execute the booking insertion for this seat
            stmt.executeUpdate();
        }

        // After inserting the booking(s), retrieve the generated bookingId(s)
        int bookingId = 0;
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                bookingId = generatedKeys.getInt(1); // Retrieve the first generated key as bookingId
            }
        }

        // Insert each selected seat into the seats table with the relevant booking details
        for (String seatNo : selectedSeats) {
            query = "INSERT INTO seats (seat_no, booking_id, booking_date, time_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt2 = conn.prepareStatement(query)) {
                stmt2.setString(1, seatNo);  // Insert seat number
                stmt2.setInt(2, bookingId);  // Set booking ID for the seat
                stmt2.setString(3, selectedDate); // Set booking date
                stmt2.setInt(4, timeId);  // Set time_id for the seat

                // Execute the insert into the seats table
                stmt2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;  // If error occurs, return false
            }
        }

        return true;  // If all insertions were successful, return true
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Return false if an error occurs during the booking process
    }
}
}
