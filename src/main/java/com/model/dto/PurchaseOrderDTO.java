package com.model.dto;

import java.time.LocalDate;

import com.util.PurchaseRequestStatus;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseOrderDTO {

    public SimpleStringProperty purchaseOrderId = new SimpleStringProperty();
    public SimpleStringProperty purchaseOrderDate = new SimpleStringProperty(LocalDate.now().toString());
    public SimpleStringProperty priceQuotationReportId = new SimpleStringProperty();
    public SimpleObjectProperty<PurchaseRequestStatus> status = new SimpleObjectProperty<>(
            PurchaseRequestStatus.AWAITING);

    public ObservableList<SimpleStringProperty> termsAndConditions = FXCollections.observableArrayList(
            new SimpleStringProperty(),
            new SimpleStringProperty(),
            new SimpleStringProperty(),
            new SimpleStringProperty());

    public PriceQuotationDTO priceQuotationDTO;
    public SimpleListProperty<ItemDTO> items = new SimpleListProperty<>(FXCollections.observableArrayList());

    public PurchaseOrderDTO setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId.set(purchaseOrderId);
        return this;
    }

    public PurchaseOrderDTO setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate.set(purchaseOrderDate.toString());
        return this;
    }

    public PurchaseOrderDTO setPriceQuotationReportId(String priceQuotationReportId) {
        this.priceQuotationReportId.set(priceQuotationReportId);
        return this;
    }

    public PurchaseOrderDTO setPriceQuotationDTO(PriceQuotationDTO dto) {
        this.priceQuotationDTO = dto;
        return this;
    }

    public PurchaseOrderDTO setStatus(PurchaseRequestStatus status) {
        this.status.set(status);
        return this;
    }

    public PurchaseOrderDTO setDelivery(String delivery) {
        this.termsAndConditions.get(0).set(delivery);
        return this;
    }

    public PurchaseOrderDTO setDeliveryLocation(String deliveryLocation) {
        this.termsAndConditions.get(1).set(deliveryLocation);
        return this;
    }

    public PurchaseOrderDTO setPayment(String payment) {
        this.termsAndConditions.get(2).set(payment);
        return this;
    }

    public PurchaseOrderDTO setAdditionalNote(String additional) {
        this.termsAndConditions.get(3).set(additional);
        return this;
    }

}
