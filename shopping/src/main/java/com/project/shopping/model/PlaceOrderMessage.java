package com.project.shopping.model;

import java.util.ArrayList;

public class PlaceOrderMessage implements IMessageTemplate{
    String message;
    @Override
    public String sendMessage() {
        return message;
    }
    public PlaceOrderMessage(){
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public  String getMessage(Customer customer, Order order) {
        message = String.format("Hello %s, your Order %d Created successfully\n Order Details", customer.getName(), order.getID());
        ArrayList<Product> products = order.getProducts();
        for (Product P : products) {
            message += P.getName() + P.getQuantity() + (P.getQuantity() * P.getPrice()) + "\n";
        }
        message += "Final Price:" + order.calculatePrice() + "\n";
        message += "Hope you enjoy the products yabn Elsh";
        return message;
    }
}
