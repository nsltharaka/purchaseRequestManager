package com.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.model.Item;
import com.service.db.DBConnection;
import com.util.PurchaseRequestStatus;

public class ItemDAO {

    public boolean insertAll(List<Item> items) {

        final String query = "INSERT INTO item "
                + "(item_id, item_category, item_name, item_description, item_quantity,quantity_unit, item_status, purchase_request_id) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {

                for (Item item : items) {
                    statement.setString(1, item.getId().toString());
                    statement.setString(2, item.getItemCategory());
                    statement.setString(3, item.getItemName());
                    statement.setString(4, item.getItemDescription());
                    statement.setInt(5, item.getItemQuantity());
                    statement.setString(6, item.getQuantityUnit());
                    statement.setString(7, item.getItemStatus().toString());
                    statement.setString(8, item.getPurchaseRequestId());

                    statement.addBatch();
                }

                return statement.executeBatch().length > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error inserting all items");
                return false;
            }

        });
    }

    public boolean updateColumnWhere(String key, String value, String... ids) {

        final String idParams = Arrays.stream(ids)
                .map(str -> "'" + str + "'")
                .collect(Collectors.joining(", ", "(", ")"));

        final String query = "UPDATE item "
                + "SET `" + key + "`=? WHERE item_id IN " + idParams;

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                // stmt.setString(1, key);
                stmt.setString(1, value);

                return stmt.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

    public List<Item> selectAll() {

        String query = "SELECT * FROM item";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                var rs = stmt.executeQuery();

                List<Item> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(resultSetToItem(rs));
                }

                rs.close();
                return items;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error getting all items");
                return List.of();
            }

        });

    }

    public List<Item> selectAllWhere(String where, String id) {

        String query = "SELECT * FROM item WHERE " + where + "_id=?";

        return DBConnection.executeQueryWithResults(con -> {

            try (var stmt = con.prepareStatement(query)) {

                stmt.setString(1, id);
                var rs = stmt.executeQuery();

                List<Item> items = new ArrayList<>();
                while (rs.next()) {
                    items.add(resultSetToItem(rs));
                }

                rs.close();
                return items;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error selecting all items where id=" + id);
            }

            return List.of();
        });

    }

    private Item resultSetToItem(ResultSet rs) throws SQLException {

        return new Item()
                .setId(UUID.fromString(rs.getString("item_id")))
                .setItemCategory(rs.getString("item_category"))
                .setItemName(rs.getString("item_name"))
                .setItemDescription(rs.getString("item_description"))
                .setItemQuantity(rs.getInt("item_quantity"))
                .setQuantityUnit(rs.getString("quantity_unit"))
                .setItemStatus(PurchaseRequestStatus.valueOf(rs.getString("item_status")))
                .setPurchaseRequestId(rs.getString("purchase_request_id"));

    }

    public void addGRN(String grn) {

        final var query = "";

    }
}
