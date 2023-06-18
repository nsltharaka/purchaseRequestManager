package com.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.model.Item;
import com.model.dto.ItemDTO;
import com.model.dto.mapper.ItemMapper;
import com.service.dao.ItemDAO;

import javafx.beans.property.SimpleListProperty;

public class ItemService {

    private ItemDAO itemDAO = new ItemDAO();

    public List<ItemDTO> selectAllItems() {

        return null;

    }

    public boolean insertItems(SimpleListProperty<ItemDTO> dtos) {

        List<Item> items = dtos.get().stream()
                .map(ItemMapper::toItem)
                .peek(i -> i.setId(UUID.randomUUID()))
                .toList();

        return itemDAO.insertAll(items);

    }

    public Optional<List<ItemDTO>> getItemsFor(String id) {

        var results = itemDAO.selectAllWhere(id);

        List<ItemDTO> itemDTOs = results.stream()
                .map(ItemMapper::toDTO)
                .toList();

        return results.isEmpty() ? Optional.empty() : Optional.of(itemDTOs);

    }

}