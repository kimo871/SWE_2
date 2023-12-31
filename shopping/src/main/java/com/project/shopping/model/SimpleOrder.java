package com.project.shopping.model;

import java.util.ArrayList;

public class SimpleOrder extends Order {
    private ArrayList<Product> products;
    private double price;

    public ArrayList<Product> getProducts() { return products; }

    public void setPrice(double price) { this.price = price; }

    public double getPrice() { return price; }

    public double calculatePrice(){
        for(Product p : products){
            price+=p.getPrice();
        }
        return price;
    }
}
