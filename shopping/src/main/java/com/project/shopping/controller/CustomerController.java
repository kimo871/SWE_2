package com.project.shopping.controller;

import com.project.shopping.model.*;
import com.project.shopping.model.EmailChannel;
import com.project.shopping.model.SMSChannel;
import com.project.shopping.service.CustomersService;
import com.project.shopping.service.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    public  CustomersService customerService;


    @Autowired
    public CustomerController(CustomersService serviceA) {
        this.customerService = serviceA;
    }
    //Check if user exists
    @GetMapping("/check/{id}")
    public Customer getCustomer(@PathVariable("id") int customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            return customer;
        } else {
            return null;
        }
    }

    @GetMapping("/get")
    public ArrayList<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    //Registeration Process
    @PostMapping("/add")
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        customer.setMyChannels(new ArrayList<IChannel>()) ;
        if(customer.getEmail() != null){
            customer.addChannel(new EmailChannel(customer.getEmail()));
        }
        if (customer.getPhone() != null){
            customer.addChannel(new SMSChannel(customer.getPhone()));
        }


        return  ResponseEntity.status(200).body(new Response("token is "+customerService.addCustomer(customer)));
    }
    //Login Process
    @PostMapping("/login")
    public Customer loginCustomer(@RequestBody Customer customer){
        Customer result = customerService.userLogin(customer);
        return result;
    }
}