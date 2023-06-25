package com.model.dto;

import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class PriceQuotationDTO {

    public SimpleStringProperty priceQuotationId;
    public SimpleStringProperty supplierName;
    public SimpleStringProperty supplierAddress;
    public SimpleBooleanProperty isApproved;
    public SimpleMapProperty<String, Double> item_quotedPrice;

    public SimpleStringProperty priceQuotationReportId;

    public PriceQuotationDTO() {
        this.priceQuotationId = new SimpleStringProperty();
        this.supplierName = new SimpleStringProperty();
        this.supplierAddress = new SimpleStringProperty();
        this.isApproved = new SimpleBooleanProperty(false);
        this.item_quotedPrice = new SimpleMapProperty<>(FXCollections.observableHashMap());
    }

    public PriceQuotationDTO setPriceQuotationReportId(String priceQuotationReportId) {
        this.priceQuotationReportId.set(priceQuotationReportId);
        return this;
    }

    public PriceQuotationDTO setPriceQuotationId(String priceQuotationId) {
        this.priceQuotationId.set(priceQuotationId);
        ;
        return this;
    }

    public PriceQuotationDTO setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
        return this;
    }

    public PriceQuotationDTO setSupplierAddress(String supplierAddress) {
        this.supplierAddress.set(supplierAddress);
        return this;
    }

    public PriceQuotationDTO setItem_quotedPrice(Map<String, Double> item_quotedPrice) {
        this.item_quotedPrice.clear();
        this.item_quotedPrice.putAll(item_quotedPrice);
        return this;
    }

    public PriceQuotationDTO setIsApproved(boolean isApproved) {
        this.isApproved.set(isApproved);
        return this;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    pr_id : %s,
                    isApproved : %s
                    supplierName : %s,
                    supplierAddress : %s,
                    map : %s,

                }
                """, priceQuotationId.get(), isApproved.get(), supplierName.get(), supplierAddress.get(),
                item_quotedPrice.toString());
    }

}
