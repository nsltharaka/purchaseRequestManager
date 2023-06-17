package com.model;

import com.util.UserRole;

public class User {

    String username;
    String password;

    UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User {\n\tusername: " + this.username + "\n\tpassword: " + this.password + "\n}";
    }

}