package com.project.shopping.service;

import com.project.shopping.Repositories.Customers_db;
import com.project.shopping.Repositories.Orders_db;
import com.project.shopping.Repositories.Shipments_db;
import com.project.shopping.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private Orders_db ordersDb;
    private Shipments_db shipments_db;
    private ProductsService productsService ;
    private CustomersService customersService ;
    private MessageService messageService ;
    private JwtAuthentication jwtService = JwtAuthentication.getInstance();



    public OrderService(){
        ordersDb = Orders_db.getInstance() ;
        productsService = new ProductsService() ;
        shipments_db = Shipments_db.getInstance();
        customersService = new CustomersService();
        messageService = MessageService.getInstance() ;
    }
    public String addOrder(Order order){
        ArrayList <Product>products_copy = new ArrayList<>(productsService.getProducts());
        ArrayList<Product>order_products = order.getProducts() ;

//        HashMap<Product , Integer> uniqueProducts = new HashMap<>() ;
//
//        for(Product p :products){
//                if (uniqueProducts.containsKey(p))
//                    uniqueProducts.put(p , uniqueProducts.get(p) + p.getQuantity());
//                else
//                    uniqueProducts.put(p , p.getQuantity()) ;

        if(order instanceof CompoundOrder){
            for (Order simpleOrder : ((CompoundOrder) order).getOrders()) {
                String res = checkProducts(order.getProducts(),products_copy);
                if(res!="") return res;
                double price = simpleOrder.calculatePrice();
                double customerBalance = customersService.getCustomerBalance(simpleOrder.getCustomerId());
                String res_cstmr = checkDeduction(customerBalance ,price );
                if(res_cstmr!="")return res_cstmr;
            }
            for (Order simpleOrder : ((CompoundOrder) order).getOrders()) {
                double price = simpleOrder.calculatePrice();
                customersService.updateCustomerBalance(order.getCustomerId() , price);
            }

        } else{
            String res = checkProducts(order.getProducts(),products_copy);
            if(res!="")return res;
            double price = order.calculatePrice();
            double customerBalance = customersService.getCustomerBalance(order.getCustomerId());
            String res_cstmr = checkDeduction(customerBalance ,price );
            if(res_cstmr!="")return res_cstmr;
            customersService.updateCustomerBalance(order.getCustomerId() , price);
        }

        ordersDb.addOrder(order);
        messageService.createMessage(order , customersService.getCustomer(order.getCustomerId()));
        // do not forget to update quantity
        productsService.saveNew(products_copy);
        return "";

    }

    public String checkProducts(ArrayList<Product> order_products,ArrayList<Product> products_copy){
        for(Product p : order_products){
            Product choosenProduct =  null;
            for( Product s : products_copy ){
                if(p.getSerialNumber() == s.getSerialNumber()){
                    choosenProduct =  s;
                }
            }
            if(choosenProduct!=null){
                // Product chosen Product = products.get(index);
                if(p.getQuantity() > choosenProduct.getQuantity()) return choosenProduct.getName()+ " Quantity Available only "+p.getQuantity();
                else {choosenProduct.setQuantity(choosenProduct.getQuantity()-p.getQuantity());}
            }
            else return p.getName() + " is Not Available in our store !";
        }
        return "";
    }

    public String checkDeduction(double customerBalance , double price){
        if(customerBalance < price){
            return "not sufficient balance";
        }
        return "";
    }

    public ResponseEntity<String> cancelOrder(int orderId) {
        int idx = -1;
        ArrayList<Shipping> shippings = shipments_db.getShippings();
        ArrayList<Order> orders = ordersDb.getOrders();

        //delete shipping if found
        for (int i = 0; i < shippings.size(); i++) {
            if (shippings.get(i).getOrderId() == orderId) {
                Duration duration = Duration.between(shippings.get(i).getStartTime(), LocalDateTime.now());
                if (duration.toSeconds() < 30) {
                    idx = i;
                }
                break;
            }
        }
        if (idx > -1) {
            shippings.remove(idx);
            shipments_db.setShippings(shippings);
        }

        idx = -1;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getID() == orderId) {
                Duration duration = Duration.between(orders.get(i).getStartTime(), LocalDateTime.now());
                if (duration.toSeconds() < 30) {
                    idx = i;
                } else {
                    return ResponseEntity.status(400).body("order can't be deleted 30 seconds passed");
                }
                break;
            }
        }
        if (idx > -1) {
            orders.remove(idx);
            ordersDb.setOrders(orders);
            return ResponseEntity.ok("The order has been deleted");
        }
        return ResponseEntity.status(400).body("order is not found");
    }

    public Order getOrderById(int id){
        return ordersDb.getOrderById(id);
    }

}
