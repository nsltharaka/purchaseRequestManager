package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationReportDialog;
import com.model.dto.ItemDTO;
import com.model.dto.PurchaseRequestDTO;
import com.service.ItemService;
import com.service.PriceQuotationReportService;
import com.util.PurchaseRequestStatus;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class RequestedItemsController {

    private ItemService itemService;
    private ItemDTO selectedItemDTO;

    private @FXML ResourceBundle resources;
    private @FXML URL location;

    private @FXML TableView<ItemDTO> tblItems;
    private @FXML TableColumn<ItemDTO, String> columnRequestID;
    private @FXML TableColumn<ItemDTO, String> columnCategory;
    private @FXML TableColumn<ItemDTO, String> columnItemName;
    private @FXML TableColumn<ItemDTO, String> columnItemDescription;
    private @FXML TableColumn<ItemDTO, Integer> columnItemQuantity;
    private @FXML TableColumn<ItemDTO, String> columnItemUnit;
    private @FXML TableColumn<ItemDTO, PurchaseRequestStatus> columnItemStatus;

    private @FXML Button btnNewQuotation;
    private @FXML Button btnAddGRN;

    @FXML
    void initialize() {

        itemService = new ItemService();
        selectedItemDTO = new ItemDTO();

        setPropertyBindings();
        setTableProperties();
        populateTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener(this::handleChange);
    }

    private void handleChange(ObservableValue<? extends ItemDTO> observable,
            ItemDTO oldValue,
            ItemDTO newValue) {

        var result = tblItems.getSelectionModel().getSelectedItems().stream()
                .anyMatch(item -> !item.itemStatus.get().equals(PurchaseRequestStatus.PROCESSING));
        btnNewQuotation.setDisable(result);

        if (newValue.itemStatus.get().equals(PurchaseRequestStatus.DELIVERED)) {
            btnAddGRN.setDisable(true);
        } else
            btnAddGRN.setDisable(false);

    }

    private void setPropertyBindings() {

    }

    private void setTableProperties() {

        tblItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnRequestID.setCellValueFactory(param -> param.getValue().purchaseRequestId);
        columnCategory.setCellValueFactory(param -> param.getValue().itemCategory);
        columnItemName.setCellValueFactory(param -> param.getValue().itemName);
        columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
        columnItemQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        columnItemUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
        columnItemStatus.setCellValueFactory(param -> param.getValue().itemStatus);
    }

    private void populateTable() {

        var allItems = itemService.selectAllProcessingItems();

        allItems.ifPresent(list -> {
            tblItems.getItems().setAll(list);
        });

    }

    @FXML
    void ShowNewQuotationReportWindow(ActionEvent event) {

        // get selected items of the table
        ObservableList<ItemDTO> selectedItems = tblItems.getSelectionModel().getSelectedItems();

        PriceQuotationReportDialog dialog = new PriceQuotationReportDialog(ButtonType.APPLY);
        dialog.setItems(selectedItems);

        var result = dialog.showAndWait();

        var priceQuotationReportService = new PriceQuotationReportService();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        result.ifPresent(pqr -> {
            try {
                if (priceQuotationReportService.insertPriceQuotationReport(pqr)) {
                    alert.setContentText("Price Quotation Report Added");
                    alert.showAndWait();
                    populateTable();
                }
            } catch (UnsupportedOperationException e) {
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    @FXML
    void updateItemStatus(ActionEvent event) {

        var dialog = new TextInputDialog();
        dialog.setTitle("Add GRN");
        dialog.setHeaderText(null);
        dialog.setContentText("enter GRN number :");

        var result = dialog.showAndWait();

        result.ifPresent(grn -> {

            if (grn.isEmpty()) {
                return;
            }

            var itemId = tblItems.getSelectionModel().getSelectedItem().itemId.get();

            itemService.addGRN(itemId, grn);
        });
    }

}
