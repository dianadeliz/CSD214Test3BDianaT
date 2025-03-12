package com.trios.dianatpizzaapp_test3b;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class PizzaOrderController {
    @FXML private TextField nameField;
    @FXML private TextField mobileField;
    @FXML private CheckBox xlBox;
    @FXML private CheckBox lBox;
    @FXML private CheckBox mBox;
    @FXML private CheckBox sBox;
    @FXML private TextField toppingsField;
    @FXML private Button createButton;
    @FXML private Button readButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private TableView<Order> tableView;
    @FXML private TableColumn<Order, Integer> orderIdColumn;
    @FXML private TableColumn<Order, String> pizzaSizeColumn;
    @FXML private TableColumn<Order, Integer> toppingsColumn;
    @FXML private TableColumn<Order, Double> totalBillColumn;

    private final OrderDAO orderDAO = new OrderDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @FXML
    public void initialize() {
        // Initialize table columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        pizzaSizeColumn.setCellValueFactory(new PropertyValueFactory<>("pizzaSize"));
        toppingsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfToppings"));
        totalBillColumn.setCellValueFactory(new PropertyValueFactory<>("totalBill"));

        //setting button actions
        createButton.setOnAction(e -> createOrder());
        readButton.setOnAction(e -> readOrders());
        updateButton.setOnAction(e -> updateOrder());
        deleteButton.setOnAction(e -> deleteOrder());

        //loading existing orders
        readOrders();
    }

    private void createOrder() {
        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String size = getPizzaSize();

        if (name.isEmpty() || mobile.isEmpty() || size.isEmpty() || toppingsField.getText().trim().isEmpty()) {
            showAlert("Error", "All fields must be filled.");
            return;
        }

        int toppings;
        try {
            toppings = Integer.parseInt(toppingsField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Number of toppings must be a valid number.");
            return;
        }

        double price = calculateTotalBill(size, toppings);

        try {
            //checking if customer exists, otherwise create a new one
            Customer customer = customerDAO.getCustomerByMobile(mobile);
            if (customer == null) {
                customer = new Customer(name, mobile);
                customerDAO.addCustomer(customer);
            }

            //creating and saving order
            Order order = new Order(customer.getCustomerId(), size, toppings, price);
            orderDAO.addOrder(order);
            readOrders(); // Refresh table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readOrders() {
        try {
            List<Order> orders = orderDAO.getAllOrders();
            tableView.getItems().setAll(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateOrder() {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Error", "Select an order to update.");
            return;
        }

        String size = getPizzaSize();
        int toppings;
        try {
            toppings = Integer.parseInt(toppingsField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Number of toppings must be a valid number.");
            return;
        }

        selectedOrder.setPizzaSize(size);
        selectedOrder.setNumberOfToppings(toppings);
        selectedOrder.setTotalBill(calculateTotalBill(size, toppings));

        try {
            orderDAO.updateOrder(selectedOrder);
            readOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder() {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            showAlert("Error", "Select an order to delete.");
            return;
        }

        try {
            orderDAO.deleteOrder(selectedOrder.getOrderId());
            readOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getPizzaSize() {
        if (xlBox.isSelected()) return "XL";
        if (lBox.isSelected()) return "L";
        if (mBox.isSelected()) return "M";
        if (sBox.isSelected()) return "S";
        return "";
    }

    protected double calculateTotalBill(String size, int toppings) {
        double basePrice = switch (size) {
            case "XL" -> 15.0;
            case "L" -> 12.0;
            case "M" -> 10.0;
            case "S" -> 8.0;
            default -> 0.0;
        };
        double subtotal = basePrice + (toppings * 1.5);
        return subtotal * 1.15; // adding 15% HST
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


