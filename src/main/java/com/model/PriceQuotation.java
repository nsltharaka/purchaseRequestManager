package com.model;

import java.util.Map;
import java.util.UUID;

public class PriceQuotation {

    private UUID quotationId;
    private String supplierName;
    private String supplierAddress;
    private boolean isApproved;
    private Map<String, Double> item_quotedPrice;

    private String priceQuotationsReportId;

    public UUID getQuotationId() {
        return quotationId;
    }

    public String getPriceQuotationsReportId() {
        return priceQuotationsReportId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public Map<String, Double> getItem_quotedPrice() {
        return item_quotedPrice;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public PriceQuotation setApproved(boolean isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public PriceQuotation setQuotationId(UUID id) {
        this.quotationId = id;
        return this;
    }

    public PriceQuotation setPriceQuotationsReport(String priceQuotationsReport) {
        this.priceQuotationsReportId = priceQuotationsReport;
        return this;
    }

    public PriceQuotation setSupplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public PriceQuotation setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
        return this;
    }

    public PriceQuotation setItem_quotedPrice(Map<String, Double> item_quotedPrice) {
        this.item_quotedPrice = item_quotedPrice;
        return this;
    }

}
