package com.model;

import java.util.UUID;

import com.util.PurchaseRequestStatus;

public class Item {

    private UUID id;
    private String itemCategory;
    private String itemName;
    private String itemDescription;
    private int itemQuantity;
    private String QuantityUnit;
    private PurchaseRequestStatus itemStatus;
    private String purchaseRequestId;

    public UUID getId() {
        return id;
    }

    public Item setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public Item setPurchaseRequestId(String purchaseRequest) {
        this.purchaseRequestId = purchaseRequest;
        return this;
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

    public Item setItemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Item setItemDescription(String itemDesc) {
        this.itemDescription = itemDesc;
        return this;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public Item setItemQuantity(int qty) {
        this.itemQuantity = qty;
        return this;
    }

    public String getQuantityUnit() {
        return QuantityUnit;
    }

    public Item setQuantityUnit(String unit) {
        this.QuantityUnit = unit;
        return this;
    }

    public Item setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
        return this;
    }

    public Item setItemStatus(PurchaseRequestStatus itemStatus) {
        this.itemStatus = itemStatus;
        return this;
    }

    @Override
    public String toString() {
        return this.itemName;
    }
}
