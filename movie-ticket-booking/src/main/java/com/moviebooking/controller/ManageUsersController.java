package com.moviebooking.controller;

import com.moviebooking.model.User;
import com.moviebooking.service.UserService;
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

public class ManageUsersController {

    @FXML
    private Button assignAdminButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<User, Integer> id;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, String> userType;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        username.setCellValueFactory(new PropertyValueFactory<>("name"));
        userType.setCellValueFactory(new PropertyValueFactory<>("userType"));

        loadUsers();
    }

    @FXML
    void assignAdmin(ActionEvent event) {
        User selectedUser = table.getSelectionModel().getSelectedItem();
    
        if (selectedUser != null) {
            boolean adminAssigned = UserService.assignAdmin(selectedUser.getName());
            if (adminAssigned) {
                loadUsers();
                System.out.println("Successfully assigned admin to " + selectedUser.getName());
                SceneUtil.showAlert("Success", "Admin assigned successfully!", AlertType.INFORMATION);
            } else {
                System.out.println("Failed to assign admin to " + selectedUser.getName());
            }
        } else {
            System.out.println("No user selected");
        }
    }
    
    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(UserService.loadUsers());
        table.setItems(users);
        table.refresh();
    }

    @FXML
    void backToPrevScene(ActionEvent event) {
        SceneUtil.switchScene((Stage) backButton.getScene().getWindow(), "/fxml/AdminScene.fxml");
    }

    @FXML
    void getRowId(MouseEvent event) {

    }

}
