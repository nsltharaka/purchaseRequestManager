package com.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.util.PriceQuotationReportStatus;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class PriceQuotationsReportDTO {

    public SimpleStringProperty quotationReportId;
    public SimpleObjectProperty<LocalDate> createdDate;
    public SimpleObjectProperty<PriceQuotationReportStatus> status;
    public SimpleListProperty<ItemDTO> itemsDTOs;
    public SimpleListProperty<PriceQuotationDTO> priceQuotationDTOs;

    public PriceQuotationsReportDTO() {
        quotationReportId = new SimpleStringProperty("PQ####");
        createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        status = new SimpleObjectProperty<PriceQuotationReportStatus>(PriceQuotationReportStatus.PENDING_APPROVAL);
        itemsDTOs = new SimpleListProperty<>(FXCollections.observableArrayList());
        priceQuotationDTOs = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public PriceQuotationsReportDTO setQuotationReportId(String quotationReportId) {
        this.quotationReportId.set(quotationReportId);
        return this;
    }

    public PriceQuotationsReportDTO setCreatedDate(LocalDate createdDate) {
        this.createdDate.set(createdDate);
        return this;
    }

    public PriceQuotationsReportDTO setItemsDTOs(List<ItemDTO> itemsDTOs) {
        this.itemsDTOs.setAll(itemsDTOs);
        return this;
    }

    public PriceQuotationsReportDTO setPriceQuotationDTOs(List<PriceQuotationDTO> priceQuotationDTOs) {
        this.priceQuotationDTOs.setAll(priceQuotationDTOs);
        return this;
    }

    public PriceQuotationsReportDTO setStatus(PriceQuotationReportStatus status) {
        this.status.set(status);
        return this;
    }

    @Override
    public String toString() {

        return """
                {
                    report_id : %s,
                    date : %s,
                    items : %s,
                    PQs : %s,
                }
                """.formatted(quotationReportId.get(), createdDate.toString(), itemsDTOs.get().toString(),
                priceQuotationDTOs.get().toString());

    }

}
