package com.moviebooking.controller;

import com.moviebooking.model.User;
import com.moviebooking.util.SceneUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button assignAdminButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button manageBookingsButton;

    @FXML
    private Button manageMoviesButton;

    @FXML
    private ImageView userSceneBackground;

    @FXML
    private Label windowTitleLabel;
    
    private String passedUsername;
    
    private Integer passedId;
    
    public void setPassedUserId(Integer passedId) {
        this.passedId = passedId;
    }

    public void setPassedUsername(String passedUsername) {
        this.passedUsername = passedUsername;
    }

    @FXML
    void assignAdminClick(ActionEvent event) {
        SceneUtil.switchScene((Stage) logOutButton.getScene().getWindow(), "/fxml/ManageUsersScene.fxml");
    }

    @FXML
    void logOutClick(ActionEvent event) {
        SceneUtil.switchScene((Stage) logOutButton.getScene().getWindow(), "/fxml/LoginScene.fxml");
    }

    @FXML
    void manageBookingsClick(ActionEvent event) {
        try {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageBookingsScene.fxml"));
            Parent root = loader.load();

            // Get the controller instance and pass the parameters
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
    void manageMovieClick(ActionEvent event) {
        try {
            Stage stage = (Stage) logOutButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageMoviesScene.fxml"));
            Parent root = loader.load();

            // Get the controller instance and pass the parameters
            ManageMoviesController controller = loader.getController();
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


    // @FXML
    // public void initialize() {
    //     System.out.println("Welcome " + passedUsername + " to the Admin page!");
    // }

}
