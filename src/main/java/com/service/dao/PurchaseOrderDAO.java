package com.service.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.model.dto.PurchaseOrderDTO;
import com.service.ItemService;
import com.service.PriceQuotationService;
import com.service.db.DBConnection;

public class PurchaseOrderDAO {

    public List<PurchaseOrderDTO> selectAll() {

        final var query = "SELECT * FROM purchase_order";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                var rs = stmt.executeQuery();

                List<PurchaseOrderDTO> list = new ArrayList<>();
                while (rs.next()) {

                    list.add(new PurchaseOrderDTO()
                            .setPurchaseOrderId(rs.getString("order_id"))
                            .setPurchaseOrderDate(LocalDate.parse(rs.getString("order_date")))
                            .setPriceQuotationReportId(rs.getString("quotation_report_id")));

                }

                list.forEach(po -> {

                    var approvedPq = new PriceQuotationService()
                            .getApprovedPriceQuotation(po.priceQuotationReportId.get());

                    approvedPq.ifPresent(po::setPriceQuotationDTO);

                    var items = new ItemService().getItemsOf("price_quotation_report", po.priceQuotationReportId.get());
                    items.ifPresent(itemsList -> po.items.setAll(itemsList));

                });

                return list;

            } catch (SQLException e) {
                e.printStackTrace();
                return List.of();
            }

        });

    }

    public boolean insert(PurchaseOrderDTO dto) {

        final String[] idParams = dto.items.stream()
                .map(item -> item.itemId.get())
                .toArray(str -> new String[str]);

        final var query = "INSERT INTO purchase_order " +
                "(order_id, order_date, quotation_report_id, delivery, delivery_location, payment, additional_note) " +
                "VALUES (?,?,?,?,?,?,?)";

        ItemDAO itemDAO = new ItemDAO();
        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, dto.purchaseOrderId.get());
                stmt.setString(2, dto.purchaseOrderDate.get());
                stmt.setString(3, dto.priceQuotationDTO.priceQuotationReportId.get());
                stmt.setString(4, dto.termsAndConditions.get(0).get());
                stmt.setString(5, dto.termsAndConditions.get(1).get());
                stmt.setString(6, dto.termsAndConditions.get(2).get());
                stmt.setString(7, dto.termsAndConditions.get(3).get());

                return stmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }) && itemDAO.updateColumnWhere("purchase_order_id", dto.purchaseOrderId.get(), idParams)
                && itemDAO.updateColumnWhere("item_status", "AWAITING", idParams);

    }

}
