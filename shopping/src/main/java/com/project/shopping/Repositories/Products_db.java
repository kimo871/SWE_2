package com.project.shopping.Repositories;

import com.project.shopping.model.Customer;
import com.project.shopping.model.Product;

import java.util.ArrayList;

public class Products_db {
    private ArrayList<Product> products = new ArrayList<Product>();
    private static Products_db instance;
    public static Products_db getInstance() {
        if (instance == null) {
            synchronized (Products_db.class) {
                if (instance == null) {
                    instance = new Products_db();
                }
            }
        }
        return instance;
    }

    public Product getProductById(int id){
        for(Product p : products){
            if(p.getSerialNumber() == id)return p;
        }
        return null;
    }

    public boolean addProduct(Product new_p){
        products.add(new_p);
        return true;
    }

    public void save(ArrayList<Product> new_state){
        products = new_state;
    }

    public ArrayList<Product> getProducts(){
        return products;
    }

    public void removeProduct(int id , int quantityRemoved) {
        for (Product p : products) {
            if (p.getSerialNumber() == id)
                p.setQuantity(p.getQuantity() - quantityRemoved);
        }
    }
}
