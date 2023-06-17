package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.PriceQuotationsReport;
import com.model.dto.ItemDTO;
import com.util.helpers.DialogPath;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PriceQuotationReportDialog extends Dialog<PriceQuotationsReport> {

    public static class PriceQuotationReportController {

        private @FXML TextField reportId;
        private @FXML DatePicker createdDate;

        private @FXML TableView<?> tblItems;
        private @FXML TableColumn<?, ?> columnItemDescription;
        private @FXML TableColumn<?, ?> columnQuantity;
        private @FXML TableColumn<?, ?> columnQuotedPrice;
        private @FXML TableColumn<?, ?> columnRequestId;
        private @FXML TableColumn<?, ?> columnItemName;
        private @FXML Button btnItemRemove;

        private @FXML TableView<?> tblQuotations;
        private @FXML TableColumn<?, ?> columnSupplierAddress;
        private @FXML TableColumn<?, ?> columnSupplierName;
        private @FXML TableColumn<?, ?> columnUnit;
        private @FXML Button btnAddQuotation;
        private @FXML Button btnRemoveQuotation;
        private @FXML Button btnViewQuotation;

        private @FXML ResourceBundle resources;
        private @FXML URL location;

        @FXML
        void initialize() {
        }
    }

    private PriceQuotationReportController controller;
    private SimpleListProperty<ItemDTO> items = new SimpleListProperty<>();

    public PriceQuotationReportDialog() {
        super();
        this.setTitle("Price Quotation Report");
        this.setDialogPane(loadFXML());

        setPropertyBindings();

        controller.btnAddQuotation.setOnAction(this::handleAddQuotation);

    }

    private void setPropertyBindings() {

    }

    private void handleAddQuotation(ActionEvent e) {

    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PRICE_QUOTATION_REPORT_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setItems(ObservableList<ItemDTO> selectedItems) {
        items.clear();
        selectedItems.forEach(items::add);
    }
}