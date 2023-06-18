package com.model.idGenerators;

import com.service.db.DBConnection;

public class ItemIdGenerator {

    public static String generate() {

        return DBConnection.executeQueryWithResults(con -> {

            try {
                var statement = con.prepareStatement("SELECT COUNT(*) FROM item");
                var rs = statement.executeQuery();

                if (!rs.next())
                    return "";

                long lastId = rs.getLong(1);
                return String.format("IT%03d", lastId + 1);

            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }

        });

    }

}
