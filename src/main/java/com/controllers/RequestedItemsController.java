package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationDialog;
import com.model.PriceQuotation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class RequestedItemsController {

    private @FXML ResourceBundle resources;
    private @FXML URL location;

    @FXML
    void initialize() {
        // tblRequestedItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // how to populate table watch : https://www.youtube.com/watch?v=vego72w5kPU
        /*
         * directly from hibernate to table
         * 
         * https://stackoverflow.com/questions/33714812/javafx-fxml-populating-tableview
         * -using-hibernate
         */

    }

    @FXML
    void ShowNewQuotationReportWindow(ActionEvent event) {

        Dialog<PriceQuotation> dialog = new PriceQuotationDialog();
        dialog.getDialogPane().getButtonTypes().add(0, ButtonType.APPLY);
        dialog.showAndWait();
    }

}
