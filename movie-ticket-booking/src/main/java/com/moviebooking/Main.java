package com.moviebooking;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main extends Application {

    private static int numberOfWindows;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the number of users to open: ");
            numberOfWindows = scanner.nextInt();
        } finally {
            scanner.close();
        }

        // Create and start a thread for each user
        for (int i = 1; i <= numberOfWindows; i++) {
            final int userNumber = i;
            Thread userThread = new Thread(() -> {
                try {
                    // Launch JavaFX for this user in the current thread
                    Platform.startup(() -> {
                        Stage stage = new Stage();
                        openLoginScene(stage, "User " + userNumber);
                    });
                } catch (IllegalStateException e) {
                    Platform.runLater(() -> {
                        Stage stage = new Stage();
                        openLoginScene(stage, "User " + userNumber);
                    });
                }
            });
            userThread.setName("User " + userNumber + " Thread");
            userThread.start();
            System.out.println(userThread.getName() + " with ID " + userThread.getId() + " started.");
        }

        
    }

    @Override
    public void start(Stage primaryStage) {
        // JavaFX entry point; no need to use this for individual user threads
        Platform.exit(); // Close this initial JavaFX thread if unused
    }

    private static void openLoginScene(Stage stage, String title) {
        try {
            URL fxmlUrl = Main.class.getResource("/fxml/LoginScene.fxml");

            if (fxmlUrl == null) {
                throw new IOException("FXML file not found");
            }

            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }
}