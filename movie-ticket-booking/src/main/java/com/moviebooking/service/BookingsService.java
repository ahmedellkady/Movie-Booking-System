package com.moviebooking.service;

import java.util.List;
import java.util.Set;

import com.moviebooking.dao.BookingsDAO;
import com.moviebooking.model.Bookings;
import com.moviebooking.model.User;
import java.sql.SQLException;

public class BookingsService {
    public static List<Bookings> loadAllBookings() {
        return BookingsDAO.getAllBookings();
    }

    public synchronized static boolean cancelBooking(int ticketId) {
        return BookingsDAO.cancelBooking(ticketId);
    }

    public synchronized static boolean addBooking(String selectedDate, String selectedTime, int timeId, Integer userId,
            Set<String> selectedSeats) throws SQLException {
        System.out.println("Booking Attempt Details: ");
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + User.getLoggedInUser().getUsername());
        System.out.println("Selected Date: " + selectedDate);
        System.out.println("Selected Time: " + selectedTime);
        System.out.println("Selected Seats: " + selectedSeats);
        System.out.println("Timestamp: " + System.currentTimeMillis());
        
        return BookingsDAO.addBooking(selectedDate, selectedTime, timeId, userId, selectedSeats);
    }
}
