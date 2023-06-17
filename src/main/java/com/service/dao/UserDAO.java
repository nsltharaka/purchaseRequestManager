package com.service.dao;

import java.sql.SQLException;
import java.util.Optional;

import com.model.User;
import com.service.db.DBConnection;
import com.util.UserRole;

public class UserDAO {

    public Optional<User> getConnectedUser(String username, String password) {

        return DBConnection.executeQueryWithResults(con -> {
            try {
                var statement = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
                statement.setString(1, username);
                statement.setString(2, password);

                var rs = statement.executeQuery();

                if (!rs.next())
                    return Optional.empty();

                var user = new User();
                user.setUsername(rs.getString("username"));
                user.setUserRole(UserRole.valueOf(rs.getString("user_role")));

                return Optional.of(user);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return Optional.empty();
        });

    }
}