package com.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBConnection {

    private static final String DATABASE_URI = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void testConnection() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URI, USERNAME, PASSWORD)) {

            System.out.println("Database connected");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error connecting Database");
        }
    }

    public static void executeQuery(Consumer<Connection> block) {

        try (Connection connection = DriverManager.getConnection(DATABASE_URI, USERNAME, PASSWORD)) {

            block.accept(connection);

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error executing query");
        }

    }

    public static <T> T executeQueryWithResults(Function<Connection, T> function) {

        try (Connection connection = DriverManager.getConnection(DATABASE_URI, USERNAME, PASSWORD)) {

            return function.apply(connection);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error executing query with results");
        }

        return null;
    }
}
