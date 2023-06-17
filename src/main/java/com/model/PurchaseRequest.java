package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.util.Department;
import com.util.PurchaseRequestStatus;

public class PurchaseRequest {

    private String requestId;
    private List<Item> items = new ArrayList<>();
    private Department requestedDepartment;
    private PurchaseRequestStatus requestStatus;
    private LocalDate requestDate;
    private LocalDate dueDate;

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

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setRequestedDepartment(Department depString) {
        this.requestedDepartment = depString;
    }

    public void setRequestStatus(PurchaseRequestStatus status) {
        this.requestStatus = status;
    }
}
