package com.moviebooking.controller;

import com.moviebooking.model.Bookings;
import com.moviebooking.service.BookingsService;
import com.moviebooking.util.SceneUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ManageBookingHistoryController {

    @FXML
    private Button backButton;

    @FXML
    private Button cancelBookingButton;

    @FXML
    private TableColumn<Bookings, String> date;

    @FXML
    private TableColumn<Bookings, String> id;

    @FXML
    private TableColumn<Bookings, String> movie;

    @FXML
    private TableColumn<Bookings, String> seat;

    @FXML
    private TableColumn<Bookings, String> status;

    @FXML
    private TableView<Bookings> table;

    @FXML
    private TableColumn<Bookings, String> time;

    @FXML
    private TableColumn<Bookings, String> username;

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        movie.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        date.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        seat.setCellValueFactory(new PropertyValueFactory<>("seatNo"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadAllBookings();
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ManageBookingsScene.fxml");
    }

    @FXML
    void deleteBooking(ActionEvent event) {
        Bookings selectedBooking = table.getSelectionModel().getSelectedItem();
    
        if (selectedBooking != null) {
            boolean cancelBooking = BookingsService.cancelBooking(selectedBooking.getbookingId());
            if (cancelBooking) {
                loadAllBookings();
                System.out.println("Successfully cancelled booking to Ticket " + selectedBooking.getbookingId());
                SceneUtil.showAlert("Success", "Booking cancelled successfully!", AlertType.INFORMATION);
            } else {
                System.out.println("Failed to cancel booking to ticket " + selectedBooking.getbookingId());
            }
        } else {
            System.out.println("No Booking selected");
        }
    }

    @FXML
    void getRowId(MouseEvent event) {

    }

    private void loadAllBookings() {
        ObservableList<Bookings> bookings = FXCollections.observableArrayList(BookingsService.loadAllBookings());
        table.setItems(bookings);
        table.refresh();
    }

}
