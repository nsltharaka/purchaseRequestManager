package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PurchaseRequestUpdateStatusDialog;
import com.controllers.dialogControllers.PriceQuotationReportDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;

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

        PriceQuotationReportDialog dialog = new PriceQuotationReportDialog();
        dialog.getDialogPane().getButtonTypes().add(0, ButtonType.APPLY);
        dialog.showAndWait();
    }

    @FXML
    void updateItemStatus(ActionEvent event) {

        PurchaseRequestUpdateStatusDialog dialog = new PurchaseRequestUpdateStatusDialog();
        dialog.showAndWait();

    }

}
