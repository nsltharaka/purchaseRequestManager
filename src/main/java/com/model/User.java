package com.model;

import com.util.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    String username;
    String password;

    @Enumerated(EnumType.STRING)
    UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User {\n\tusername: " + this.username + "\n\tpassword: " + this.password + "\n}";
    }

}