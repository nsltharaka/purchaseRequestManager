package com.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.util.Department;
import com.util.PurchaseRequestStatus;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class PurchaseRequestDTO {

    public SimpleStringProperty requestId;
    public SimpleObjectProperty<Department> requestedDepartment;
    public SimpleObjectProperty<LocalDate> requestDate;
    public SimpleObjectProperty<LocalDate> dueDate;
    public SimpleListProperty<ItemDTO> itemDTOs;
    public SimpleObjectProperty<PurchaseRequestStatus> requestStatus;

    public PurchaseRequestDTO() {
        this.requestId = new SimpleStringProperty();
        this.requestedDepartment = new SimpleObjectProperty<>();
        this.requestDate = new SimpleObjectProperty<>(LocalDate.now());
        this.dueDate = new SimpleObjectProperty<>();
        this.itemDTOs = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.requestStatus = new SimpleObjectProperty<>(PurchaseRequestStatus.PENDING_APPROVAL);
    }

    public PurchaseRequestDTO setRequestId(String requestId) {
        this.requestId.set(requestId);
        return this;
    }

    public PurchaseRequestDTO setRequestedDepartment(Department requestedDepartment) {
        this.requestedDepartment.set(requestedDepartment);
        return this;
    }

    public PurchaseRequestDTO setRequestDate(LocalDate requestDate) {
        this.requestDate.set(requestDate);
        return this;
    }

    public PurchaseRequestDTO setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
        return this;
    }

    public PurchaseRequestDTO setItemDTOs(List<ItemDTO> items) {
        this.itemDTOs.setAll(items);
        return this;
    }

    public PurchaseRequestDTO setRequestStatus(PurchaseRequestStatus requestStatus) {
        this.requestStatus.set(requestStatus);
        return this;
    }

    @Override
    public String toString() {
        return requestId.get();
    }
}
