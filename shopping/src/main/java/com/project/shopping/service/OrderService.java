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
    public String addOrder(Order order) {
        // get Prices
        for (Product product : order.getProducts()) {
            product.setPrice(productsService.getProductBySerialNumber(product.getSerialNumber()).getPrice());
        }

        ArrayList<Product> products_copy = new ArrayList<>(productsService.getProducts());
        ArrayList<Product> order_products = order.getProducts();

        // copy products into hash map
        HashMap<Product , Integer> uniqueProducts = new HashMap<>() ;

        for(Product p :order_products){
                if (uniqueProducts.containsKey(p))
                    uniqueProducts.put(p , uniqueProducts.get(p) + p.getQuantity());
                else
                    uniqueProducts.put(p , p.getQuantity()) ;
        }

        // check products in store
        for (Map.Entry<Product , Integer> entry : uniqueProducts.entrySet()) {
            int pID = entry.getKey().getSerialNumber();
            if (entry.getValue() > productsService.getAvailableQuantity(pID))
                return entry.getKey().getName() + " is Not Available in our store !" ;
        }


        if(order instanceof CompoundOrder){
            // check customers balance
            for (Order simpleOrder : ((CompoundOrder) order).getOrders()) {

                double price = simpleOrder.calculatePrice();
                double customerBalance = customersService.getCustomerBalance(simpleOrder.getCustomerId());
                if (customerBalance <price)
                    return "not sufficient balance" ;

            }
            // remove from customer balance
            for (Order simpleOrder : ((CompoundOrder) order).getOrders()) {
                double price = simpleOrder.calculatePrice();
                customersService.updateCustomerBalance(simpleOrder.getCustomerId() , price);
            }
        }
        else{
            // check customer balance
            double price = order.calculatePrice();
            double customerBalance = customersService.getCustomerBalance(order.getCustomerId());
            if(customerBalance < price)
                return "not sufficient balance" ;
            // remove from customer balance
            customersService.updateCustomerBalance(order.getCustomerId() , price);
        }

        // update our store
        for (Map.Entry<Product , Integer> entry : uniqueProducts.entrySet()) {
            int pID = entry.getKey().getSerialNumber();
            productsService.updateProductQuantity(pID , entry.getValue()); ;
        }

        ordersDb.addOrder(order);
        // send notification
        messageService.createMessage(order , customersService.getCustomer(order.getCustomerId()));
        // do not forget to update quantity
        productsService.saveNew(products_copy);
        return "";

    }

    public String cancelOrder(int orderId) {
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
            double fees = shippings.get(idx).getShippingFees();
            Order order = ordersDb.getOrderById(orderId) ;
            customersService.refundCustomerBalance( order.getCustomerId(),fees );
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
                    return "order can't be deleted 30 seconds passed";
                }
                break;
            }
        }
        if (idx > -1) {
            double order_price = orders.get(idx).getPrice();
            customersService.refundCustomerBalance(ordersDb.getOrderById(orderId).getCustomerId() ,order_price );
            orders.remove(idx);
            ordersDb.setOrders(orders);
            return "The order has been deleted";
        }
        return "";
    }

    public Order getOrderById(int id){
        return ordersDb.getOrderById(id);
    }

    public ArrayList<Order> getOrders(){
        return ordersDb.getOrders();
    }

}
