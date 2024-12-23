package ru.kpfu.itis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCollectionUtil {

    private static Connection connection;

    private DatabaseCollectionUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    "root",
                    "poiasdmnb"
            );

            if (connection != null) {
                System.out.println("Connected to the database successfully!");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            new DatabaseCollectionUtil();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing the database connection", e);
        }
    }
}