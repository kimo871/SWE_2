package com.project.shopping.model;

import java.time.LocalDateTime;

public class Shipping {

    private int OrderId;
    private int ID, cancellationTime;
    private double shippingFees;
    private String status;
    private LocalDateTime startTime;

    public Shipping(){
        startTime = LocalDateTime.now();
        cancellationTime = 1;
    }
    public double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }

    public String getStatus() {
        return status;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void place(){}

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

