package com.trios.dianatpizzaapp_test3b;

public class Order {
    private int orderId;
    private int customerId;
    private String pizzaSize;
    private int numberOfToppings;
    private double totalBill;

    public Order() {}

    public Order(int customerId, String pizzaSize, int numberOfToppings, double totalBill) {
        this.customerId = customerId;
        this.pizzaSize = pizzaSize;
        this.numberOfToppings = numberOfToppings;
        this.totalBill = totalBill;
    }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getPizzaSize() { return pizzaSize; }
    public void setPizzaSize(String pizzaSize) { this.pizzaSize = pizzaSize; }

    public int getNumberOfToppings() { return numberOfToppings; }
    public void setNumberOfToppings(int numberOfToppings) { this.numberOfToppings = numberOfToppings; }

    public double getTotalBill() { return totalBill; }
    public void setTotalBill(double totalBill) { this.totalBill = totalBill; }
}