package com.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.model.PriceQuotation;
import com.service.db.DBConnection;

public class PriceQuotationDAO {

    public boolean insert(PriceQuotation priceQuotation) {

        final var query = "INSERT INTO price_quotation "
                + "(quotation_id, supplier_name, supplier_address, price_quotation_report_id, approved, quoted_total) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, priceQuotation.getQuotationId().toString());
                stmt.setString(2, priceQuotation.getSupplierName());
                stmt.setString(3, priceQuotation.getSupplierAddress());
                stmt.setString(4, priceQuotation.getPriceQuotationsReportId());
                stmt.setBoolean(5, priceQuotation.isApproved());
                stmt.setDouble(6, priceQuotation.getQuotedTotal());

                return stmt.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

    public boolean insertAll(List<PriceQuotation> pqList) {

        final var query = "INSERT INTO price_quotation "
                + "(quotation_id, supplier_name, supplier_address, price_quotation_report_id, approved, quoted_total) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                for (var pq : pqList) {

                    stmt.setString(1, pq.getQuotationId().toString());
                    stmt.setString(2, pq.getSupplierName());
                    stmt.setString(3, pq.getSupplierAddress());
                    stmt.setString(4, pq.getPriceQuotationsReportId());
                    stmt.setBoolean(5, pq.isApproved());
                    stmt.setDouble(6, pq.getQuotedTotal());

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

    public List<PriceQuotation> selectPriceQuotations(String pqrId) {

        List<PriceQuotation> pqList = new ArrayList<>();

        final var query = "SELECT * FROM price_quotation "
                + "WHERE price_quotation_report_id=?";

        DBConnection.executeQuery(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, pqrId);

                var rs = stmt.executeQuery();

                while (rs.next()) {
                    var pq = resultSetToPriceQuotation(rs);
                    pqList.add(pq);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        for (var pq : pqList) {
            pq.setItem_quotedPrice(selectAllQuotedPrices(pqrId, pq.getQuotationId().toString()));
        }

        return pqList;

    }

    private Map<String, Double> selectAllQuotedPrices(String priceQuotationId, String quotationId) {

        final var query = "SELECT item_id, quoted_price FROM price_quotation__quoted_price "
                + "WHERE price_quotation_report_id=? AND quotation_id=?";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, priceQuotationId);
                stmt.setString(2, quotationId);

                var rs = stmt.executeQuery();

                Map<String, Double> map = new HashMap<>();
                while (rs.next()) {
                    map.put(rs.getString("item_id"), rs.getDouble("quoted_price"));
                }

                return map;

            } catch (SQLException e) {
                e.printStackTrace();
                return Map.of();
            }

        });

    }

    private PriceQuotation resultSetToPriceQuotation(ResultSet rs) throws SQLException {
        return new PriceQuotation()
                .setQuotationId(UUID.fromString(rs.getString("quotation_id")))
                .setApproved(rs.getBoolean("approved"))
                .setSupplierName(rs.getString("supplier_name"))
                .setSupplierAddress(rs.getString("supplier_address"))
                .setQuotedTotal(rs.getDouble("quoted_total"))
                .setPriceQuotationsReport(rs.getString("price_quotation_report_id"));
    }

}
