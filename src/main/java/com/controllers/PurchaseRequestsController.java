package com.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationReportDialog;
import com.controllers.dialogControllers.PurchaseRequestDialog;
import com.controllers.dialogControllers.PurchaseRequestUpdateStatusDialog;
import com.model.dto.ItemDTO;
import com.model.dto.PurchaseRequestDTO;
import com.service.ItemService;
import com.service.PurchaseRequestService;
import com.util.Department;
import com.util.PurchaseRequestStatus;
import com.util.UserRole;
import com.util.helpers.CurrentUser;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PurchaseRequestsController {

    private PurchaseRequestService service;
    private ItemService itemService;
    private PurchaseRequestDTO selectedPurchaseRequestDTO;

    private @FXML TableView<ItemDTO> tableItemsInPR;
    private @FXML TableColumn<ItemDTO, String> columnItemDescription;
    private @FXML TableColumn<ItemDTO, String> columnItemName;
    private @FXML TableColumn<ItemDTO, Integer> columnQuantity;
    private @FXML TableColumn<ItemDTO, String> columnUnit;

    private @FXML TableView<PurchaseRequestDTO> tablePurchaseRequests;
    private @FXML TableColumn<PurchaseRequestDTO, LocalDate> columnRequestDate;
    private @FXML TableColumn<PurchaseRequestDTO, LocalDate> columnRequestDueDate;
    private @FXML TableColumn<PurchaseRequestDTO, String> columnRequestId;
    private @FXML TableColumn<PurchaseRequestDTO, PurchaseRequestStatus> columnRequestStatus;
    private @FXML TableColumn<PurchaseRequestDTO, Department> columnRequestedDepartment;
    private @FXML Button btnViewPurchaseRequest;
    private @FXML Button btnApprovePurchaseRequest;
    private @FXML ResourceBundle resources;
    private @FXML URL location;

    @FXML
    void initialize() {
        service = new PurchaseRequestService(CurrentUser.getCurrentUser());
        itemService = new ItemService();

        selectedPurchaseRequestDTO = new PurchaseRequestDTO();

        columnRequestId.setCellValueFactory(param -> param.getValue().requestId);
        columnRequestDate.setCellValueFactory(param -> param.getValue().requestDate);
        columnRequestDueDate.setCellValueFactory(param -> param.getValue().dueDate);
        columnRequestedDepartment.setCellValueFactory(param -> param.getValue().requestedDepartment);
        columnRequestStatus.setCellValueFactory(param -> param.getValue().requestStatus);

        columnItemName.setCellValueFactory(param -> param.getValue().itemName);
        columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
        columnQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        columnUnit.setCellValueFactory(param -> param.getValue().quantityUnit);

        tablePurchaseRequests.getSelectionModel().selectedItemProperty().addListener(this::handleItemSelected);

        setPropertyBindings();
        populateTable();
    }

    private void handleItemSelected(ObservableValue<? extends PurchaseRequestDTO> observable,
            PurchaseRequestDTO oldValue,
            PurchaseRequestDTO newValue) {

        if (newValue == null)
            return;

        selectedPurchaseRequestDTO
                .setRequestId(newValue.requestId.get())
                .setRequestDate(newValue.requestDate.get())
                .setDueDate(newValue.dueDate.get())
                .setRequestStatus(newValue.requestStatus.get())
                .setIsApproved(newValue.isApproved.get())
                .setRequestedDepartment(newValue.requestedDepartment.get());

        var items = itemService.getItemsOf("purchase_request", newValue.requestId.get());
        items.ifPresent(list -> {
            selectedPurchaseRequestDTO.setItemDTOs(list);
        });
    }

    private void setPropertyBindings() {
        btnViewPurchaseRequest.disableProperty()
                .bind(tablePurchaseRequests.getSelectionModel().selectedItemProperty().isNull());
        btnApprovePurchaseRequest.disableProperty()
                .bind(CurrentUser.getCurrentUser().userRole.isEqualTo(UserRole.PURCHASER));

        tableItemsInPR.itemsProperty().bindBidirectional(selectedPurchaseRequestDTO.itemDTOs);

    }

    private void populateTable() {
        tablePurchaseRequests.getItems().clear();
        service.getAllPurchaseRequests().ifPresent(tablePurchaseRequests.getItems()::addAll);
    }

    @FXML
    void showAddPurchaseRequestWindow(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        Dialog<PurchaseRequestDTO> dialog = new PurchaseRequestDialog();
        Optional<PurchaseRequestDTO> optionalRequestDTO = dialog.showAndWait();

        optionalRequestDTO.ifPresent(requestDTO -> {
            try {
                if (service.insertPurchaseRequest(requestDTO)) {
                    alert.setContentText("Purchase Request Created");
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
    void viewPurchaseRequest(ActionEvent event) {
        PurchaseRequestDialog dialog = new PurchaseRequestDialog(selectedPurchaseRequestDTO);
        // HACK
        dialog.getDialogPane().getButtonTypes()
                .removeIf(btn -> btn.equals(ButtonType.APPLY) || btn.equals(ButtonType.CANCEL));
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        dialog.show();
    }

    @FXML
    void approvePurchaseRequest(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        PurchaseRequestDialog dialog = new PurchaseRequestDialog(selectedPurchaseRequestDTO);
        Optional<PurchaseRequestDTO> optionalRequestDTO = dialog.showAndWait();

        optionalRequestDTO.ifPresent(requestDTO -> {
            try {
                if (service.updatePurchaseRequestApproved(requestDTO)) {
                    alert.setContentText("Purchase Request Updated");
                    alert.showAndWait();
                    populateTable();
                }

            } catch (UnsupportedOperationException e) {
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

    }
}