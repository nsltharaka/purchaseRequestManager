package com.model.dto;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class PriceQuotationDTO {

    SimpleStringProperty supplierName;
    SimpleStringProperty supplierAddress;
    SimpleMapProperty<Long, Double> item_quotedPrice;

}
