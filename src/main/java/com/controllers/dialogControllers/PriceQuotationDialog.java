package com.controllers.dialogControllers;

import java.io.IOException;

import com.model.dto.PriceQuotationDTO;
import com.util.helpers.DialogPath;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PriceQuotationDialog extends Dialog<PriceQuotationDTO> {

    public static class PriceQuotationController {

        private @FXML CheckBox chkSelectedSupplier;

        private @FXML TableView<?> tblItems;
        private @FXML TableColumn<?, ?> columnItemDescription;
        private @FXML TableColumn<?, ?> columnItemName;
        private @FXML TableColumn<?, ?> columnItemQuantity;
        private @FXML TableColumn<?, ?> columnItemUnit;

        private @FXML TextField txtSupplierName;
        private @FXML TextArea txtSupplierAddress;
        private @FXML TextField txtQuotedPrice;
    }

    private PriceQuotationController controller;

    public PriceQuotationDialog() {
        super();
        this.setTitle("Price Quotation");
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

}
