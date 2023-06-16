package com.service.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.model.Item;
import com.service.db.Database;

public class ItemDAO {

    public List<Item> getAllItems() {

        try (Session session = Database.getSessionFactory().openSession()) {

            Query<Item> query = session.createQuery("from Item", Item.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error getting all items from the database");
            return List.of();
        }
    }
}
