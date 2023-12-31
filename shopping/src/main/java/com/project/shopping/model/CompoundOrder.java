package com.project.shopping.model;

import java.util.*;

public class CompoundOrder extends Order {
    private ArrayList<Order> orders = new ArrayList<Order>();
    Double price ;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public double calculatePrice() {
        for (Order order : orders) {
            price += order.calculatePrice();
        }
        return price;
    }

    public ArrayList<Product> getProducts(){
        ArrayList<Product>products = new ArrayList<>();
        for(Order O : orders){
            products.addAll(O.getProducts()) ;
        }
        return products ;
    }
};