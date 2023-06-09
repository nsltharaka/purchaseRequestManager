package com.service;

import java.util.List;
import java.util.Optional;

import com.model.dto.PurchaseRequestDTO;
import com.model.dto.UserDTO;
import com.model.dto.mapper.PurchaseRequestMapper;
import com.model.idGenerators.RequestIdGenerator;
import com.service.dao.ItemDAO;
import com.service.dao.PurchaseRequestDAO;
import com.util.PurchaseRequestStatus;

public class PurchaseRequestService {

    private PurchaseRequestDAO purchaseRequestDAO;
    private UserDTO userDTO;

    public PurchaseRequestService(UserDTO user) {
        this.purchaseRequestDAO = new PurchaseRequestDAO();
        this.userDTO = user;
    }

    public boolean insertPurchaseRequest(PurchaseRequestDTO dto) {

        String prId = RequestIdGenerator.generate();

        dto.itemDTOs.get()
                .forEach(item -> item.setPurchaseRequestId(prId));

        return purchaseRequestDAO.insert(pr -> {

            pr.setRequestId(prId);
            pr.setRequestDate(dto.requestDate.get());
            pr.setDueDate(dto.dueDate.get());
            pr.setRequestedDepartment(dto.requestedDepartment.get());
            pr.setApproved(dto.isApproved.get());
            pr.setRequestStatus(dto.requestStatus.get());

        }) && new ItemService().insertItems(dto.itemDTOs);

    }

    public Optional<PurchaseRequestDTO> getPurchaseRequest(String id) {
        var result = purchaseRequestDAO.getPurchaseRequest(id);

        if (result == null)
            return Optional.empty();

        var dto = PurchaseRequestMapper.toDTO(result);
        return Optional.of(dto);

    }

    public Optional<List<PurchaseRequestDTO>> getAllPurchaseRequests() {

        var resultSet = purchaseRequestDAO.getAllPurchaseRequests();

        if (resultSet.isEmpty())
            return Optional.empty();

        var purchaseRequestDTOs = resultSet.stream()
                .map(PurchaseRequestMapper::toDTO)
                .toList();

        return Optional.of(purchaseRequestDTOs);

    }

    public boolean updatePurchaseRequestApproved(PurchaseRequestDTO requestDTO) {

        String[] itemIds = requestDTO.itemDTOs.stream()
                .map(dto -> dto.itemId.get())
                .toArray(length -> new String[length]);

        return purchaseRequestDAO
                .updateApprovedStatus(pr -> pr.setRequestId(requestDTO.requestId.get())
                        .setApproved(true)
                        .setRequestStatus(PurchaseRequestStatus.PROCESSING))

                &&

                new ItemDAO().updateColumnWhere("item_status", "PROCESSING", itemIds);

    }

}
