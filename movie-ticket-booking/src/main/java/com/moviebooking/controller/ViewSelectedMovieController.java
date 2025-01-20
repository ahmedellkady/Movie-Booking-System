package com.moviebooking.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.moviebooking.model.Movie;
import com.moviebooking.model.User;
import com.moviebooking.service.MovieService;
import com.moviebooking.service.TimesService;
import com.moviebooking.util.SceneUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewSelectedMovieController {

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
    
    private Integer passedId;
    
    public void setPassedUserId(Integer passedId) {
        this.passedId = passedId;
    }

    private String passedUsername;

    // Method to set the passed username
    public void setPassedUsername(String passedUsername) {
        this.passedUsername = passedUsername;
        System.out.println("Passed Username in ViewSelectedMovieController: " + passedUsername);
        Movie selectedMovie = Movie.getSelectedMovie();
        System.out.println("User: " + passedUsername + " selected movie: " + selectedMovie.getName());
//        System.out.println("User: " + User.getLoggedInUser().getUsername() + " selected movie: " + selectedMovie.getName());
        initializeMovieData(selectedMovie);
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewMoviesScene.fxml"));
            Parent root = loader.load();

            // Pass the username back to ViewMoviesController
            ViewMoviesController controller = loader.getController();
            controller.setPassedUsername(passedUsername);
            controller.setPassedUserId(passedId);

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteFilm(ActionEvent event) {
        MovieService.deleteMovie(Movie.getSelectedMovie().getName());
        SceneUtil.showAlert("Success", "Successfully deleted " + Movie.getSelectedMovie().getName(),
                AlertType.INFORMATION);
        backToPrevScene(event);
    }

    @FXML
    void goToBookingScene(ActionEvent event) {
        try {
            Stage stage = (Stage) bookButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageBookingsScene.fxml"));
            Parent root = loader.load();

            // Assuming ManageBookingsController needs the username as well
            ManageBookingsController controller = loader.getController();
            controller.setPassedUsername(passedUsername);
            controller.setPassedUserId(passedId);

            // Set the scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        
    }

    public void initializeMovieData(Movie selectedMovie) {
        if (User.getLoggedInUser().getUserType().equals("user")) {
            deleteFilmButton.setVisible(false);
        }

        Image image = new Image(new ByteArrayInputStream(selectedMovie.getPoster()));
        selectedFilmPoster.setImage(image);

        title.setText(selectedMovie.getName());
        description.setText(selectedMovie.getDescription());
        startDate.setText(selectedMovie.getDateFrom());
        endDate.setText(selectedMovie.getDateTo());

        List<String> times = TimesService.loadListOfTimesByMovie(selectedMovie.getMovieId());
        time.setText(times.toString());
    }
}
