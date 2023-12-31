package com.project.shopping.service;

import com.project.shopping.Repositories.Customers_db;
import com.project.shopping.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomersService {
    public Customers_db customers_db = Customers_db.getInstance();
    public JwtAuthentication jwtService =  JwtAuthentication.getInstance();

    public Customer getCustomer(int ID){
       return customers_db.getCustomerById(ID);
    }

    public String addCustomer(Customer c){
        customers_db.saveCustomer(c);
        return jwtService.generateToken(c);

    }

    public Customer userLogin(Customer customer){
        Customer result = customers_db.getCustomerByEmail(customer.getEmail());
        if(result == null){
            System.out.println("No Such Email Exists");
            return null;
        }
        else if(result !=null && result.getPassword() != customer.getPassword())return null;
        else{
            System.out.println("logged");
            return result;
        }

    }

    public ArrayList<Customer> getCustomers(){
        return Customers_db.getCustomers();
    }

    public boolean save(Customer C){
        customers_db.saveCustomer(C);
        return true;
    }

    public Double getCustomerBalance(int ID){
        return getCustomer(ID).getBalance() ;
    }
    public void updateCustomerBalance(int ID ,Double amount) {
        customers_db.updateCustomerBalance(ID , amount);
    }
    public void refundCustomerBalance(int ID ,Double amount) {
        customers_db.refund(ID , amount);
    }
    public void restoreCustomerBalance(int ID,Double amount){customers_db.refund(ID,amount);}
}
