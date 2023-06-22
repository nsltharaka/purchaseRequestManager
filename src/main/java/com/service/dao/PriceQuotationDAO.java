package com.service.dao;

import com.model.PriceQuotation;
import com.service.db.DBConnection;

public class PriceQuotationDAO {

    public boolean insert(PriceQuotation priceQuotation) {

        final var query = "INSERT INTO price_quotation "
                + "(quotation_id, supplier_name, supplier_address, price_quotation_report_id) "
                + "VALUES (?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, priceQuotation.getQuotationId());
                stmt.setString(2, priceQuotation.getSupplierName());
                stmt.setString(3, priceQuotation.getSupplierAddress());
                stmt.setString(4, priceQuotation.getPriceQuotationsReportId());

                return stmt.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

}
