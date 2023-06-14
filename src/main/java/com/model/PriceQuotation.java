package com.model;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;

@Entity
public class PriceQuotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne()
    @JoinColumn(name = "quotationReport_id")
    PriceQuotationsReport priceQuotationsReport;

    String supplierName;
    String supplierAddress;

    @ElementCollection
    @CollectionTable(name = "item_quoted_price")
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quoted_price")
    Map<Item, Double> item_quotedPrice;

}
