package com.project.shopping.model;

import java.util.*;
import java.time.LocalDateTime;

abstract public class Order {

    private int customerId;
    private int ID;

    protected double price;

    private LocalDateTime startTime;

    private static int counter = 0;

    public Order(){
        ID = counter++;
        startTime = LocalDateTime.now();
        setPrice(calculatePrice());
    }

    private void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getStartTime() { return startTime; }

    public int getID() {
        return ID;
    }
    abstract public double calculatePrice();
    abstract  public ArrayList<Product> getProducts();

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}

