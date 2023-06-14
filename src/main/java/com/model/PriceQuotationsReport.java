package com.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PriceQuotationsReport {

    @Id
    @GeneratedValue(generator = "pq-gen")
    @GenericGenerator(name = "pq-gen", strategy = "com.model.PriceQuotation")
    private String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "priceQuotationsReport", orphanRemoval = true)
    List<Item> items;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "priceQuotationsReport", orphanRemoval = true)
    List<PriceQuotation> priceQuotations;

    private LocalDate createdDate;

}