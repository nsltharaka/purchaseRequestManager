package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.ReportDialog;

import javafx.fxml.FXML;

public class ReportController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

        var result = new ReportDialog().showAndWait();

        result.ifPresent(System.out::println);

    }

}
