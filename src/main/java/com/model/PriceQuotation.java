package com.model;

import java.util.Map;

public class PriceQuotation {

    private String quotationId;
    private String supplierName;
    private String supplierAddress;
    private Map<Item, Double> item_quotedPrice;

    private String priceQuotationsReportId;

    public String getQuotationId() {
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

    public Map<Item, Double> getItem_quotedPrice() {
        return item_quotedPrice;
    }

    public PriceQuotation setQuotationId(String id) {
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

    public PriceQuotation setItem_quotedPrice(Map<Item, Double> item_quotedPrice) {
        this.item_quotedPrice = item_quotedPrice;
        return this;
    }

}
