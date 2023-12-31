package com.project.shopping.controller;

import com.project.shopping.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.shopping.service.ProductsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/products")
public class ProductController {
 private ProductsService productsService;
 @Autowired
 public ProductController(ProductsService serviceA) {
  this.productsService = serviceA;
 }
  @PostMapping("/add")
  public void addProduct(@RequestBody Product productToAdd){
   productsService.addProduct(productToAdd);
  }

  @GetMapping("/{id}")
  public Product getProduct(@PathVariable("id") int productId) {
   return productsService.getProductBySerialNumber(productId);
  }

  @GetMapping("/get")
 public ArrayList<Product> getProducts(){
  return productsService.getProducts();
  }
}
