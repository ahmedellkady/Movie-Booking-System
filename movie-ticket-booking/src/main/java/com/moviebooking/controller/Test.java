package com.moviebooking.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.moviebooking.model.Movie;
import com.moviebooking.model.User;
import com.moviebooking.service.MovieService;
import com.moviebooking.service.TimesService;
import com.moviebooking.util.SceneUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test {

    @FXML
    private Button backButton;

    @FXML
    private Button bookButton;

    @FXML
    private Button deleteFilmButton;

    @FXML
    private Text description;

    @FXML
    private Text endDate;

    @FXML
    private ImageView selectedFilmPoster;

    @FXML
    private Text startDate;

    @FXML
    private Text time;

    @FXML
    private Text title;

    @FXML
    void backToPrevScene(ActionEvent event) {
        switchSceneToViewMovies();
    }

    @FXML
    void deleteFilm(ActionEvent event) {
        Movie selectedMovie = Movie.getSelectedMovie();
        MovieService.deleteMovie(selectedMovie.getName());
        SceneUtil.showAlert("Success", "Successfully deleted " + selectedMovie.getName(), AlertType.INFORMATION);
        switchSceneToViewMovies();
    }

    @FXML
    void goToBookingScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ManageBookingsScene.fxml");
    }

    @FXML
    public void initialize() {
        Movie selectedMovie = Movie.getSelectedMovie();
        System.out.println(selectedMovie.getName() + " is selected.");
        initializeMovieData(selectedMovie);
    }

    private void initializeMovieData(Movie selectedMovie) {
        // Hide delete button if user type is 'user'
        if (User.getLoggedInUser().getUserType().equals("user")) {
            deleteFilmButton.setVisible(false);
        }

        // Set movie poster
        try {
            Image image = new Image(new ByteArrayInputStream(selectedMovie.getPoster()));
            selectedFilmPoster.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();  // You can show a default image or error message to the user
        }

        // Set movie details
        title.setText(selectedMovie.getName());
        description.setText(selectedMovie.getDescription());
        startDate.setText(selectedMovie.getDateFrom());
        endDate.setText(selectedMovie.getDateTo());

        // Display times
        List<String> times = TimesService.loadListOfTimesByMovie(selectedMovie.getMovieId());
        String formattedTimes = times.stream().collect(Collectors.joining(", "));
        time.setText(formattedTimes);
    }

    private void switchSceneToViewMovies() {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ViewMoviesScene.fxml");
    }
}
