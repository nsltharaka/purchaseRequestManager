package com.model.dto;

import java.time.LocalDate;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class PriceQuotationsReportDTO {

    public SimpleStringProperty quotationReportId;
    public SimpleObjectProperty<LocalDate> createdDate;
    public SimpleListProperty<ItemDTO> itemsDTOs;
    public SimpleListProperty<PriceQuotationDTO> priceQuotationDTOs;

    public PriceQuotationsReportDTO() {
        quotationReportId = new SimpleStringProperty("PQ####");
        createdDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        itemsDTOs = new SimpleListProperty<>(FXCollections.observableArrayList());
        priceQuotationDTOs = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

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
