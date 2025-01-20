package com.moviebooking.model;

public class User {
    private static Integer id;
    private static String username;
    private Integer user_id;
    private String name;
    private String password;
    private String userType;
    
    private static User loggedInUser;

    public User(Integer id, String username, String password, String userType) {
        this.id = id;
        this.name = username;
        this.password = password;
        this.userType = userType;
    }

    public User(int userId, String username, String userType) {
        this.user_id = userId;
        this.name = username;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }
    
    public static Integer getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    public static String getUserName() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getUserType() {
        return userType;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Type: " + userType;
    }
}

