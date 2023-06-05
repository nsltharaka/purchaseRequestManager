package com.controllers.dialogControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ItemDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public TextField txtItemDescription;

    @FXML
    public TextField txtItemName;

    @FXML
    public TextField txtItemUnit;

    @FXML
    public TextField txtRequestedQuantity;

    @FXML
    void initialize() {

        txtRequestedQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRequestedQuantity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }
}
