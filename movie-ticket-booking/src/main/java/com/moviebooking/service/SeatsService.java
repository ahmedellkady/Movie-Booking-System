package com.moviebooking.service;

import com.moviebooking.dao.SeatsDAO;
import java.util.Set;

public class SeatsService {
    public static Set<String> loadBookedSeats(String selectedDate, String selectedTime, String movieName) {
        return SeatsDAO.loadSeatsForMovieTime(selectedDate, selectedTime, movieName);
    }
    

}
