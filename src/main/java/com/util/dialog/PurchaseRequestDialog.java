package com.util.dialog;

import java.io.IOException;
import java.util.Optional;

import com.controllers.dialogControllers.PurchaseRequestDialogController;
import com.model.dto.ItemDTO;
import com.model.dto.PurchaseRequestDTO;
import com.util.helpers.DialogPath;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

public class PurchaseRequestDialog extends Dialog<PurchaseRequestDTO> {

    private PurchaseRequestDTO purchaseRequestDTO;
    private PurchaseRequestDialogController controller;

    public PurchaseRequestDialog() {
        this(new PurchaseRequestDTO());
    }

    public PurchaseRequestDialog(PurchaseRequestDTO pr) {
        super();
        this.purchaseRequestDTO = pr;

        this.setTitle("Purchase Request");
        this.setDialogPane(loadFXML());

        controller.btnAddItem.setOnAction(this::handleAddItemAction);
        controller.btnUpdateItem.setOnAction(this::handleUpdateItemAction);
        controller.btnRemoveItem.setOnAction(this::handleRemoveItemAction);

        setPropertyBindings();
        this.setResultConverter(this::resultConverter);
    }

    private void handleAddItemAction(ActionEvent event) {
        Dialog<ItemDTO> dialog = new ItemDialog();

        Optional<ItemDTO> optionalItem = dialog.showAndWait();

        optionalItem.ifPresent(i -> controller.tableItems.getItems().add(i));
    }

    private void handleUpdateItemAction(ActionEvent event) {
        var selectedItem = controller.tableItems.getSelectionModel().getSelectedItem();
        var index = controller.tableItems.getSelectionModel().getSelectedIndex();

        ItemDialog dialog = new ItemDialog();
        dialog.setPropertiesWithoutBinding(selectedItem);
        Optional<ItemDTO> optionalItem = dialog.showAndWait();

        optionalItem.ifPresent(i -> {
            i.purchaseRequestDTO = this.purchaseRequestDTO;
            controller.tableItems.getItems().remove(index);
            controller.tableItems.getItems().add(index, i);
        });
    }

    private void handleRemoveItemAction(ActionEvent event) {
        var index = controller.tableItems.getSelectionModel().getSelectedIndex();
        controller.tableItems.getItems().remove(index);
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PURCHASE_REQUEST_DIALOG.getPath()));

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

        controller.txtRequestID.textProperty().bindBidirectional(purchaseRequestDTO.requestId);
        controller.dtpRequestDate.valueProperty().bindBidirectional(purchaseRequestDTO.requestDate);
        controller.dtpDueDate.valueProperty().bindBidirectional(purchaseRequestDTO.dueDate);
        controller.cmbRequestedDepartment.valueProperty()
                .bindBidirectional(purchaseRequestDTO.requestedDepartment);
        controller.tableItems.itemsProperty().bindBidirectional(purchaseRequestDTO.itemDTOs);
    }

    private PurchaseRequestDTO resultConverter(ButtonType buttonType) {

        if (buttonType == ButtonType.APPLY) {
            return purchaseRequestDTO;
        }

        return null;
    }
}
