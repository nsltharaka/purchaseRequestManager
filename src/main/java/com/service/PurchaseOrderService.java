package com.service;

import java.util.List;

import com.model.dto.PurchaseOrderDTO;
import com.model.idGenerators.PurchaseOrderIdGenerator;
import com.service.dao.PurchaseOrderDAO;

public class PurchaseOrderService {

    private PurchaseOrderDAO dao = new PurchaseOrderDAO();

    public List<PurchaseOrderDTO> selectAll() {
        return dao.selectAll();
    }

    public boolean insertPurchaseOrder(PurchaseOrderDTO po) {

        po.setPurchaseOrderId(PurchaseOrderIdGenerator.generate());
        return dao.insert(po);

    }

}
