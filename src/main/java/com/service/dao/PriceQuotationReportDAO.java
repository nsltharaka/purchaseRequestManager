package com.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.model.PriceQuotationsReport;
import com.service.db.DBConnection;
import com.util.PriceQuotationReportStatus;

public class PriceQuotationReportDAO {

    public List<PriceQuotationsReport> selectAll() {

        String query = "SELECT * FROM price_quotation_report";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {

                var rs = statement.executeQuery();

                List<PriceQuotationsReport> pqrList = new ArrayList<>();
                while (rs.next()) {
                    var pqr = resultSetToPriceQuotationReport(rs);
                    pqrList.add(pqr);
                }

                rs.close();
                return pqrList;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error getting all purchase requests");
            }
            return List.of();

        });

    }

    private PriceQuotationsReport resultSetToPriceQuotationReport(ResultSet rs) throws SQLException {
        return new PriceQuotationsReport()
                .setId(rs.getString("report_id"))
                .setCreatedDate(LocalDate.parse(rs.getString("created_date")))
                .setStatus(PriceQuotationReportStatus.valueOf(rs.getString("quotation_report_status")));
    }

    public boolean insert(Consumer<PriceQuotationsReport> block) {

        var report = new PriceQuotationsReport();
        block.accept(report);

        final var query = "INSERT INTO price_quotation_report "
                + "(report_id, created_date, quotation_report_status) "
                + "VALUES (?,?,?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {

                statement.setString(1, report.getId());
                statement.setString(2, report.getCreatedDate().toString());
                statement.setString(3, report.getStatus().toString());

                return statement.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

}
