package com.moviebooking.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtil {
    public static <T> T switchScene(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneUtil.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
            return null;
        }
    }
    
    public static void switchScene1(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneUtil.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static void closeStage(Stage stage) {
        stage.close();
    }

    public static void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
