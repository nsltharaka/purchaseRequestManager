package com.model;

import java.util.Map;

public class PriceQuotation {

    Long id;
    PriceQuotationsReport priceQuotationsReport;
    String supplierName;
    String supplierAddress;
    Map<Item, Double> item_quotedPrice;

}
