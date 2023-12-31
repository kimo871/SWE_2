package com.project.shopping.model;

public class Product {
    private String vendor, name;
    private double price;
    private int quantity, serialNumber;
    private static int counter = 0;

    public Product(){
        serialNumber = counter++;
    }

    public String getName(){
         return name;
    }

    public double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) { this.price = price; }

    public void setSerialNumber(int serialNumber) { this.serialNumber = serialNumber; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setVendor(String vendor) { this.vendor = vendor; }

    public int getSerialNumber() { return serialNumber; }

    public String getVendor() { return vendor; }

}

