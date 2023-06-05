package com.service.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.model.PurchaseRequest;
import com.service.db.Database;

public class PurchaseRequestDAO {

    public Optional<PurchaseRequest> getPurchaseRequest(String id) {

        Optional<PurchaseRequest> result = Optional.empty();

        try (Session session = Database.getSessionFactory().openSession()) {

            var pr = session.find(PurchaseRequest.class, id);
            result = Optional.of(pr);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error finding PR");
        }

        return result;

    }

    public Optional<List<PurchaseRequest>> getAllPurchaseRequests() {

        try (Session session = Database.getSessionFactory().openSession()) {

            var result = session.createQuery("from PurchaseRequest", PurchaseRequest.class).list();
            return Optional.of(result);

        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public boolean insert(Consumer<PurchaseRequest> block) {

        var pr = new PurchaseRequest();
        block.accept(pr);

        Transaction transaction = null;
        try (Session session = Database.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(pr);

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error inserting PR");
        }

        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

}
