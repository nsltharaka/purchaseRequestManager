package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.model.dto.ItemDTO;
import com.model.dto.PurchaseRequestDTO;
import com.util.Department;
import com.util.UserRole;
import com.util.helpers.CurrentUser;
import com.util.helpers.DialogPath;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PurchaseRequestDialog extends Dialog<PurchaseRequestDTO> {

    public static class Controller {

        private @FXML ResourceBundle resources;
        private @FXML URL location;
        public @FXML TableView<ItemDTO> tableItems;
        private @FXML TableColumn<ItemDTO, String> columnItemDescription;
        private @FXML TableColumn<ItemDTO, String> columnItemName;
        private @FXML TableColumn<ItemDTO, Integer> columnQuantity;
        private @FXML TableColumn<ItemDTO, String> columnUnit;
        public @FXML TextField txtRequestID;
        public @FXML ComboBox<Department> cmbRequestedDepartment;
        public @FXML DatePicker dtpRequestDate;
        public @FXML DatePicker dtpDueDate;
        public @FXML Button btnAddItem;
        public @FXML Button btnUpdateItem;
        public @FXML Button btnRemoveItem;
        public @FXML CheckBox chboxApproved;

        @FXML
        void initialize() {
            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
            columnUnit.setCellValueFactory(param -> param.getValue().quantityUnit);

            dtpRequestDate.setOnMouseClicked(e -> {
                if (dtpRequestDate.isShowing())
                    dtpRequestDate.hide();
            });

            cmbRequestedDepartment.setItems(FXCollections.observableArrayList(Department.values()));

            // if (CurrentUser.getCurrentUser().userRole.get() == UserRole.MANAGER) {
            // chboxApproved.setVisible(true);
            // }

        }

    }

    private PurchaseRequestDTO purchaseRequestDTO;
    private Controller controller;

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
            // i.purchaseRequestId = this.purchaseRequestDTO.requestId;
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
        controller.chboxApproved.visibleProperty()
                .bind(CurrentUser.getCurrentUser().userRole.isEqualTo(UserRole.MANAGER));
    }

    private PurchaseRequestDTO resultConverter(ButtonType buttonType) {

        if (buttonType == ButtonType.APPLY) {
            return purchaseRequestDTO;
        }

        return null;
    }
}
