package com.model.dto;

import java.time.LocalDate;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PriceQuotationsReportDTO {

    public SimpleStringProperty quotationReportId;
    public SimpleObjectProperty<LocalDate> createdDate;
    public SimpleListProperty<ItemDTO> items;
    public SimpleListProperty<PriceQuotationDTO> priceQuotations;

}
