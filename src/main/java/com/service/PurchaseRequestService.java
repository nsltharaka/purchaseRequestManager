package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.model.dto.PurchaseRequestDTO;
import com.model.dto.UserDTO;
import com.model.dto.mapper.ItemMapper;
import com.model.dto.mapper.PurchaseRequestMapper;
import com.model.idGenerators.RequestIdGenerator;
import com.service.dao.ItemDAO;
import com.service.dao.PurchaseRequestDAO;

public class PurchaseRequestService {

    private PurchaseRequestDAO purchaseRequestDAO;
    private ItemDAO itemDAO;
    private UserDTO userDTO;

    public PurchaseRequestService(UserDTO user) {
        this.purchaseRequestDAO = new PurchaseRequestDAO();
        this.itemDAO = new ItemDAO();
        this.userDTO = user;
    }

    public boolean insertPurchaseRequest(PurchaseRequestDTO dto) {

        String prId = RequestIdGenerator.generate();

        var itemList = dto.itemDTOs.stream()
                .map(ItemMapper::toItem)
                .peek(item -> item.setPurchaseRequestId(prId))
                .collect(Collectors.toList());

        return purchaseRequestDAO.insert(pr -> {

            pr.setRequestId(prId);
            pr.setRequestDate(dto.requestDate.get());
            pr.setDueDate(dto.dueDate.get());
            pr.setRequestedDepartment(dto.requestedDepartment.get());
            pr.setRequestStatus(dto.requestStatus.get());

        });

    }

    public Optional<PurchaseRequestDTO> getPurchaseRequest(String id) {
        var result = purchaseRequestDAO.getPurchaseRequest(id);

        return result.map(PurchaseRequestMapper::toDTO)
                .or(Optional::empty);
    }

    public Optional<List<PurchaseRequestDTO>> getAllPurchaseRequests() {

        var resultSet = purchaseRequestDAO.getAllPurchaseRequests();

        return resultSet.map(list -> list.stream()
                .map(PurchaseRequestMapper::toDTO)
                .collect(Collectors.toList())

        ).or(Optional::empty);

    }

}
