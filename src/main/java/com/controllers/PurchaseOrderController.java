package com.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PurchaseOrderDialog;
import com.model.dto.PurchaseOrderDTO;
import com.service.PurchaseOrderService;
import com.util.PurchaseRequestStatus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;

public class PurchaseOrderController {

    private PurchaseOrderService service = new PurchaseOrderService();

    @FXML
    private Button btnAddComment;

    @FXML
    private Button btnEditPo;

    @FXML
    private Button btnUpdatePO;

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

        btnAddComment.setManaged(false);
        btnEditPo.setManaged(false);
        btnUpdatePO.setManaged(false);

        columnPurchaseOrderId.setCellValueFactory(param -> param.getValue().purchaseOrderId);
        columnPurchaseOrderDate.setCellValueFactory(param -> param.getValue().purchaseOrderDate);
        columnStatus.setCellValueFactory(param -> param.getValue().status);
        columnSupplier.setCellValueFactory(param -> {
            var value = param.getValue();

            return value.priceQuotationDTO.supplierName;
        });

        populateTable();

    }

    @FXML
    void printPO() {

        var selectedItem = tblPurchaseOrders.getSelectionModel().getSelectedItem();
        DialogPane dialogPane = new PurchaseOrderDialog(selectedItem).getDialogPane();
        dialogPane.getButtonTypes().clear();
        dialogPane.setBackground(Background.EMPTY);

        PrinterJob printerJob = PrinterJob.createPrinterJob();

        // Set the job attributes
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT,
                Printer.MarginType.HARDWARE_MINIMUM);

        printerJob.setPrinter(printer);

        // Print the DialogPane to PDF
        boolean printed = printerJob.printPage(pageLayout, dialogPane);

        if (printed) {
            printerJob.endJob();
            new Alert(AlertType.INFORMATION, "printed successfully").show();
        } else {
            System.out.println("Printing failed.");
            new Alert(AlertType.ERROR, "error occurred while printing").show();
        }

    }

    @FXML
    void viewPurchaseOrder(ActionEvent event) {

        var selectedItem = tblPurchaseOrders.getSelectionModel().getSelectedItem();

        var dialog = new PurchaseOrderDialog(selectedItem);
        dialog.getDialogPane().getButtonTypes().remove(ButtonType.APPLY);
        dialog.show();

    }

    private void populateTable() {
        var pos = service.selectAll();
        tblPurchaseOrders.getItems().setAll(pos);
    }

}
