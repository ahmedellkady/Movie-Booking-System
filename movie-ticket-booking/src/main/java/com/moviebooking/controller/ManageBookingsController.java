package com.moviebooking.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.moviebooking.model.Movie;
import com.moviebooking.model.User;
import com.moviebooking.service.BookingsService;
import com.moviebooking.service.MovieService;
import com.moviebooking.service.SeatsService;
import com.moviebooking.service.TimesService;
import com.moviebooking.util.SceneUtil;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ManageBookingsController {

    @FXML
    private MaterialIconView A1;
    @FXML
    private MaterialIconView A2;
    @FXML
    private MaterialIconView A3;
    @FXML
    private MaterialIconView A4;
    @FXML
    private MaterialIconView A5;
    @FXML
    private MaterialIconView A6;
    @FXML
    private MaterialIconView B1;
    @FXML
    private MaterialIconView B2;
    @FXML
    private MaterialIconView B3;
    @FXML
    private MaterialIconView B4;
    @FXML
    private MaterialIconView B5;
    @FXML
    private MaterialIconView B6;
    @FXML
    private MaterialIconView C1;
    @FXML
    private MaterialIconView C2;
    @FXML
    private MaterialIconView C3;
    @FXML
    private MaterialIconView C4;
    @FXML
    private MaterialIconView C5;
    @FXML
    private MaterialIconView C6;

    @FXML
    private Button backButton;
    @FXML
    private Button bookSeatButton;
    // @FXML
    // private Text customer;
    // @FXML
    // private ComboBox<String> customerDropDownList;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> filmDropDownList;
    @FXML
    private GridPane gridSeats;
    @FXML
    private ComboBox<String> timeDropDownList;
    // @FXML
    // private Button viewHistoryButton;

    private String selectedTime;
    private String selectedDate;
    private Set<String> selectedSeats = new HashSet<>();
    
    private Integer passedId;
    private String passedUsername;

    // Thread to update seats every 3 seconds
    private Thread seatUpdateThread;
    private boolean isRunning = true;

    public void setPassedUserId(Integer passedId) {
        this.passedId = passedId;
    }

    public void setPassedUsername(String passedUsername) {
        this.passedUsername = passedUsername;
        System.out.println("Passed Username in ManageBookingsController: " + passedUsername);
    }

    @FXML
    private void initialize() {
        if (User.getLoggedInUser().getUserType().equals("user")) {
            // customerDropDownList.setVisible(false);
            // customer.setVisible(false);
        }

        // populateCustomerDropDownList(null);
        populateFilmDropDownList(null);
        populateTimeDropDownList(null);

        // Start the thread for periodic seat updates
        startSeatUpdateThread();
    }

    private void startSeatUpdateThread() {
        seatUpdateThread = new Thread(() -> {
            while (isRunning) {
                try {
                    // Wait for 3 seconds before checking booked seats
                    Thread.sleep(10000);

                    // Update seats on the JavaFX application thread
                    Platform.runLater(() -> {
                        if (selectedDate != null && selectedTime != null && Movie.getSelectedMovie() != null) {
                            getBookedSeats();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        seatUpdateThread.start();
    }

    private void stopSeatUpdateThread() {
        isRunning = false;
        if (seatUpdateThread != null) {
            seatUpdateThread.interrupt();
        }
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ViewSelectedMovieScene.fxml");
    }

    @FXML
    void bookSeatOnClick(ActionEvent event) throws SQLException {
        if (validateBooking()) {
            Integer timeId = TimesService.getTimeId(Movie.getSelectedMovie().getMovieId(), selectedTime);

            System.out.println("Booking Initiated for " + passedUsername);
            System.out.println("Thread ID: " + Thread.currentThread().getId());

            if (BookingsService.addBooking(selectedDate, selectedTime, timeId, passedId, selectedSeats)) {
                System.out.println("Booking successful for " + User.getLoggedInUser().getUsername() + " Selected Seats: "
                        + selectedSeats.toString());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookingSummaryScene.fxml"));
                    Parent root = loader.load();

                    BookingSummaryController controller = loader.getController();
                    controller.setBookingDetails(passedUsername, Movie.getSelectedMovie().getName(), selectedDate, selectedTime, selectedSeats.toString());

                    Stage stage = (Stage) bookSeatButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Add notification for already booked seats and refresh the page
            }
        }
    }

    @FXML
    void handleCustomerList(ActionEvent event) {}

    @FXML
    void handleDatePicker(ActionEvent event) {
        selectedDate = datePicker.getValue().toString();
        if (selectedDate != null && selectedTime != null && Movie.getSelectedMovie() != null) {
            getBookedSeats();
        }
    }

    @FXML
    void handleFilmList(ActionEvent event) {
        String selectedFilm = filmDropDownList.getValue();
        Movie selectedMovie = null;

        for (Movie movie : MovieService.loadAllMovies()) {
            if (movie.getName().equals(selectedFilm)) {
                selectedMovie = movie;
                break;
            }
        }

        Movie.setSelectedMovie(selectedMovie);
        populateTimeDropDownList(null);
    }

    @FXML
    void handleTimeList(ActionEvent event) {
        selectedTime = timeDropDownList.getValue();

        if (selectedDate != null && selectedTime != null && Movie.getSelectedMovie() != null) {
            getBookedSeats();
        }
    }

    // @FXML
    // void populateCustomerDropDownList(MouseEvent event) {
    //     List<User> users = UserService.loadUsers();
    //     customerDropDownList.getItems().clear();
    //     for (User user : users) {
    //         if (user.getUserType().equals("user"))
    //             customerDropDownList.getItems().add(user.getUsername());
    //     }
    // }

    @FXML
    void populateFilmDropDownList(MouseEvent event) {
        List<Movie> movies = MovieService.loadAllMovies();
        filmDropDownList.getItems().clear();
        for (Movie movie : movies) {
            filmDropDownList.getItems().add(movie.getName());
        }

        if (Movie.getSelectedMovie() != null) {
            filmDropDownList.setValue(Movie.getSelectedMovie().getName());
        }
    }

    @FXML
    void populateTimeDropDownList(MouseEvent event) {
        if (Movie.getSelectedMovie() == null) {
            return;
        }

        List<String> times = TimesService.loadListOfTimesByMovie(Movie.getSelectedMovie().getMovieId());
        timeDropDownList.getItems().clear();
        for (String time : times) {
            timeDropDownList.getItems().add(time);
        }
    }

    @FXML
    void selectSeat(MouseEvent event) {
        MaterialIconView clickedSeat = (MaterialIconView) event.getSource();

        if (clickedSeat.isDisable()) {
            SceneUtil.showAlert("Error", "This seat is not available.", AlertType.ERROR);
            return;
        }

        String seatId = clickedSeat.getId();

        if (selectedSeats.contains(seatId)) {
            selectedSeats.remove(seatId);
            clickedSeat.setFill(javafx.scene.paint.Color.BLACK);
        } else {
            selectedSeats.add(seatId);
            clickedSeat.setFill(javafx.scene.paint.Color.GREEN);
        }
    }

    // @FXML
    // void viewBookingHistoryOnClick(ActionEvent event) {
    //     SceneUtil.switchScene((Stage) viewHistoryButton.getScene().getWindow(), "/fxml/ManageBookingHistoryScene.fxml");
    // }

    private void getBookedSeats() {
        System.out.println("Selected Date: " + selectedDate + " Selected Time: " + selectedTime + " Selected Movie: "
                + Movie.getSelectedMovie().getName());

        Set<String> bookedSeats = SeatsService.loadBookedSeats(selectedDate, selectedTime,
                Movie.getSelectedMovie().getName());

        System.out.println("Booked Seats: " + bookedSeats.toString());
        colorBookedSeat(bookedSeats);
    }

    private void colorBookedSeat(Set<String> seatsBooked) {
        Map<String, MaterialIconView> seatMap = Map.ofEntries(
                Map.entry("A1", A1), Map.entry("A2", A2), Map.entry("A3", A3), Map.entry("A4", A4), Map.entry("A5", A5), Map.entry("A6", A6),
                Map.entry("B1", B1), Map.entry("B2", B2), Map.entry("B3", B3), Map.entry("B4", B4), Map.entry("B5", B5), Map.entry("B6", B6),
                Map.entry("C1", C1), Map.entry("C2", C2), Map.entry("C3", C3), Map.entry("C4", C4), Map.entry("C5", C5), Map.entry("C6", C6));

        Platform.runLater(() -> {
            for (MaterialIconView seat : seatMap.values()) {
                seat.setFill(javafx.scene.paint.Color.BLACK);
                seat.setDisable(false);
            }

            for (String seat : seatsBooked) {
                MaterialIconView seatView = seatMap.get(seat);

                if (seatView != null) {
                    seatView.setFill(javafx.scene.paint.Color.RED);
                    seatView.setDisable(true);
                }
            }

            selectedSeats.clear();
        });
    }

    private boolean validateBooking() {
        if (selectedSeats.isEmpty() || selectedDate == null || selectedTime == null) {
            SceneUtil.showAlert("Error", "Please select a seat, date and time.", AlertType.ERROR);
            return false;
        }
        return true;
    }

    // Optional: Stop thread when window is closed
    @FXML
    private void handleWindowClose() {
        stopSeatUpdateThread();
    }
}
