package com.project.shopping.controller;

import com.project.shopping.model.Product;
import com.project.shopping.service.CountProductsPerCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.shopping.service.ProductsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/product")
public class ProductController {
 private ProductsService productsService;
 private CountProductsPerCategory countProductsPerCategory;
 @Autowired
 public ProductController(ProductsService serviceA) {
  this.productsService = serviceA;
 }

  @PostMapping("/add")
  public ResponseEntity<String> addProduct(@RequestBody Product productToAdd){
    return productsService.addProduct(productToAdd);
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable("id") int productId) {
   return productsService.getProductBySerialNumber(productId);
  }

  @GetMapping("/CountProductsPerCategory")
 public HashMap<String,Integer> getCountProductsPerCategory() {
   return countProductsPerCategory.getCountProductsPerCat();
  }

  @GetMapping("/get")
 public ArrayList<Product> getProducts(){
  return productsService.getProducts();
  }
}
