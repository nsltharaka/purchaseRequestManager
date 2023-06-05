package com.model.dto;

import com.util.UserRole;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserDTO {
    public SimpleStringProperty username;
    public SimpleStringProperty password;
    public SimpleObjectProperty<UserRole> userRole;

    public UserDTO() {

        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.userRole = new SimpleObjectProperty<UserRole>();
    }

    public UserDTO setUsername(String username) {
        this.username.set(username);
        return this;
    }

    public UserDTO setPassword(String password) {
        this.password.set(password);
        return this;
    }

    public UserDTO setUserRole(UserRole userRole) {
        this.userRole.set(userRole);
        return this;
    }
}