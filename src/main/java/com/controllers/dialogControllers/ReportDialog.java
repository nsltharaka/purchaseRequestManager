package com.controllers.dialogControllers;

import java.io.IOException;

import com.util.PurchaseRequestStatus;
import com.util.helpers.DialogPath;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class ReportDialog extends Dialog<String> {

    public static class Controller {

        @FXML
        private ComboBox<PurchaseRequestStatus> cmbStatus;

        @FXML
        private ComboBox<String> cmbType;

        @FXML
        private DatePicker dtpFrom;

        @FXML
        private DatePicker dtpTo;

        @FXML
        void initialize() {

            cmbStatus.getItems().setAll(PurchaseRequestStatus.values());
            cmbType.getItems().setAll(
                    "Purchase Request",
                    "Price Quotation Report",
                    "Purchase Order",
                    "Item");

        }

    }

    private Controller controller;
    private String query = "SELECT * FROM ";

    public ReportDialog() {
        super();
        this.setDialogPane(loadFXML());
        this.setResultConverter(this::resultConverter);
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.REPORT_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String resultConverter(ButtonType btn) {

        if (!btn.equals(ButtonType.APPLY)) {
            return null;
        }

        var from = controller.cmbType.getValue().replace(" ", "_").toLowerCase();

        query = query.concat(from);
        return query;

    }

}
