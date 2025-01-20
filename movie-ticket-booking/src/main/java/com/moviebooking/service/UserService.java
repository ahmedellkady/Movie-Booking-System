package com.moviebooking.service;

import java.util.List;

import com.moviebooking.dao.UserDAO;
import com.moviebooking.model.User;

public class UserService {
    public static User authenticateUser(String username, String password) {
        if (UserDAO.authenticateUser(username, password) != null) {
            User user = UserDAO.authenticateUser(username, password);
            return user;
        } else {
            return null;
        }
    }

    public static boolean registerUser(String username, String password, String userType) {
        return UserDAO.registerUser(username, password, userType);
    }

    public static boolean assignAdmin(String username) {
        return UserDAO.assignAdmin(username);
    }

    public static List<User> loadUsers() {
        return UserDAO.loadUsers();
    }
}
