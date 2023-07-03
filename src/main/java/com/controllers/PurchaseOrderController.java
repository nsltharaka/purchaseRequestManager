package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.model.dto.PurchaseOrderDTO;
import com.service.PurchaseOrderService;
import com.util.PurchaseRequestStatus;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PurchaseOrderController {

    private PurchaseOrderService service = new PurchaseOrderService();

    @FXML
    private TableColumn<PurchaseOrderDTO, String> columnPurchaseOrderDate;

    @FXML
    private TableColumn<PurchaseOrderDTO, String> columnPurchaseOrderId;

    @FXML
    private TableColumn<PurchaseOrderDTO, PurchaseRequestStatus> columnStatus;

    @FXML
    private TableColumn<PurchaseOrderDTO, String> columnSupplier;

    @FXML
    private TableView<PurchaseOrderDTO> tblPurchaseOrders;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

        columnPurchaseOrderId.setCellValueFactory(param -> param.getValue().purchaseOrderId);
        columnPurchaseOrderDate.setCellValueFactory(param -> param.getValue().purchaseOrderDate);
        columnStatus.setCellValueFactory(param -> param.getValue().status);
        columnSupplier.setCellValueFactory(param -> {
            var value = param.getValue();

            return value.priceQuotationDTO.supplierName;
        });

        populateTable();

    }

    private void populateTable() {
        var pos = service.selectAll();
        tblPurchaseOrders.getItems().setAll(pos);
    }

}
