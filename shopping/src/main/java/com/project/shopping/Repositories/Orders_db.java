package com.project.shopping.Repositories;

import com.project.shopping.model.Order;

import java.util.ArrayList;
import java.util.List;

public class Orders_db {
    private ArrayList<Order> Orders = new ArrayList<>();

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }

    private static Orders_db instance;
    public static Orders_db getInstance() {
        if (instance == null) {
            synchronized (Orders_db.class) {
                if (instance == null) {
                    instance = new Orders_db();
                }
            }
        }
        return instance;
    }

    public Order getOrderById(int id){
        for(Order c : Orders){
            if(c.getID() == id)return c;
        }
        return null;
    }

    public Order addOrder(Order O){
        Orders.add(O);
        return O;
    }

    public boolean saveCustomer(Order new_c){
        Orders.add(new_c);
        return true;
    }
}

// customer model  order model shipment model


