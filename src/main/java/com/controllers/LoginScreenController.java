package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.javafx.FontIcon;

import com.model.dto.UserDTO;
import com.service.UserService;
import com.util.UserRole;
import com.util.helpers.CurrentUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController implements Initializable {

    private @FXML TextField txtUsername;
    private @FXML PasswordField txtPassword;
    private @FXML Label lblInfo;
    private @FXML FontIcon btnClose;

    private UserService service;

    @FXML
    void handleLogin(ActionEvent event) throws IOException {

        // if (!validateFields()) {
        // lblInfo.setText("Fields cannot be empty");
        // return;
        // }

        // if (!validateUser()) {
        // lblInfo.setText("User not found");
        // return;
        // }

        CurrentUser.setCurrentUser(new UserDTO().setUsername("").setUserRole(UserRole.PURCHASER));
        CurrentUser.setCurrentUser(new UserDTO().setUsername("").setUserRole(UserRole.MANAGER));

        // successful login, redirects to main screen
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneController.getMainScene(stage);
        stage.close();
    }

    /**
     * 
     * @return {@code true} if checked fields are not empty
     */

    private boolean validateFields() {
        return !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }

    private boolean validateUser() {

        var userDTO = new UserDTO()
                .setUsername(txtUsername.getText())
                .setPassword(txtPassword.getText());

        Optional<UserDTO> optionalUser = service.getUser(userDTO);

        if (optionalUser.isPresent()) {
            CurrentUser.setCurrentUser(optionalUser.get());
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        service = new UserService();

        // if txtusername and txtpassword is on focus, then hide the lblInfo
        txtUsername.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                lblInfo.setText("");
        });
        txtPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue)
                lblInfo.setText("");
        });

        // close button event handler
        btnClose.setOnMouseClicked(event -> {
            SceneController.close((Stage) ((Node) event.getSource()).getScene().getWindow());
        });

    }

}