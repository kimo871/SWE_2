package com.project.shopping.model;

import java.util.*;

public class CompoundOrder extends Order {
    private ArrayList<SimpleOrder> orders = new ArrayList<SimpleOrder>();


    public ArrayList<SimpleOrder> getOrders() {
        return orders;
    }
    public CompoundOrder(){}

    public CompoundOrder(ArrayList<SimpleOrder> orders) {
        this.orders = orders;
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