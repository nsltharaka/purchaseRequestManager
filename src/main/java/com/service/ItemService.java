package com.service;

import java.util.List;
import java.util.stream.Collectors;

import com.model.dto.ItemDTO;
import com.model.dto.mapper.ItemMapper;
import com.service.dao.ItemDAO;

public class ItemService {

    private ItemDAO itemDAO = new ItemDAO();

    public List<ItemDTO> selectAllItems() {

        var items = itemDAO.getAllItems();

        if (items.isEmpty())
            return List.of();

        return items.stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());

    }

}
