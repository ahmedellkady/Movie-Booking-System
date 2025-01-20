package com.moviebooking.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLIntegrityConstraintViolationException;

import com.moviebooking.model.User;
import com.moviebooking.service.UserService;
import com.moviebooking.util.SceneUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    private Button logInButton;

    @FXML
    private Button registerButton;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private TextField usernameBox;

    @FXML
    private Text wrongCredentials;
    
    public String passedUsername = "";
    
    public Integer passedId;

    @FXML
    void exitButton(MouseEvent event) {
        // Implement exit behavior if necessary
    }

    @FXML
    void loginClick(ActionEvent event) {
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        if (username.isEmpty() || password.isEmpty()) {
            wrongCredentials.setText("Please enter both username and password.");
            wrongCredentials.setVisible(true);
            return;
        } else {
            // Create a new thread for user authentication
            Thread loginThread = new Thread(() -> {
                try {
                    User user = UserService.authenticateUser(username, password);

                    Platform.runLater(() -> {
                        if (user != null) {
                            // Set logged-in user
                            User.setLoggedInUser(user);
                            passedUsername = User.getUserName();
                            passedId = User.getID();
                            // Launch different windows based on user type
                            if (user.getUserType().equals("user")) {
//                                passedUsername = "Ahmed Saed";
                                launchUserWindow(user,passedUsername,passedId);
                            } else {
                                launchAdminWindow(user,passedUsername,passedId);
                            }
                        } else {
                            wrongCredentials.setText("Invalid username or password");
                            wrongCredentials.setVisible(true);
                            passwordBox.clear();  // Clear password field after failed login
                        }
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        wrongCredentials.setText("An error occurred during login. Please try again.");
                        wrongCredentials.setVisible(true);
                        passwordBox.clear();  // Clear password field in case of error
                    });
                    e.printStackTrace();
                }
            });

            loginThread.setName("Login Thread for " + passedUsername);
            loginThread.start();
            System.out.println(loginThread.getName() + " with ID " + loginThread.getId() + " started.");
        }
    }

    @FXML
    void registerClick(ActionEvent event) throws SQLIntegrityConstraintViolationException {
        String username = usernameBox.getText();
        String password = passwordBox.getText();
    
        if (username.isEmpty() || password.isEmpty()) {
            wrongCredentials.setText("Please enter both username and password.");
            wrongCredentials.setVisible(true);
            return;
        } else {
            try {
                boolean isRegistered = UserService.registerUser(username, password, "user");
                if (isRegistered) {
                    SceneUtil.showAlert("Registration Successful", "User registered successfully!", AlertType.INFORMATION);
                } else {
                    // Show error message if username is taken
                    wrongCredentials.setText("Username already exists. Please choose a different username.");
                    wrongCredentials.setVisible(true);
                }
            } catch (Exception e) {
                SceneUtil.showAlert("Registration Failed", "An error occurred while registering the user.", AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }
    
//    // Helper method to launch the user window
//    private void launchUserWindow(User user, String passedUsername) {
//        try {
//            Stage stage = (Stage) logInButton.getScene().getWindow(); 
//            SceneUtil.switchScene(stage, "/fxml/ViewMoviesScene.fxml");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
       private void launchUserWindow(User user, String passedUsername, Integer passedId) {
            try {
                Stage stage = (Stage) logInButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewMoviesScene.fxml"));
                Parent root = loader.load();

                // Get the controller instance and pass the username
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


//    private void launchAdminWindow(User user) {
//        try {
//            Stage stage = (Stage) logInButton.getScene().getWindow();
//            SceneUtil.switchScene(stage, "/fxml/AdminScene.fxml");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    private void launchAdminWindow(User user, String passedUsername, Integer passedId) {
            try {
                Stage stage = (Stage) logInButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminScene.fxml"));
                Parent root = loader.load();

                // Get the controller instance for the AdminScene
                AdminController controller = loader.getController();

                // Pass the username and user ID to the AdminController
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
