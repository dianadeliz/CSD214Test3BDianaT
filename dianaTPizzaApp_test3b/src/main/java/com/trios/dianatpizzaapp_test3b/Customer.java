package com.trios.dianatpizzaapp_test3b;

public class Customer {
    private int customerId;
    private String customerName;
    private String mobileNumber;

    public Customer(int customerId, String customerName, String mobileNumber) {}

    public Customer(String customerName, String mobileNumber) {
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
}

