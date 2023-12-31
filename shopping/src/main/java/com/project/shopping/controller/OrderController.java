package com.project.shopping.controller;

import com.project.shopping.model.*;
import com.project.shopping.service.JwtAuthentication;
import com.project.shopping.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.shopping.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity; // For ResponseEntity class


@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private JwtAuthentication jwtService = JwtAuthentication.getInstance();

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/simpleOrder/add")
    public ResponseEntity<Response> addOrder(@RequestBody SimpleOrder order){
        //System.out.println(authorizationHeader);
        //String token="";
        //if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // token = authorizationHeader.replace("Bearer ", "").trim();
          //  System.out.println("Extracted Token: " + token); // Check the extracted token
            // Further processing or validation of the token
            // Extract the JWT token from the header
       // }

        //if(jwtService.validateToken(token)){
            String res= orderService.addOrder(order);
            System.out.println(res);
            if(!res.equals(""))return ResponseEntity.status(200).body(new Response(res));
            return ResponseEntity.status(200).body(new Response("Order Added Successfully"));
      //  }
        //else  return ResponseEntity.status(401).body(new Response("Token Invalid"));
    }

    @PostMapping("/compoundOrder/add")
    //@RequestHeader("Authorization") String authorizationHeader ,
    public ResponseEntity<Response> addOrder( @RequestBody CompoundOrder order){
        //String token = authorizationHeader.replace("Bearer ", ""); // Extract the JWT token from the header
       // if(jwtService.validateToken(token)){
            String res= orderService.addOrder(order);
            if(!res.equals(""))return ResponseEntity.status(200).body(new Response(res));
            return ResponseEntity.status(200).body(new Response("Order Added Successfully"));
       // }
        //else  return ResponseEntity.status(401).body(new Response("Token Invalid"));
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") int orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") int orderId) {
        return orderService.cancelOrder(orderId);
    }

}