package com.moviebooking.service;

import java.util.ArrayList;
import java.util.List;

import com.moviebooking.dao.TimesDAO;
import com.moviebooking.model.Times;

public class TimesService {
    public static List<String> loadListOfTimesByMovie(int movieId) {
        List<Times> times = TimesDAO.loadTimesByMovie(movieId);
        List<String> timesString = new ArrayList<>();

        for (Times time : times) {
            timesString.add(time.getTime());
        }

        return timesString;
    }

    public static Integer getTimeId(int movieId, String time) {
        return TimesDAO.getTimeId(movieId, time);
    }

    public static List<Times> loadTimesByMovie(int movieId) {
        return TimesDAO.loadTimesByMovie(movieId);
    }
}
