package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.model.dto.PriceQuotationsReportDTO;
import com.util.helpers.DialogPath;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PriceQuotationReportDialog extends Dialog<PriceQuotationsReportDTO> {

    public static class PriceQuotationReportController {

        private @FXML TextField reportId;
        private @FXML DatePicker createdDate;

        private @FXML TableView<ItemDTO> tblItems;
        private @FXML TableColumn<ItemDTO, String> columnRequestId;
        private @FXML TableColumn<ItemDTO, String> columnItemName;
        private @FXML TableColumn<ItemDTO, String> columnItemDescription;
        private @FXML TableColumn<ItemDTO, Integer> columnQuantity;
        private @FXML TableColumn<ItemDTO, String> columnUnit;

        private @FXML Button btnItemRemove;

        private @FXML TableView<PriceQuotationDTO> tblQuotations;
        private @FXML TableColumn<PriceQuotationDTO, String> columnSupplierName;
        private @FXML TableColumn<PriceQuotationDTO, String> columnSupplierAddress;
        private @FXML TableColumn<PriceQuotationDTO, Double> columnQuotedPrice;

        private @FXML Button btnAddQuotation;
        private @FXML Button btnRemoveQuotation;
        private @FXML Button btnViewQuotation;

        private @FXML ResourceBundle resources;
        private @FXML URL location;

        @FXML
        void initialize() {
            setItemTableProperties();
            setQuotationTableProperties();
        }

        private void setQuotationTableProperties() {
            columnSupplierName.setCellValueFactory(param -> param.getValue().supplierName);
            columnSupplierAddress.setCellValueFactory(param -> param.getValue().supplierAddress);
            columnQuotedPrice.setCellValueFactory(param -> param.getValue().quotedTotal.asObject());
        }

        private void setItemTableProperties() {
            columnRequestId.setCellValueFactory(param -> param.getValue().purchaseRequestId);
            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
            columnUnit.setCellValueFactory(param -> param.getValue().quantityUnit);
        }
    }

    private PriceQuotationReportController controller;
    private PriceQuotationsReportDTO priceQuotationsReportDTO;

    public PriceQuotationReportDialog(PriceQuotationsReportDTO dto, ButtonType... buttonTypes) {
        super();
        this.priceQuotationsReportDTO = dto;
        this.setTitle("Price Quotation Report");
        this.setDialogPane(loadFXML());
        this.getDialogPane().getButtonTypes().addAll(buttonTypes);
        this.setResultConverter(this::resultConverter);

        setPropertyBindings();

        controller.btnAddQuotation.setOnAction(this::handleAddQuotation);
        controller.btnItemRemove.setOnAction(this::handleRemoveItem);

    }

    public PriceQuotationReportDialog(ButtonType... buttonTypes) {
        this(new PriceQuotationsReportDTO(), buttonTypes);
    }

    private PriceQuotationsReportDTO resultConverter(ButtonType buttonType) {

        if (!buttonType.equals(ButtonType.APPLY))
            return null;

        return priceQuotationsReportDTO;
    }

    private void setPropertyBindings() {

        controller.tblItems.itemsProperty().bindBidirectional(priceQuotationsReportDTO.itemsDTOs);
        controller.reportId.textProperty().bindBidirectional(priceQuotationsReportDTO.quotationReportId);
        controller.createdDate.valueProperty().bindBidirectional(priceQuotationsReportDTO.createdDate);
        controller.tblQuotations.itemsProperty().bindBidirectional(priceQuotationsReportDTO.priceQuotationDTOs);

        controller.btnItemRemove.disableProperty()
                .bind(controller.tblItems.getSelectionModel().selectedItemProperty().isNull());

        Node applyButton;
        if ((applyButton = this.getDialogPane().lookupButton(ButtonType.APPLY)) != null) {
            applyButton.disableProperty()
                    .bind(Bindings.isEmpty(controller.tblQuotations.getItems()));
        }
    }

    private void handleAddQuotation(ActionEvent e) {

        PriceQuotationDialog dialog = new PriceQuotationDialog(priceQuotationsReportDTO.itemsDTOs, ButtonType.APPLY);
        var result = dialog.showAndWait();

        result.ifPresent(priceQuotationsReportDTO.priceQuotationDTOs::add);

    }

    // TODO considering a removal
    private void handleRemoveItem(ActionEvent e) {
        var index = controller.tblItems.getSelectionModel().getSelectedIndex();
        priceQuotationsReportDTO.itemsDTOs.remove(index);
    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PRICE_QUOTATION_REPORT_DIALOG.getPath()));

        try {
            DialogPane root = loader.load();
            controller = loader.getController();

            return root;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setItems(ObservableList<ItemDTO> selectedItems) {
        priceQuotationsReportDTO.itemsDTOs.setAll(selectedItems);
    }

    public void setPriceQuotationsReportDTO(PriceQuotationsReportDTO priceQuotationsReportDTO) {
        this.priceQuotationsReportDTO = priceQuotationsReportDTO;
    }

}