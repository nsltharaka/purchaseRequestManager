package com.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationReportDialog;
import com.controllers.dialogControllers.PurchaseRequestUpdateStatusDialog;
import com.model.dto.ItemDTO;
import com.service.ItemService;
import com.util.PurchaseRequestStatus;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RequestedItemsController {

    // ITems service object
    private ItemService itemService = new ItemService();

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

    @FXML
    void initialize() {

        tblItems.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnRequestID.setCellValueFactory(param -> param.getValue().purchaseRequestId);
        columnCategory.setCellValueFactory(param -> param.getValue().itemCategory);
        columnItemName.setCellValueFactory(param -> param.getValue().itemName);
        columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
        columnItemQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        columnItemUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
        columnItemStatus.setCellValueFactory(param -> param.getValue().itemStatus);

        populateTable();
    }

    private void populateTable() {

        List<ItemDTO> allItems = itemService.selectAllItems();

        tblItems.getItems().clear();
        tblItems.getItems().addAll(allItems);

    }

    @FXML
    void ShowNewQuotationReportWindow(ActionEvent event) {

        // get selected items of the table
        ObservableList<ItemDTO> selectedItems = tblItems.getSelectionModel().getSelectedItems();

        PriceQuotationReportDialog dialog = new PriceQuotationReportDialog();
        dialog.setItems(selectedItems);

        dialog.getDialogPane().getButtonTypes().add(0, ButtonType.APPLY);
        dialog.showAndWait();
    }

    @FXML
    void updateItemStatus(ActionEvent event) {

        PurchaseRequestUpdateStatusDialog dialog = new PurchaseRequestUpdateStatusDialog();
        dialog.showAndWait();

    }

}
