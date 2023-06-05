package com.model.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ItemDTO {

    public SimpleObjectProperty<String> itemName;
    public SimpleObjectProperty<String> itemDescription;
    public SimpleIntegerProperty itemQuantity;
    public SimpleObjectProperty<String> quantityUnit;
    public PurchaseRequestDTO purchaseRequestDTO;

    public ItemDTO() {
        this.itemName = new SimpleObjectProperty<>();
        this.itemDescription = new SimpleObjectProperty<>();
        this.itemQuantity = new SimpleIntegerProperty(0);
        this.quantityUnit = new SimpleObjectProperty<>("pcs");
    }

    public ItemDTO setPurchaseRequestDTO(PurchaseRequestDTO purchaseRequest) {
        this.purchaseRequestDTO = purchaseRequest;
        return this;
    }

    public ItemDTO setItemName(String itemName) {
        this.itemName.set(itemName);
        return this;
    }

    public ItemDTO setItemDescription(String itemDescription) {
        this.itemDescription.set(itemDescription);
        return this;
    }

    public ItemDTO setItemQuantity(int itemQuantity) {
        this.itemQuantity.set(itemQuantity);
        return this;
    }

    public ItemDTO setQuantityUnit(String quantityUnit) {
        this.quantityUnit.set(quantityUnit);
        return this;
    }
}