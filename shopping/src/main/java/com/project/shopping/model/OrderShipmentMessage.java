package com.project.shopping.model;

import java.util.ArrayList;

public class OrderShipmentMessage implements IMessageTemplate {
    String message;

    @Override
    public String sendMessage() {
        return message;
    }

    public OrderShipmentMessage() {
    }

    public String getMessage(Customer customer, Shipping shipment) {
        message = String.format("Hello %s, your Order %d is on shipping now:\n", customer.getName(), shipment.getOrderId());
        message += "Shipping fees:" + shipment.getShippingFees() + "\n";
        message += "Estimated Delivery in 2 hours";
        return message;
    }
}
