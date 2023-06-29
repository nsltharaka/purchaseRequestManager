package com.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.controllers.dialogControllers.PriceQuotationReportDialog;
import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.model.dto.PriceQuotationsReportDTO;
import com.service.ItemService;
import com.service.PriceQuotationReportService;
import com.service.PriceQuotationService;
import com.util.PriceQuotationReportStatus;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PriceQuotationsController {

    private PriceQuotationReportService priceQuotationReportService;
    private PriceQuotationService priceQuotationService;
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

    @FXML
    void initialize() {
        priceQuotationReportService = new PriceQuotationReportService();
        priceQuotationService = new PriceQuotationService();
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

    private void setPropertyBindings() {

        tableItems.itemsProperty().bindBidirectional(selectedPQR.itemsDTOs);

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
