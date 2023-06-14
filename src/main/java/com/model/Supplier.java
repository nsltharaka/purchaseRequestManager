package com.model;

import jakarta.persistence.*;

@Entity
public class Supplier {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    private double QuotedPrice;

}