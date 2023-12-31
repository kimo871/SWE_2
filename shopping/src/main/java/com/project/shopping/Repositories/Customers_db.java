package com.project.shopping.Repositories;

import java.util.ArrayList;
import java.util.*;

import com.project.shopping.model.Customer;

public class Customers_db {
    private static Customers_db instance;
    private static final ArrayList<Customer> Customers = new ArrayList<>();

    private Customers_db() {
    }

    public static Customers_db getInstance() {
        if (instance == null) {
            synchronized (Customers_db.class) {
                if (instance == null) {
                    instance = new Customers_db();
                }
            }
        }
        return instance;
    }

    public Customer getCustomerById(int id) {
        for (Customer c : Customers) {
            if (c.getID() == id) return c;
        }
        return null;
    }

    public static ArrayList<Customer> getCustomers() {
        return Customers;
    }

    public Customer getCustomerByEmail(String email) {
        for (Customer c : Customers) {
            if (c.getEmail().equals(email) ) return c;
        }
        return null;
    }

    public boolean saveCustomer(Customer new_c) {
        Customers.add(new_c);
        return true;
    }

    public void updateCustomerBalance(int ID, Double amount) {
        for (Customer c : Customers) {
            if (c.getID() == ID) {
                c.setBalance(c.getBalance() - amount);
            }
        }
    }

    public void refund(int ID, Double amount) {
        for (Customer c : Customers) {
            if (c.getID() == ID) {
                c.setBalance(c.getBalance() + amount);
            }
        }
    }
};
