package com.project.shopping.controller;


import com.project.shopping.model.Shipping;
import com.project.shopping.service.OrderService;
import com.project.shopping.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ship")
public class ShippingController {
    public ShipmentService shippingService;
    public OrderService orderService;

    @Autowired
    public ShippingController(ShipmentService serviceA) {
        this.shippingService = serviceA;
    }
    @PostMapping("/order")
    public ResponseEntity<String> createShipment(@RequestBody Shipping ship) {
        // Create a shipment for the provided order and shipping address
        //Shipping newShipment = new Shipping(order, shippingAddress);
        //orderService.getOrderById(ship.getOrderId()));
        // Process the shipment (calculate fees, deduct from customer accounts, etc.)
        String response = shippingService.process_shipment(ship);
        if (response != null) {
            return  ResponseEntity.status(200).body("Order Shipped Successfully");
        }
        return  ResponseEntity.status(400).body("Not Enough Balance");

    }


    @GetMapping("/{shipmentId}")
    public Shipping getShipmentDetails(@PathVariable int shipmentId) {
        return shippingService.getShipmentDetails(shipmentId);
    }

    @D("/cancel/{shipmentId}")
    public ResponseEntity<String> cancelShipment(@PathVariable int shipmentId) {
        return shippingService.cancelShipment(shipmentId);
    }

}