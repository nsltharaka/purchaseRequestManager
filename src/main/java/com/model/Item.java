package com.model;

import com.util.PurchaseRequestStatus;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "purchase_request_id", nullable = false)
    private PurchaseRequest purchaseRequest;

    @ManyToOne()
    @JoinColumn(name = "quotationReport_id")
    private PriceQuotationsReport priceQuotationsReport;

    @Enumerated(EnumType.STRING)
    private PurchaseRequestStatus itemStatus;

    private String itemName;
    private String itemDescription;
    private int itemQuantity;
    private String QuantityUnit;
    private String itemCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public PurchaseRequestStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDesc) {
        this.itemDescription = itemDesc;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int qty) {
        this.itemQuantity = qty;
    }

    public String getQuantityUnit() {
        return QuantityUnit;
    }

    public void setQuantityUnit(String unit) {
        this.QuantityUnit = unit;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemStatus(PurchaseRequestStatus itemStatus) {
        this.itemStatus = itemStatus;
    }
}
