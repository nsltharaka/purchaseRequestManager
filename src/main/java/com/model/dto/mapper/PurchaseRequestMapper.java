package com.model.dto.mapper;

import com.model.PurchaseRequest;
import com.model.dto.PurchaseRequestDTO;

public class PurchaseRequestMapper {

    public static PurchaseRequestDTO toDTO(PurchaseRequest pr) {

        // var itemDTOs =
        // pr.getItems().stream().map(ItemMapper::toDTO).collect(Collectors.toList());

        return new PurchaseRequestDTO()
                .setRequestId(pr.getRequestId())
                .setRequestDate(pr.getRequestDate())
                .setDueDate(pr.getDueDate())
                .setRequestedDepartment(pr.getRequestedDepartment())
                .setRequestStatus(pr.getRequestStatus());
        // .setItemDTOs(FXCollections.observableList(itemDTOs));

    }

    public static PurchaseRequest fromDTO(PurchaseRequestDTO dto) {

        var pr = new PurchaseRequest();
        pr.setRequestDate(dto.requestDate.get());
        pr.setDueDate(dto.dueDate.get());
        pr.setRequestedDepartment(dto.requestedDepartment.get());
        pr.setRequestStatus(dto.requestStatus.get());

        return pr;

    }

}
