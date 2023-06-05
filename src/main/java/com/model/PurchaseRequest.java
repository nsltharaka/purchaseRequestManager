package com.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.util.Department;
import com.util.PurchaseRequestStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class PurchaseRequest {

    @Id
    @GeneratedValue(generator = "rq-generator")
    @GenericGenerator(name = "rq-generator", strategy = "com.util.idGenerators.RequestIdGenerator")
    private String requestId;

    @OneToMany(mappedBy = "purchaseRequest", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Item> items;

    @Enumerated(EnumType.STRING)
    private Department requestedDepartment;

    @Enumerated(EnumType.STRING)
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
