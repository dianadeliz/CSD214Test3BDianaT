package com.trios.dianatpizzaapp_test3b;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
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
        }
    }

    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (customer_id, pizza_size, number_of_toppings, total_bill) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getPizzaSize());
            stmt.setInt(3, order.getNumberOfToppings());
            stmt.setDouble(4, order.getTotalBill());
            stmt.executeUpdate();
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setPizzaSize(rs.getString("pizza_size"));
                order.setNumberOfToppings(rs.getInt("number_of_toppings"));
                order.setTotalBill(rs.getDouble("total_bill"));
                orders.add(order);
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE Orders SET pizza_size = ?, number_of_toppings = ?, total_bill = ? WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getPizzaSize());
            stmt.setInt(2, order.getNumberOfToppings());
            stmt.setDouble(3, order.getTotalBill());
            stmt.setInt(4, order.getOrderId());
            stmt.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        }
    }
}
