package com.controllers.dialogControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.model.dto.ItemDTO;
import com.model.dto.PriceQuotationDTO;
import com.model.dto.PurchaseOrderDTO;
import com.util.helpers.DialogPath;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PurchaseOrderDialog extends Dialog<PurchaseOrderDTO> {

    public static class Controller {

        private @FXML ResourceBundle resources;
        private @FXML URL location;

        private @FXML TableView<ItemDTO> tblItems;
        private @FXML TableColumn<ItemDTO, String> columnItemName;
        private @FXML TableColumn<ItemDTO, String> columnItemDescription;
        private @FXML TableColumn<ItemDTO, Integer> columnItemQuantity;
        private @FXML TableColumn<ItemDTO, Double> columnPrice;
        private @FXML TableColumn<ItemDTO, Double> columnTotal;

        private @FXML TextField txtOrderId;
        private @FXML TextField txtOrderDate;

        private @FXML TextField txtDelivery;
        private @FXML TextField txtDeliveryLocation;
        private @FXML TextField txtPaymentTerms;
        private @FXML TextArea txtAdditionalNotes;

        private @FXML Label lblSubTotal;

        @FXML
        void initialize() {

            columnItemName.setCellValueFactory(param -> param.getValue().itemName);
            columnItemDescription.setCellValueFactory(param -> param.getValue().itemDescription);
            columnItemQuantity.setCellValueFactory(param -> param.getValue().itemQuantity.asObject());
        }
    }

    private Controller controller;
    private PurchaseOrderDTO purchaseOrderDTO;

    public PurchaseOrderDialog(PurchaseOrderDTO dto, ButtonType... buttons) {
        super();
        this.setTitle("Purchase Order");
        this.setDialogPane(loadFXML());
        this.getDialogPane().getButtonTypes().addAll(buttons);
        this.setResultConverter(this::resultConverter);

        this.purchaseOrderDTO = dto;
        setPropertyBindings();

        populateTable(purchaseOrderDTO.priceQuotationDTO, purchaseOrderDTO.items);
    }

    public void setPurchaseOrderDTO(PurchaseOrderDTO purchaseOrderDTO) {
        this.purchaseOrderDTO = purchaseOrderDTO;
        setPropertyBindings();
        populateTable(purchaseOrderDTO.priceQuotationDTO, purchaseOrderDTO.items);
    }

    public PurchaseOrderDTO resultConverter(ButtonType btn) {

        if (!btn.equals(ButtonType.APPLY)) {
            return null;
        }

        return purchaseOrderDTO;
    }

    private void populateTable(PriceQuotationDTO priceQuotationDTO, SimpleListProperty<ItemDTO> itemsDTOs) {

        var map = priceQuotationDTO.item_quotedPrice.get();

        controller.columnPrice.setCellValueFactory(param -> {
            var value = map.get(param.getValue().itemId.get());
            return new SimpleDoubleProperty(value).asObject();
        });

        controller.columnTotal.setCellValueFactory(param -> {
            var value = map.get(param.getValue().itemId.get());
            var qty = param.getValue().itemQuantity.get();
            return new SimpleDoubleProperty(value * qty).asObject();
        });

        controller.lblSubTotal.setText(String.valueOf(priceQuotationDTO.quotedTotal.get() + " LKR"));

    }

    private DialogPane loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(DialogPath.PURCHASE_ORDER_DIALOG.getPath()));

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

        controller.tblItems.itemsProperty().bind(
                purchaseOrderDTO.items);

        controller.txtOrderId.textProperty().bindBidirectional(
                purchaseOrderDTO.purchaseOrderId);

        controller.txtOrderDate.textProperty().bindBidirectional(
                purchaseOrderDTO.purchaseOrderDate);

        controller.txtDelivery.textProperty().bindBidirectional(
                purchaseOrderDTO.termsAndConditions.get(0));

        controller.txtDeliveryLocation.textProperty().bindBidirectional(
                purchaseOrderDTO.termsAndConditions.get(1));

        controller.txtPaymentTerms.textProperty().bindBidirectional(
                purchaseOrderDTO.termsAndConditions.get(2));

        controller.txtAdditionalNotes.textProperty().bindBidirectional(
                purchaseOrderDTO.termsAndConditions.get(3));

    }

}
