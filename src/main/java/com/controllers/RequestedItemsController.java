package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

public class RequestedItemsController {

    private @FXML ResourceBundle resources;
    private @FXML URL location;
    private @FXML TableView<?> tblRequestedItems;

    @FXML
    void initialize() {
        tblRequestedItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // how to populate table watch : https://www.youtube.com/watch?v=vego72w5kPU
        /*
         * directly from hibernate to table
         * 
         * https://stackoverflow.com/questions/33714812/javafx-fxml-populating-tableview
         * -using-hibernate
         */

    }

}
