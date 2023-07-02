package com.service.dao;

import java.sql.SQLException;

import com.model.dto.PurchaseOrderDTO;
import com.service.db.DBConnection;

public class PurchaseOrderDAO {

    public boolean insert(PurchaseOrderDTO dto) {

        final var query = "INSERT INTO purchase_order " +
                "(order_id, order_date, quotation_id, delivery, delivery_location, payment, additional_note) " +
                "(?,?,?,?,?,?,?);" +

                "UPDATE item SET item.purchase_order_id=? WHERE IN ()";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, dto.purchaseOrderId.get());
                stmt.setString(2, dto.purchaseOrderDate.get());
                stmt.setString(3, dto.priceQuotationDTO.priceQuotationId.get());
                stmt.setString(4, dto.termsAndConditions.get(0).get());
                stmt.setString(5, dto.termsAndConditions.get(1).get());
                stmt.setString(6, dto.termsAndConditions.get(2).get());
                stmt.setString(7, dto.termsAndConditions.get(3).get());

                // return stmt.executeUpdate() > 0;
                System.out.println(dto.itemIds.get());
                System.out.println(stmt.toString());
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        });

    }

}
