package com.model.dto;

import java.util.Map;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class PriceQuotationDTO {

    public SimpleStringProperty priceQuotationId;
    public SimpleStringProperty supplierName;
    public SimpleStringProperty supplierAddress;
    public SimpleMapProperty<ItemDTO, Double> item_quotedPrice;

    public SimpleStringProperty priceQuotationReportId;

    public PriceQuotationDTO() {
        this.priceQuotationId = new SimpleStringProperty();
        this.supplierName = new SimpleStringProperty();
        this.supplierAddress = new SimpleStringProperty();
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

    public PriceQuotationDTO setItem_quotedPrice(Map<ItemDTO, Double> item_quotedPrice) {
        this.item_quotedPrice.clear();
        this.item_quotedPrice.putAll(item_quotedPrice);
        return this;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    pr_id : %s,
                    supplierName : %s,
                    supplierAddress : %s,
                    map : %s,

                }
                """, priceQuotationId.get(), supplierName.get(), supplierAddress.get(), item_quotedPrice.toString());
    }

}
