package com.project.shopping.service;
import com.project.shopping.Repositories.Products_db;
import com.project.shopping.model.Product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class ProductsService {

    private Products_db  productsDb ;

    public ProductsService(){
        productsDb = Products_db.getInstance();
    }
    private JwtAuthentication jwtService = JwtAuthentication.getInstance();

    public ResponseEntity<String> addProduct(Product product){
        productsDb.addProduct(product);
        return ResponseEntity.ok("Product has been added");
    }

    public Product getProductBySerialNumber(int serialNumber){
        return productsDb.getProductById(serialNumber);
    }

    public int getAvailableQuantity(int productId){
        Product p = productsDb.getProductById(productId) ;
        if (p == null)
            return 0;
        return p.getQuantity() ;
    }

    public void saveNew(ArrayList<Product> p){
        productsDb.save(p);
    }

    public ArrayList<Product> getProducts(){
        return productsDb.getProducts();
    }

    public void updateProductQuantity(int productId , int quantity){
        productsDb.removeProduct(productId , quantity);
    }

}
