package com.service.dao;

import java.util.Optional;

import org.hibernate.Session;

import com.model.User;
import com.service.db.Database;

import jakarta.persistence.TypedQuery;

public class UserDAO {

    public Optional<User> getConnectedUser(String username, String password) {

        User user = null;

        try (Session session = Database.getSessionFactory().openSession()) {

            TypedQuery<User> query = session.createQuery(
                    "SELECT u FROM User u WHERE username= :username AND password= :password",
                    User.class);

            query.setParameter("username", username);
            query.setParameter("password", password);

            user = query.getSingleResult();
            return Optional.of(user);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}