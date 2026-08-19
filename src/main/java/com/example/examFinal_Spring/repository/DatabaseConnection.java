package com.example.examFinal_Spring.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection getConnection() {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/exam_poo_db",
                    "postgres",
                    "jacko");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error during database connection: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
