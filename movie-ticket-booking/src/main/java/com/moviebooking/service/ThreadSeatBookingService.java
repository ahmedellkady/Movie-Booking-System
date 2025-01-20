package com.moviebooking.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSeatBookingService {
    // Singleton instance to manage global seat bookings
    private static ThreadSeatBookingService instance;
    
    // Thread-safe map to store booked seats for each show
    private final ConcurrentHashMap<String, Set<String>> bookedSeats;

    private ThreadSeatBookingService() {
        bookedSeats = new ConcurrentHashMap<>();
    }

    // Thread-safe singleton method
    public static synchronized ThreadSeatBookingService getInstance() {
        if (instance == null) {
            instance = new ThreadSeatBookingService();
        }
        return instance;
    }

    // Synchronized method to book seats
    public synchronized boolean bookSeats(String showKey, Set<String> requestedSeats) {
        // Get or create a thread-safe set of booked seats for this show
        Set<String> currentBookedSeats = bookedSeats.computeIfAbsent(
            showKey, k -> Collections.synchronizedSet(new HashSet<>())
        );

        // Check if any requested seat is already booked
        for (String seat : requestedSeats) {
            if (currentBookedSeats.contains(seat)) {
                return false; // Seat already booked
            }
        }

        // Book all seats
        currentBookedSeats.addAll(requestedSeats);
        return true;
    }

    // Method to get booked seats for a specific show
    public Set<String> getBookedSeats(String showKey) {
        return bookedSeats.getOrDefault(showKey, new HashSet<>());
    }
}