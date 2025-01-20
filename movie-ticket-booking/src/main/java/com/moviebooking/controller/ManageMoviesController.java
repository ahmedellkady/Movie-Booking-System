package com.moviebooking.controller;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.model.Movie;
import com.moviebooking.service.MovieService;
import com.moviebooking.util.SceneUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManageMoviesController {

    @FXML
    private Button backButton;

    @FXML
    private TextArea filmDescription;

    @FXML
    private DatePicker filmEndDate;

    @FXML
    private DatePicker filmStartDate;

    @FXML
    private ComboBox<String> filmTime1;

    @FXML
    private ComboBox<String> filmTime2;

    @FXML
    private ComboBox<String> filmTime3;

    @FXML
    private TextField filmTitle;

    @FXML
    private Text newFilmDescription;

    @FXML
    private Text newFilmEndDate;

    @FXML
    private Text newFilmStartDate;

    @FXML
    private Text newFilmTime1;

    @FXML
    private Text newFilmTime2;

    @FXML
    private Text newFilmTime3;

    @FXML
    private Text newFilmTitle;

    @FXML
    private ImageView uploadedFilmPoster;

    private byte[] posterBytes;

    private String passedUsername;
    
    private Integer passedId;
    
    public void setPassedUserId(Integer passedId) {
        this.passedId = passedId;
    }

    public void setPassedUsername(String passedUsername) {
        this.passedUsername = passedUsername;
    }

    public void initialize() {
        filmTime1.getItems().addAll("10:00 AM", "12:00 PM", "16:00 PM", "20:00 PM", "12:00 AM");
        filmTime2.getItems().addAll("10:00 AM", "12:00 PM", "16:00 PM", "20:00 PM", "12:00 AM");
        filmTime3.getItems().addAll("10:00 AM", "12:00 PM", "16:00 PM", "20:00 PM", "12:00 AM");
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/AdminScene.fxml");
    }

    @FXML
    void launchViewFilms(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/ViewMoviesScene.fxml");
    }

    @FXML
    void storeFilmInfo(ActionEvent event) {
        if (isFormValid()) {
            List<String> times = new ArrayList<>();
            
            times.add(filmTime1.getValue().toString());
            times.add(filmTime2.getValue().toString());
            times.add(filmTime3.getValue().toString());

            System.out.println("Times: " + times);

            Movie movie = new Movie(filmTitle.getText(), filmDescription.getText(), filmStartDate.getValue().toString(),
                    filmEndDate.getValue().toString(), posterBytes, times);
            
            MovieService.addMovie(movie.getName(), movie.getDescription(), movie.getDateFrom(), movie.getDateTo(),
                    movie.getPoster(), movie.getTimes());

            SceneUtil.showAlert("Movie Added", "The movie has been added successfully!", AlertType.INFORMATION);
            clearForm();
        }
    }

    @FXML
    void updateDateAndTime(ActionEvent event) {
        updatePreviewText();
    }

    @FXML
    void updateFilmText(KeyEvent event) {
        updatePreviewText();
    }

    @FXML
    void uploadImageClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Movie Poster");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                posterBytes = Files.readAllBytes(selectedFile.toPath());

                Image image = new Image(new ByteArrayInputStream(posterBytes));
                uploadedFilmPoster.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePreviewText() {
        newFilmTitle.setText(filmTitle.getText().isEmpty() ? "Movie Title" : filmTitle.getText());
        newFilmDescription.setText(filmDescription.getText().isEmpty() ? "Movie Description" : filmDescription.getText());
        newFilmStartDate.setText(filmStartDate.getValue() == null ? "yyyy-mm-dd" : filmStartDate.getValue().toString());
        newFilmEndDate.setText(filmEndDate.getValue() == null ? "yyyy-mm-dd" : filmEndDate.getValue().toString());
        newFilmTime1.setText(filmTime1.getValue() == null ? "hh:mm" : filmTime1.getValue().toString());
        newFilmTime2.setText(filmTime2.getValue() == null ? "hh:mm" : filmTime2.getValue().toString());
        newFilmTime3.setText(filmTime3.getValue() == null ? "hh:mm" : filmTime3.getValue().toString());
    }

    private boolean isFormValid() {
        if (filmTitle.getText().isEmpty() || filmStartDate.getValue() == null || filmEndDate.getValue() == null
                || filmTime1.getValue() == null || filmTime2.getValue() == null || filmTime3.getValue() == null
                || filmDescription.getText().isEmpty() || posterBytes == null) {

            SceneUtil.showAlert("Error", "Please fill in all the required fields.", AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void clearForm() {
        filmTitle.clear();
        filmDescription.clear();
        filmStartDate.setValue(null);
        filmEndDate.setValue(null);
        filmTime1.setValue(null);
        filmTime2.setValue(null);
        filmTime3.setValue(null);
        uploadedFilmPoster.setImage(null);
        posterBytes = null;

        newFilmTitle.setText("Movie Title");
        newFilmDescription.setText("Movie Description");
        newFilmStartDate.setText("yyyy-mm-dd");
        newFilmEndDate.setText("yyyy-mm-dd");
        newFilmTime1.setText("hh:mm");
        newFilmTime2.setText("hh:mm");
        newFilmTime3.setText("hh:mm");
    }
}
