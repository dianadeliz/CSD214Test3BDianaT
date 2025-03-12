package com.trios.dianatpizzaapp_test3b;

import java.sql.*;

public class CustomerDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pizzadiana";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "MySQLServer";

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (customer_name, mobile_number) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getMobileNumber());
            stmt.executeUpdate();

            // Get generated customer_id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Customer getCustomerByMobile(String mobileNumber) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE mobile_number = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mobileNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("customer_name"),
                            rs.getString("mobile_number")
                    );
                }
            }
        }
        return null; //customer not found
    }
}

