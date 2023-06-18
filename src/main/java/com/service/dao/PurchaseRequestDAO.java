package com.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.model.PurchaseRequest;
import com.service.db.DBConnection;
import com.util.Department;
import com.util.PurchaseRequestStatus;

public class PurchaseRequestDAO {

    public PurchaseRequest getPurchaseRequest(String id) {

        return DBConnection.executeQueryWithResults(con -> {

            try {

                var statement = con.prepareStatement("SELECT * FROM purchase_request WHERE request_id=?");
                statement.setString(1, id);

                var rs = statement.executeQuery();

                if (!rs.next())
                    return null;

                return new PurchaseRequest()
                        .setRequestId(rs.getString("request_id"))
                        .setRequestDate(rs.getDate("requested_date").toLocalDate())
                        .setDueDate(rs.getDate("due_date").toLocalDate())
                        .setRequestedDepartment(Department.valueOf(rs.getString("requested_department")))
                        .setRequestStatus(PurchaseRequestStatus.valueOf(rs.getString("request_status")));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error getting purchase request");
            }
            return null;

        });

    }

    public List<PurchaseRequest> getAllPurchaseRequests() {

        return DBConnection.executeQueryWithResults(con -> {

            try {

                var statement = con.prepareStatement("SELECT * FROM purchase_request");
                var rs = statement.executeQuery();

                List<PurchaseRequest> prList = new ArrayList<>();
                while (rs.next()) {

                    var pr = new PurchaseRequest()
                            .setRequestId(rs.getString("request_id"))
                            .setRequestDate(rs.getDate("requested_date").toLocalDate())
                            .setDueDate(rs.getDate("due_date").toLocalDate())
                            .setRequestedDepartment(Department.valueOf(rs.getString("requested_department")))
                            .setRequestStatus(PurchaseRequestStatus.valueOf(rs.getString("request_status")));

                    prList.add(pr);
                }

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

        return DBConnection.executeQueryWithResults(con -> {

            try {
                var prStatement = con.prepareStatement(
                        "INSERT INTO purchase_request " +
                                "(request_id, requested_date, due_date, requested_department, request_status) " +
                                "VALUES (?,?,?,?,?)");

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
}
