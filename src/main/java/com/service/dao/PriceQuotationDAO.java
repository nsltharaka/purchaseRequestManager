package com.service.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.model.PriceQuotation;
import com.service.db.DBConnection;

public class PriceQuotationDAO {

    public boolean insert(PriceQuotation priceQuotation) {

        final var query = "INSERT INTO price_quotation "
                + "(quotation_id, supplier_name, supplier_address, price_quotation_report_id, approved) "
                + "VALUES (?, ?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, priceQuotation.getQuotationId().toString());
                stmt.setString(2, priceQuotation.getSupplierName());
                stmt.setString(3, priceQuotation.getSupplierAddress());
                stmt.setString(4, priceQuotation.getPriceQuotationsReportId());
                stmt.setBoolean(5, priceQuotation.isApproved());

                return stmt.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

    public boolean insertAll(List<PriceQuotation> pqList) {

        final var query = "INSERT INTO price_quotation "
                + "(quotation_id, supplier_name, supplier_address, price_quotation_report_id, approved) "
                + "VALUES (?, ?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                for (var pq : pqList) {

                    stmt.setString(1, pq.getQuotationId().toString());
                    stmt.setString(2, pq.getSupplierName());
                    stmt.setString(3, pq.getSupplierAddress());
                    stmt.setString(4, pq.getPriceQuotationsReportId());
                    stmt.setBoolean(5, pq.isApproved());

                    stmt.addBatch();

                }

                return stmt.executeBatch().length > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error inserting all priceQuotations");
                return false;
            }

        }) && insertAllQuotedPrices(pqList);

    }

    private boolean insertAllQuotedPrices(List<PriceQuotation> pqList) {

        final var itemMapQuery = "INSERT INTO price_quotation__quoted_price "
                + "(quotation_id, price_quotation_report_id, item_id, quoted_price) "
                + "VALUES (?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(itemMapQuery)) {

                for (var pq : pqList) {

                    stmt.setString(1, pq.getQuotationId().toString());
                    stmt.setString(2, pq.getPriceQuotationsReportId());

                    Map<String, Double> item_quotedPrice = pq.getItem_quotedPrice();

                    for (var entry : item_quotedPrice.entrySet()) {
                        stmt.setString(3, entry.getKey());
                        stmt.setDouble(4, entry.getValue());
                        stmt.addBatch();
                    }

                }

                return stmt.executeBatch().length > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error inserting all priceQuotations-quotedPrice");
                return false;
            }

        });

    }

}
