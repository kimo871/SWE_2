package com.project.shopping.service;

import com.project.shopping.Repositories.Products_db;
import com.project.shopping.model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class CountProductsPerCategory {
    private static Products_db productsDb;
    private  HashMap<String, Integer> CountProductsPerCat;

    public CountProductsPerCategory() {
        productsDb = Products_db.getInstance();
        Update();
    }

    public void Update() {
        ArrayList<Product> products = productsDb.getProducts();
        HashMap<String, Integer> CountProductsPerCat = new HashMap<>();
        for (Product P : products) {
            String categoryName = P.getCategoryName();
            if (CountProductsPerCat.containsKey(categoryName)) {
                CountProductsPerCat.put(categoryName, CountProductsPerCat.get(categoryName) + P.getQuantity());
            } else {
                CountProductsPerCat.put(categoryName, P.getQuantity());
            }
        }
    }

    public HashMap<String, Integer> getCountProductsPerCat() {
        Update();
        return CountProductsPerCat;
    }

}
