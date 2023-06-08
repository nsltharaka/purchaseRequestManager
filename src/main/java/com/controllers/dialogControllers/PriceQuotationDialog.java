package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.PriceQuotation;
import com.util.helpers.DialogPath;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class PriceQuotationDialog extends Dialog<PriceQuotation> {

    public static class PriceQuotationController {

        private @FXML ResourceBundle resources;
        private @FXML URL location;

        @FXML
        void initialize() {
        }
    }

    private PriceQuotationController controller;

    public PriceQuotationDialog() {
        super();
        this.setTitle("Price Quotation Report");
        this.setDialogPane(loadFXML());
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PRICE_QUOTATION_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setPropertyBindings() {

    }

}