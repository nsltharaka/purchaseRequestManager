package com.service;

import java.util.List;
import java.util.Optional;

import com.model.Item;
import com.model.dto.ItemDTO;
import com.model.dto.mapper.ItemMapper;
import com.model.idGenerators.ItemIdGenerator;
import com.service.dao.ItemDAO;

import javafx.beans.property.SimpleListProperty;

public class ItemService {

    private ItemDAO itemDAO = new ItemDAO();

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

    public Optional<List<ItemDTO>> getItemsFor(String id) {

        var results = itemDAO.selectAllWhere(id);

        List<ItemDTO> itemDTOs = results.stream()
                .map(ItemMapper::toDTO)
                .toList();

        return results.isEmpty() ? Optional.empty() : Optional.of(itemDTOs);

    }

}