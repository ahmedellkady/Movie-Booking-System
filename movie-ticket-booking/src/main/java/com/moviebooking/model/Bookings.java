package com.moviebooking.model;

public class Bookings {
    private int bookingId;
    private String username;
    private String movieTitle;
    private String bookingDate;
    private String time;
    private String seatNo;
    private String status;

    public Bookings(int bookingId, String username, String movieTitle, String bookingDate, String time, String seatNo,
            String status) {
        this.bookingId = bookingId;
        this.username = username;
        this.movieTitle = movieTitle;
        this.bookingDate = bookingDate;
        this.time = time;
        this.seatNo = seatNo;
        this.status = status;
    }

    public int getbookingId() {
        return bookingId;
    }

    public void setbookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}