package com.model.dto.mapper;

import com.model.Item;
import com.model.dto.ItemDTO;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {

        return new ItemDTO()
                .setItemName(item.getItemName())
                .setItemDescription(item.getItemDescription())
                .setItemQuantity(item.getItemQuantity())
                .setQuantityUnit(item.getQuantityUnit());

    }

    public static Item toItem(ItemDTO dto) {

        var item = new Item();
        item.setItemName(dto.itemName.get());
        item.setItemDescription(dto.itemDescription.get());
        item.setItemQuantity(dto.itemQuantity.get());
        item.setQuantityUnit(dto.quantityUnit.get());

        return item;

    }
}
