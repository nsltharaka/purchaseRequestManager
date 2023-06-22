package com.model.idGenerators;

import com.service.db.DBConnection;

public class QuotationReportIdGenerator {

    public static String generate() {
        String query = "SELECT COUNT(*) FROM price_quotation_report";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {
                var rs = statement.executeQuery();

                if (!rs.next())
                    return null;

                long lastId = rs.getLong(1);

                rs.close();
                return String.format("PQR%03d", lastId + 1);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        });
    }

}
