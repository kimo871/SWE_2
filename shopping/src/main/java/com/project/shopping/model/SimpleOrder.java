package com.project.shopping.model;

import java.util.ArrayList;

public class SimpleOrder extends Order {
    private ArrayList<Product> products;

    public ArrayList<Product> getProducts() { return products; }
    SimpleOrder() {}
    public SimpleOrder(ArrayList<Product> products) {
        this.products = products;
        super.setPrice(calculatePrice());
    }
    public double calculatePrice(){
        price=0;
        for(Product p : products){
            System.out.println(p.getPrice() + " " + p.getQuantity());
            price+=(p.getPrice()*p.getQuantity());
        }
        return price;
    }
}
