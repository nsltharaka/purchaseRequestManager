package com.model.dto.mapper;

import com.model.Item;
import com.model.dto.ItemDTO;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {

        return new ItemDTO()
                .setItemName(item.getItemName())
                .setItemDescription(item.getItemDescription())
                .setItemQuantity(item.getItemQuantity())
                .setQuantityUnit(item.getQuantityUnit())
                .setItemCategory(item.getItemCategory())
                .setItemStatus(item.getItemStatus())
                .setPurchaseRequestId(item.getPurchaseRequest().getRequestId());

    }

    public static Item toItem(ItemDTO dto) {

        var item = new Item();
        item.setId(dto.itemId.get());
        item.setItemName(dto.itemName.get());
        item.setItemDescription(dto.itemDescription.get());
        item.setItemQuantity(dto.itemQuantity.get());
        item.setQuantityUnit(dto.quantityUnit.get());
        item.setItemCategory(dto.itemCategory.get());
        item.setItemStatus(dto.itemStatus.get());

        return item;

    }
}
