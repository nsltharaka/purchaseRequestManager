package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.PriceQuotationsReport;
import com.model.dto.ItemDTO;
import com.util.helpers.DialogPath;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
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

        private @FXML TableView<ItemDTO> tblItems;
        private @FXML TableColumn<ItemDTO, String> columnRequestId;
        private @FXML TableColumn<ItemDTO, String> columnItemName;
        private @FXML TableColumn<ItemDTO, String> columnItemDescription;
        private @FXML TableColumn<ItemDTO, Integer> columnQuantity;
        private @FXML TableColumn<ItemDTO, String> columnUnit;

        private @FXML Button btnItemRemove;

        private @FXML TableView<?> tblQuotations;
        private @FXML TableColumn<?, ?> columnSupplierName;
        private @FXML TableColumn<?, ?> columnSupplierAddress;
        private @FXML TableColumn<?, ?> columnQuotedPrice;

        private @FXML Button btnAddQuotation;
        private @FXML Button btnRemoveQuotation;
        private @FXML Button btnViewQuotation;

        private @FXML ResourceBundle resources;
        private @FXML URL location;

        @FXML
        void initialize() {

            setItemTableProperties();

        }

        private void setItemTableProperties() {
            columnRequestId.setCellValueFactory(param -> param.getValue().purchaseRequestId);
            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
            columnUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
        }
    }

    private PriceQuotationReportController controller;
    private SimpleListProperty<ItemDTO> items = new SimpleListProperty<>(FXCollections.observableArrayList());

    public PriceQuotationReportDialog() {
        super();
        this.setTitle("Price Quotation Report");
        this.setDialogPane(loadFXML());

        setPropertyBindings();
        controller.btnAddQuotation.setOnAction(this::handleAddQuotation);
        controller.btnItemRemove.setOnAction(this::handleRemoveItem);
    }

    private void setPropertyBindings() {
        controller.tblItems.itemsProperty().bindBidirectional(items);

        controller.btnItemRemove.disableProperty().bind(
                controller.tblItems.getSelectionModel().selectedItemProperty().isNull());
    }

    private void handleAddQuotation(ActionEvent e) {

        PriceQuotationDialog dialog = new PriceQuotationDialog(items);
        dialog.show();

    }

    private void handleRemoveItem(ActionEvent e) {
        var index = controller.tblItems.getSelectionModel().getSelectedIndex();
        items.remove(index);
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
        items.setAll(selectedItems);
    }

}