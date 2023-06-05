package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.model.dto.PurchaseRequestDTO;
import com.model.dto.UserDTO;
import com.model.dto.mapper.ItemMapper;
import com.model.dto.mapper.PurchaseRequestMapper;
import com.service.dao.PurchaseRequestDAO;

public class PurchaseRequestService {

    private PurchaseRequestDAO dao;
    private UserDTO userDTO;

    public PurchaseRequestService(UserDTO user) {
        this.dao = new PurchaseRequestDAO();
        this.userDTO = user;
    }

    public boolean insertPurchaseRequest(PurchaseRequestDTO dto) {

        // if (userDTO.userRole.get() != UserRole.PURCHASER) {
        // throw new UnsupportedOperationException("User does not have permission to
        // perform this action");
        // }

        return dao.insert(pr -> {
            pr.setRequestDate(dto.requestDate.get());
            pr.setDueDate(dto.dueDate.get());
            pr.setRequestedDepartment(dto.requestedDepartment.get());
            pr.setRequestStatus(dto.requestStatus.get());

            var itemList = dto.itemDTOs.stream()
                    .map(ItemMapper::toItem)
                    .peek(item -> item.setPurchaseRequest(pr))
                    .collect(Collectors.toList());

            pr.setItems(itemList);

        });

    }

    public Optional<PurchaseRequestDTO> getPurchaseRequest(String id) {
        var result = dao.getPurchaseRequest(id);

        return result.map(PurchaseRequestMapper::toDTO)
                .or(Optional::empty);
    }

    public Optional<List<PurchaseRequestDTO>> getAllPurchaseRequests() {

        var resultSet = dao.getAllPurchaseRequests();

        return resultSet.map(list -> list.stream()
                .map(PurchaseRequestMapper::toDTO)
                .collect(Collectors.toList())

        ).or(Optional::empty);

    }

}
