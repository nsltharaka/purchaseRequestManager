package com.model;

import java.time.LocalDate;
import java.util.List;

import com.util.PriceQuotationReportStatus;

public class PriceQuotationsReport {

    private String id;
    private LocalDate createdDate;
    private List<Item> items;
    private List<PriceQuotation> priceQuotations;
    private PriceQuotationReportStatus status;

    public String getId() {
        return id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<PriceQuotation> getPriceQuotations() {
        return priceQuotations;
    }

    public PriceQuotationReportStatus getStatus() {
        return status;
    }

    public PriceQuotationsReport setStatus(PriceQuotationReportStatus status) {
        this.status = status;
        return this;
    }

    public PriceQuotationsReport setId(String id) {
        this.id = id;
        return this;
    }

    public PriceQuotationsReport setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public PriceQuotationsReport setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public PriceQuotationsReport setPriceQuotations(List<PriceQuotation> priceQuotations) {
        this.priceQuotations = priceQuotations;
        return this;
    }

}