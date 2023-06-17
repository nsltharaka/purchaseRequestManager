package com.model;

import com.util.PurchaseRequestStatus;

public class Item {

    private String id;
    private String purchaseRequestId;
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

    public String getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(String purchaseRequest) {
        this.purchaseRequestId = purchaseRequest;
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
