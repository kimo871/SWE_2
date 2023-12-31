package com.project.shopping.model;

import java.util.ArrayList;

public class SimpleOrder extends Order {
    private ArrayList<Product> products;

    public ArrayList<Product> getProducts() { return products; }


    public double calculatePrice(){
        price=0;
        for(Product p : products){
            price+=(p.getPrice()*p.getQuantity());
        }
        return price;
    }
}
