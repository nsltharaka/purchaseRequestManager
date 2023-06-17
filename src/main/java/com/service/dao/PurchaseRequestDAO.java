package com.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import com.model.PurchaseRequest;
import com.service.db.DBConnection;

public class PurchaseRequestDAO {

    public Optional<PurchaseRequest> getPurchaseRequest(String id) {

        return null;

    }

    public Optional<List<PurchaseRequest>> getAllPurchaseRequests() {

        return null;

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

                var itemStatement = con.prepareStatement(
                        String.join(
                                " ",
                                "INSERT INTO item",
                                "(item_id, )"));

                return prStatement.executeUpdate() > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        });

    }
}
