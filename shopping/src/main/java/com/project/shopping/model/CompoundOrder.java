package com.project.shopping.model;

import java.util.*;

public class CompoundOrder extends Order {
    private ArrayList<Order> orders = new ArrayList<Order>();


    public ArrayList<Order> getOrders() {
        return orders;
    }
    public CompoundOrder(){}

    public CompoundOrder(ArrayList<Order> orders) {
        this.orders = orders;
        setPrice(calculatePrice());
    }

    public double calculatePrice() {
        price=0.0;
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