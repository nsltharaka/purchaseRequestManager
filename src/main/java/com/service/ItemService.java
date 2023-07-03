package com.service;

import java.util.List;
import java.util.Optional;

import com.model.Item;
import com.model.dto.ItemDTO;
import com.model.dto.mapper.ItemMapper;
import com.model.idGenerators.ItemIdGenerator;
import com.service.dao.ItemDAO;
import com.util.PurchaseRequestStatus;

import javafx.beans.property.SimpleListProperty;

public class ItemService {

    private ItemDAO itemDAO = new ItemDAO();

    public Optional<List<ItemDTO>> selectAllProcessingItems() {

        var results = itemDAO.selectAll();

        List<ItemDTO> allProcessing = results.stream()
                .filter(item -> item.getItemStatus() != PurchaseRequestStatus.PENDING_APPROVAL)
                .map(ItemMapper::toDTO)
                .toList();

        return allProcessing.isEmpty() ? Optional.empty() : Optional.of(allProcessing);

    }

    public Optional<List<ItemDTO>> selectAllItems() {

        var results = itemDAO.selectAll();

        List<ItemDTO> itemDTOs = results.stream()
                .map(ItemMapper::toDTO)
                .toList();

        return results.isEmpty() ? Optional.empty() : Optional.of(itemDTOs);

    }

    public boolean insertItems(SimpleListProperty<ItemDTO> itemDTOs) {

        List<Item> items = itemDTOs.get().stream()
                .map(ItemMapper::toItem)
                .peek(i -> i.setId(ItemIdGenerator.generate()))
                .toList();

        return itemDAO.insertAll(items);

    }

    public Optional<List<ItemDTO>> getItemsOf(String of, String id) {

        var results = itemDAO.selectAllWhere(of, id);

        List<ItemDTO> itemDTOs = results.stream()
                .map(ItemMapper::toDTO)
                .toList();

        return results.isEmpty() ? Optional.empty() : Optional.of(itemDTOs);

    }

    public void addGRN(String itemId, String grn) {

        itemDAO.updateColumnWhere("grn", grn, itemId);
        itemDAO.updateColumnWhere("item_status", "DELIVERED", itemId);

    }

}