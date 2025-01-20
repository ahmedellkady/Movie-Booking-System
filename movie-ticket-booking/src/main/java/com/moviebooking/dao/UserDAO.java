package com.moviebooking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.model.User;
import com.moviebooking.util.DBConnectionUtil;

public class UserDAO {
    public synchronized static boolean registerUser(String username, String password, String userType) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)";
    
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
    
            // Check if username already exists
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                // Username exists
                return false;
            }
    
            // Insert new user if username doesn't exist
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.setString(3, userType);
                insertStmt.executeUpdate();
                return true;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        
    public static User authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
                
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Integer userId = rs.getInt("id");
                String userType = rs.getString("user_type");
                return new User(userId, username, password, userType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public synchronized static boolean assignAdmin(String username) {
        String query = "UPDATE users SET user_type = 'admin' WHERE username = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
    
        String query = "SELECT * FROM users";
    
        try (Connection conn = DBConnectionUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String userType = rs.getString("user_type");
                users.add(new User(userId, username, userType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return users;
    }
}
