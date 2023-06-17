package com.model.idGenerators;

import com.service.db.DBConnection;

public class RequestIdGenerator {

    public static String generate() {

        return DBConnection.executeQueryWithResults(con -> {

            try {
                var statement = con.prepareStatement("SELECT COUNT(*) FROM purchase_request");
                var rs = statement.executeQuery();

                if (!rs.next())
                    return "";

                long lastId = rs.getLong(1);
                return String.format("PR%03d", lastId + 1);

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }

        });

    }
}
