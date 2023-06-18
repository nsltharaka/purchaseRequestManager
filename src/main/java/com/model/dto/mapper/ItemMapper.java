package com.model.dto.mapper;

import com.model.Item;
import com.model.dto.ItemDTO;

public class ItemMapper {

    public static ItemDTO toDTO(Item item) {

        return new ItemDTO()
                .setItemId(item.getId().toString())
                .setItemCategory(item.getItemCategory())
                .setItemName(item.getItemName())
                .setItemDescription(item.getItemDescription())
                .setItemQuantity(item.getItemQuantity())
                .setQuantityUnit(item.getQuantityUnit())
                .setItemStatus(item.getItemStatus())
                .setPurchaseRequestId(item.getPurchaseRequestId());

    }

    public static Item toItem(ItemDTO dto) {

        var item = new Item()
                .setItemCategory(dto.itemCategory.get())
                .setItemName(dto.itemName.get())
                .setItemDescription(dto.itemDescription.get())
                .setItemQuantity(dto.itemQuantity.get())
                .setQuantityUnit(dto.quantityUnit.get())
                .setItemCategory(dto.itemCategory.get())
                .setItemStatus(dto.itemStatus.get())
                .setPurchaseRequestId(dto.purchaseRequestId.get());

        return item;

    }
}
