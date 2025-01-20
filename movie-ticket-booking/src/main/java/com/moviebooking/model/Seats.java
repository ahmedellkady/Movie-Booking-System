package com.moviebooking.model;

public class Seats {
    private int id;
    private String seatNo;
    private String status;
    private int bookingId;
    private int timeId;

    public Seats(int id, String seatNo, String status, int bookingId, int timeId) {
        this.id = id;
        this.seatNo = seatNo;
        this.status = status;
        this.bookingId = bookingId;
        this.timeId = timeId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    @Override
    public String toString() {
        return "Seats [id=" + id + ", seatNo=" + seatNo + ", status=" + status + ", bookingId=" + bookingId + "]";
    }
}
