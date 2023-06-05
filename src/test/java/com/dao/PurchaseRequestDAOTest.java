package com.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.model.Item;
import com.model.PurchaseRequest;
import com.model.dto.mapper.ItemMapper;
import com.model.dto.mapper.PurchaseRequestMapper;
import com.util.Department;

import javafx.collections.FXCollections;

public class PurchaseRequestDAOTest {
    @Test
    public void testGetAllPurchaseRequests() {

    }

    @Test
    public void testInsert() {

        var pr = new PurchaseRequest();
        pr.setRequestId("testPR");
        pr.setRequestDate(LocalDate.now());
        pr.setDueDate(LocalDate.now());
        pr.setRequestedDepartment(Department.PLANNING);

        var item = new Item();
        item.setItemName("cheese pizza");
        item.setItemDescription("without pine apple");
        item.setItemQuantity(10);
        item.setQuantityUnit("box");
        item.setPurchaseRequest(pr);

        var item2 = new Item();
        item2.setItemName("Pepsi");
        item2.setItemDescription("large bottle");
        item2.setItemQuantity(10);
        item2.setQuantityUnit("bottle");
        item2.setPurchaseRequest(pr);

        pr.setItems(FXCollections.observableList(List.of(
                item, item2)));

        var prDTO = PurchaseRequestMapper.toDTO(pr);
        var itemDTO = ItemMapper.toDTO(item);

        System.out.println(prDTO.itemDTOs.get().toString());
        System.out.println(itemDTO.purchaseRequestDTO.requestStatus);

    }
}
