package com.moviebooking.model;

public class Times {
    Integer timeId;
    String time;
    Integer movieId;
    
    public Times(Integer timeId, String time, Integer movieId) {
        this.timeId = timeId;
        this.time = time;
        this.movieId = movieId;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    
}
