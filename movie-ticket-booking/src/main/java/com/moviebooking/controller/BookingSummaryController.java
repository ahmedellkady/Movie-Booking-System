package com.moviebooking.controller;

import com.moviebooking.util.SceneUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookingSummaryController {

    @FXML
    private ToggleButton backButton;

    @FXML
    private ToggleButton closeButton1;

    @FXML
    private Text dateSummary;

    @FXML
    private Text filmSummary;

    @FXML
    private Text nameSummary;

    @FXML
    private Text seatSummary;

    @FXML
    private Text timeSummary;

    public void setBookingDetails(String username, String movieTitle, String date, String time, String seat) {
        System.out.println("Booking Summary: ");
        System.out.println("Username: " + username);
        System.out.println("Movie: " + movieTitle);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Seat: " + seat);

        nameSummary.setText(username);
        filmSummary.setText(movieTitle);
        dateSummary.setText(date);
        timeSummary.setText(time);
        seatSummary.setText(seat);
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ManageBookingsScene.fxml");
    }

    @FXML
    void closeStage(ActionEvent event) {
        SceneUtil.closeStage((Stage) closeButton1.getScene().getWindow());
    }

}
