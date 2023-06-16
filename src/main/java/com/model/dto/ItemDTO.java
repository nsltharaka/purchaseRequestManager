package com.model.dto;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemDTO {

    public SimpleObjectProperty<String> itemName;
    public SimpleObjectProperty<String> itemDescription;
    public SimpleIntegerProperty itemQuantity;
    public SimpleObjectProperty<String> quantityUnit;
    public SimpleStringProperty purchaseRequestId;

    public ItemDTO() {
        this.itemName = new SimpleObjectProperty<>();
        this.itemDescription = new SimpleObjectProperty<>();
        this.itemQuantity = new SimpleIntegerProperty(0);
        this.quantityUnit = new SimpleObjectProperty<>("pcs");
        this.purchaseRequestId = new SimpleStringProperty();
    }

    public ItemDTO setPurchaseRequestId(String id) {
        this.purchaseRequestId.set(id);
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