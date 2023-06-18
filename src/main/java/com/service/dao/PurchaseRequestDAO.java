package com.service.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.model.PurchaseRequest;
import com.service.db.DBConnection;
import com.util.Department;
import com.util.PurchaseRequestStatus;

public class PurchaseRequestDAO {

    public PurchaseRequest getPurchaseRequest(String id) {

        String query = "SELECT * FROM purchase_request WHERE request_id=?";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {

                statement.setString(1, id);
                var rs = statement.executeQuery();

                if (!rs.next())
                    return null;

                return resultSetToPurchaseRequest(rs);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error getting purchase request");
                return null;
            }
        });

    }

    public List<PurchaseRequest> getAllPurchaseRequests() {

        String query = "SELECT * FROM purchase_request";

        return DBConnection.executeQueryWithResults(con -> {

            try (var statement = con.prepareStatement(query)) {

                var rs = statement.executeQuery();

                List<PurchaseRequest> prList = new ArrayList<>();
                while (rs.next()) {
                    var pr = resultSetToPurchaseRequest(rs);
                    prList.add(pr);
                }

                rs.close();
                return prList;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error getting all purchase requests");
            }
            return List.of();

        });

    }

    public boolean insert(Consumer<PurchaseRequest> block) {

        var pr = new PurchaseRequest();
        block.accept(pr);

        String query = "INSERT INTO purchase_request " +
                "(request_id, requested_date, due_date, requested_department, request_status) " +
                "VALUES (?,?,?,?,?)";

        return DBConnection.executeQueryWithResults(con -> {

            try (var prStatement = con.prepareStatement(query)) {

                prStatement.setString(1, pr.getRequestId());
                prStatement.setString(2, pr.getRequestDate().toString());
                prStatement.setString(3, pr.getDueDate().toString());
                prStatement.setString(4, pr.getRequestedDepartment().toString());
                prStatement.setString(5, pr.getRequestStatus().toString());

                return prStatement.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }

    private PurchaseRequest resultSetToPurchaseRequest(ResultSet rs) throws SQLException {
        return new PurchaseRequest()
                .setRequestId(rs.getString("request_id"))
                .setRequestDate(rs.getDate("requested_date").toLocalDate())
                .setDueDate(rs.getDate("due_date").toLocalDate())
                .setRequestedDepartment(Department.valueOf(rs.getString("requested_department")))
                .setRequestStatus(PurchaseRequestStatus.valueOf(rs.getString("request_status")));
    }
}
