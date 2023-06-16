package com.model.dto;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class PriceQuotationDTO {

    public SimpleStringProperty supplierName;
    public SimpleStringProperty supplierAddress;
    public SimpleMapProperty<Long, Double> item_quotedPrice;

}
