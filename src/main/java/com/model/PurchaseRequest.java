package com.model;

import java.time.LocalDate;
import java.util.List;

import com.util.Department;
import com.util.PurchaseRequestStatus;

public class PurchaseRequest {

    private String requestId;
    private LocalDate requestDate;
    private LocalDate dueDate;
    private Department requestedDepartment;
    private boolean isApproved;
    private PurchaseRequestStatus requestStatus;
    private List<Item> items;

    public String getRequestId() {
        return requestId;
    }

    public List<Item> getItems() {
        return items;
    }

    public Department getRequestedDepartment() {
        return requestedDepartment;
    }

    public PurchaseRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public PurchaseRequest setApproved(boolean isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public PurchaseRequest setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public PurchaseRequest setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
        return this;
    }

    public PurchaseRequest setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public PurchaseRequest setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public PurchaseRequest setRequestedDepartment(Department depString) {
        this.requestedDepartment = depString;
        return this;
    }

    public PurchaseRequest setRequestStatus(PurchaseRequestStatus status) {
        this.requestStatus = status;
        return this;
    }
}
