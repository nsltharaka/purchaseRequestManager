package com.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationReportDialog;
import com.controllers.dialogControllers.PurchaseOrderDialog;
import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.model.dto.PriceQuotationsReportDTO;
import com.model.dto.PurchaseOrderDTO;
import com.service.ItemService;
import com.service.PriceQuotationReportService;
import com.service.PriceQuotationService;
import com.service.PurchaseOrderService;
import com.util.PriceQuotationReportStatus;
import com.util.UserRole;
import com.util.helpers.CurrentUser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PriceQuotationsController {

    private PriceQuotationReportService priceQuotationReportService;
    private PriceQuotationService priceQuotationService;
    private PurchaseOrderService purchaseOrderService;
    private ItemService itemService;

    private PriceQuotationsReportDTO selectedPQR;

    private @FXML ResourceBundle resources;
    private @FXML URL location;

    private @FXML TableView<PriceQuotationsReportDTO> tblPriceQuotationReports;
    private @FXML TableColumn<PriceQuotationsReportDTO, String> columnReportId;
    private @FXML TableColumn<PriceQuotationsReportDTO, LocalDate> columnCreatedDate;
    private @FXML TableColumn<PriceQuotationsReportDTO, String> columnSelectedSupplier;
    private @FXML TableColumn<PriceQuotationsReportDTO, PriceQuotationReportStatus> columnStatus;

    private @FXML TableView<ItemDTO> tableItems;
    private @FXML TableColumn<ItemDTO, String> columnRequestId;
    private @FXML TableColumn<ItemDTO, String> columnItemName;
    private @FXML TableColumn<ItemDTO, String> columnItemDescription;
    private @FXML TableColumn<ItemDTO, Integer> columnQuantity;
    private @FXML TableColumn<ItemDTO, String> columnUnit;

    private @FXML Button btnApprovePurchaseRequest;

    @FXML
    void initialize() {
        priceQuotationReportService = new PriceQuotationReportService();
        priceQuotationService = new PriceQuotationService();
        purchaseOrderService = new PurchaseOrderService();
        itemService = new ItemService();

        selectedPQR = new PriceQuotationsReportDTO();

        setItemTableProperties();
        setPriceQuotationReportTableProperties();
        populateTable();

        setPropertyBindings();
        tblPriceQuotationReports.getSelectionModel().selectedItemProperty().addListener(this::handleChange);

    }

    @FXML
    void viewPriceQuotation(ActionEvent e) {

        List<PriceQuotationDTO> pqDtos = priceQuotationService
                .getPriceQuotations(selectedPQR.quotationReportId.get());

        selectedPQR.setPriceQuotationDTOs(pqDtos);

        var dialog = new PriceQuotationReportDialog(selectedPQR);
        dialog.show();

    }

    @FXML
    void approvePriceQuotationReport(ActionEvent event) {

        List<PriceQuotationDTO> pqDtos = priceQuotationService
                .getPriceQuotations(selectedPQR.quotationReportId.get());

        selectedPQR.setPriceQuotationDTOs(pqDtos);

        var dialog = new PriceQuotationReportDialog(selectedPQR, ButtonType.APPLY);
        var result = dialog.showAndWait();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        result.ifPresent(pq -> {

            try {
                priceQuotationReportService.setApproved(pq);
                alert.setContentText("price quotation report approved");
                alert.showAndWait();
                populateTable();

            } catch (UnsupportedOperationException e) {
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        });

    }

    @FXML
    void createPurchaseOrder(ActionEvent e) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        var poDto = new PurchaseOrderDTO();
        poDto.setPriceQuotationReportId(selectedPQR.quotationReportId.get());

        var approvedPq = priceQuotationService.getApprovedPriceQuotation(
                selectedPQR.quotationReportId.get());

        approvedPq.ifPresent(poDto::setPriceQuotationDTO);
        poDto.items.setAll(selectedPQR.itemsDTOs.get());

        var dialog = new PurchaseOrderDialog(poDto);
        var result = dialog.showAndWait();

        result.ifPresent(po -> {
            purchaseOrderService.insertPurchaseOrder(po);

            alert.setContentText("Purchase Order Created");
            alert.showAndWait();
            populateTable();
        });

    }

    private void setPropertyBindings() {

        tableItems.itemsProperty().bindBidirectional(selectedPQR.itemsDTOs);

        btnApprovePurchaseRequest.disableProperty()
                .bind(CurrentUser.getCurrentUser().userRole.isEqualTo(UserRole.PURCHASER));

    }

    private void handleChange(ObservableValue<? extends PriceQuotationsReportDTO> observable,
            PriceQuotationsReportDTO oldValue, PriceQuotationsReportDTO newValue) {

        if (newValue == null)
            return;

        selectedPQR
                .setQuotationReportId(newValue.quotationReportId.get())
                .setCreatedDate(newValue.createdDate.get())
                .setStatus(newValue.status.get());

        var items = itemService.getItemsOf("price_quotation_report", newValue.quotationReportId.get());
        items.ifPresent(list -> selectedPQR.setItemsDTOs(list));
    }

    private void populateTable() {
        tblPriceQuotationReports.getItems().setAll(priceQuotationReportService.getAllPriceQuotationReports());
    }

    private void setPriceQuotationReportTableProperties() {
        columnReportId.setCellValueFactory(param -> param.getValue().quotationReportId);
        columnCreatedDate.setCellValueFactory(param -> param.getValue().createdDate);
        columnStatus.setCellValueFactory(param -> param.getValue().status);

        columnSelectedSupplier.setCellValueFactory(param -> {
            var pqr = param.getValue();
            var result = pqr.priceQuotationDTOs.stream()
                    .filter(q -> q.isApproved.get())
                    .findAny();

            return result.map(pq -> pq.supplierName).orElse(new SimpleStringProperty("-"));
        });
    }

    private void setItemTableProperties() {
        columnRequestId.setCellValueFactory(param -> param.getValue().purchaseRequestId);
        columnItemName.setCellValueFactory(param -> param.getValue().itemName);
        columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
        columnQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        columnUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
    }

}
