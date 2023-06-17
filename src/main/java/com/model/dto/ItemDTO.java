package com.model.dto;

import com.util.PurchaseRequestStatus;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemDTO {

    public SimpleObjectProperty<String> itemName;
    public SimpleObjectProperty<String> itemDescription;
    public SimpleIntegerProperty itemQuantity;
    public SimpleObjectProperty<String> quantityUnit;
    public SimpleStringProperty itemCategory;
    public SimpleObjectProperty<PurchaseRequestStatus> itemStatus;
    public SimpleStringProperty purchaseRequestId;
    public SimpleStringProperty itemId;

    public ItemDTO() {
        this.itemId = new SimpleStringProperty();
        this.itemName = new SimpleObjectProperty<>();
        this.itemDescription = new SimpleObjectProperty<>();
        this.itemQuantity = new SimpleIntegerProperty(0);
        this.quantityUnit = new SimpleObjectProperty<>("pcs");
        this.itemCategory = new SimpleStringProperty();
        this.itemStatus = new SimpleObjectProperty<>(PurchaseRequestStatus.PENDING_APPROVAL);
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

    public ItemDTO setItemCategory(String itemCategory) {
        this.itemCategory.set(itemCategory);
        return this;
    }

    public ItemDTO setItemStatus(PurchaseRequestStatus itemStatus) {
        this.itemStatus.set(itemStatus);
        return this;
    }

    public ItemDTO setItemId(String itemId) {
        this.itemId.set(itemId);
        return this;
    }
}