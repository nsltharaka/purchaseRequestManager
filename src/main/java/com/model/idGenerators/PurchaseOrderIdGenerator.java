package com.model.idGenerators;

import com.service.db.DBConnection;

public class PurchaseOrderIdGenerator {

    public static String generate() {

        String query = "SELECT COUNT(*) FROM purchase_order";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {
                var rs = statement.executeQuery();

                if (!rs.next())
                    return null;

                long lastId = rs.getLong(1);

                rs.close();
                return String.format("PO%03d", lastId + 1);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        });

    }

}
