package com.model.dto;

import java.time.LocalDate;

import com.util.helpers.Tuple;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseOrderDTO {

    public SimpleStringProperty purchaseOrderId = new SimpleStringProperty();
    public SimpleStringProperty purchaseOrderDate = new SimpleStringProperty(LocalDate.now().toString());
    public ObservableList<SimpleStringProperty> termsAndConditions = FXCollections.observableArrayList(
            new SimpleStringProperty(),
            new SimpleStringProperty(),
            new SimpleStringProperty(),
            new SimpleStringProperty());

    public PriceQuotationDTO priceQuotationDTO;
    public SimpleListProperty<String> itemIds;

    public PurchaseOrderDTO setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId.set(purchaseOrderId);
        return this;
    }

    public PurchaseOrderDTO setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate.set(purchaseOrderDate.toString());
        return this;
    }

}
