package com.moviebooking.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.moviebooking.model.Movie;
import com.moviebooking.model.User;
import com.moviebooking.service.MovieService;
import com.moviebooking.util.SceneUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewMoviesController {

    @FXML
    private Button backButton;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrollPane;

    private String passedUsername;
    
    private Integer passedId;
    
    public void setPassedUserId(Integer passedId) {
        this.passedId = passedId;
    }

    public void setPassedUsername(String passedUsername) {
        this.passedUsername = passedUsername;
        System.out.println("Passed Username: " + passedUsername);
        loadAllMovies(); // Call this method after setting passedUsername
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        if (User.getLoggedInUser().getUserType().equals("user")) {
            SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/LoginScene.fxml");
        } else {
            SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ManageMoviesScene.fxml");
        }
    }

    // No longer need to load movies in initialize
    @FXML
    public void initialize() {
        setPassedUsername(passedUsername);
        System.out.println("ViewMoviesController initialized.");
    }

    private void loadAllMovies() {
        // Run the movie loading in a background thread to avoid blocking the UI thread
        Thread loadMoviesThread = new Thread(() -> {
            List<Movie> movies = MovieService.loadAllMovies();

            Platform.runLater(() -> {
                int col = 0, row = 0;

                for (Movie movie : movies) {
                    try {
                        ByteArrayInputStream bis = new ByteArrayInputStream(movie.getPoster());
                        Image image = new Image(bis);
                        ImageView iv = new ImageView(image);
                        iv.setFitHeight(200);
                        iv.setFitWidth(150);

                        iv.setOnMouseClicked(event -> launchViewSelectedMovie(movie, passedUsername,passedId));

                        grid.add(iv, col, row);
                        col++;

                        if (col == 4) {
                            col = 0;
                            row++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        loadMoviesThread.setName("Load Movies Thread");
        loadMoviesThread.start();
        System.out.println(loadMoviesThread.getName() + " with ID " + loadMoviesThread.getId() + " started.");
    }

//    private void launchViewSelectedMovie(Movie movie) {
//        Movie.setSelectedMovie(movie);
//        try {
//            Stage stage = (Stage) grid.getScene().getWindow();
//            SceneUtil.switchScene(stage, "/fxml/ViewSelectedMovieScene.fxml");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void launchViewSelectedMovie(Movie movie, String passedUsername, Integer passedId) {
    Movie.setSelectedMovie(movie);
        try {
            Stage stage = (Stage) grid.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewSelectedMovieScene.fxml"));
            Parent root = loader.load();

            // Get the controller instance and pass the username
            ViewSelectedMovieController controller = loader.getController();
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

}
