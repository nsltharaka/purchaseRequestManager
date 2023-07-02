package com.service;

import com.model.dto.PurchaseOrderDTO;
import com.service.dao.PurchaseOrderDAO;

public class PurchaseOrderService {

    private PurchaseOrderDAO dao = new PurchaseOrderDAO();

    public boolean insertPurchaseOrder(PurchaseOrderDTO po) {

        return dao.insert(po);

    }

}
