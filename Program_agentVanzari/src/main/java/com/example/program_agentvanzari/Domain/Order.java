package com.example.program_agentvanzari.Domain;

import java.io.Serializable;

public class Order extends Entity implements Serializable {
    private String customerName;
    private String customerSurname;
    private String customerAddress;
    private double postalCode;
    private String customerEmail;
    private String customerPhone;
    private double orderAmount;
    private int productId;
    public Order(int orderID, String customerName, String customerSurname, String customerAddress, double postalCode, String customerEmail, String customerPhone, double orderAmount, int productId) {
        super(orderID);
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.orderAmount = orderAmount;
        this.productId = productId;
    }

    @Override
    public int getId() {return super.getId();}
    public String getCustomerName() {return customerName;}
    public String getCustomerSurname() {return customerSurname;}
    public String getCustomerAddress() {return customerAddress;}
    public double getPostalCode() {return postalCode;}
    public String getCustomerEmail() {return customerEmail;}
    public String getCustomerPhone() {return customerPhone;}
    public double getOrderAmount() {return orderAmount;}
    public int getProductId() {return productId;}
    @Override public void setId(int id) {super.setId(id);}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public void setCustomerSurname(String customerSurname) {this.customerSurname = customerSurname;}
    public void setCustomerAddress(String customerAddress) {this.customerAddress = customerAddress;}
    public void setPostalCode(double postalCode) {this.postalCode = postalCode;}
    public void setCustomerEmail(String customerEmail) {this.customerEmail = customerEmail;}
    public void setCustomerPhone(String customerPhone) {this.customerPhone = customerPhone;}
    public void setOrderAmount(double orderAmount) {this.orderAmount = orderAmount;}
    public void setProductId(int productId) {this.productId = productId;}
}
