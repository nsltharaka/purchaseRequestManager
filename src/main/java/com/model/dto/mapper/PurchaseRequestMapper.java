package com.model.dto.mapper;

import com.model.PurchaseRequest;
import com.model.dto.PurchaseRequestDTO;

public class PurchaseRequestMapper {

    public static PurchaseRequestDTO toDTO(PurchaseRequest pr) {

        return new PurchaseRequestDTO()
                .setRequestId(pr.getRequestId())
                .setRequestDate(pr.getRequestDate())
                .setDueDate(pr.getDueDate())
                .setRequestedDepartment(pr.getRequestedDepartment())
                .setIsApproved(pr.isApproved())
                .setRequestStatus(pr.getRequestStatus());

    }

    public static PurchaseRequest fromDTO(PurchaseRequestDTO dto) {

        return new PurchaseRequest()
                .setRequestDate(dto.requestDate.get())
                .setDueDate(dto.dueDate.get())
                .setRequestedDepartment(dto.requestedDepartment.get())
                .setApproved(dto.isApproved.get())
                .setRequestStatus(dto.requestStatus.get());

    }

}
