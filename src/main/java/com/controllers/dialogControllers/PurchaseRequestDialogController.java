package com.controllers.dialogControllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.dto.ItemDTO;
import com.util.Department;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PurchaseRequestDialogController {

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

    }

}