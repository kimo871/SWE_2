package com.project.shopping.Repositories;

import com.project.shopping.model.Order;
import com.project.shopping.model.Product;
import com.project.shopping.model.Shipping;

import java.util.ArrayList;
import java.util.List;

public class Shipments_db {

    private ArrayList<Shipping> shippings = new ArrayList<>();

    private static Shipments_db instance;

    private Shipments_db(){}

    public static Shipments_db getInstance() {
        if (instance == null) {
            synchronized (Shipments_db.class) {
                if (instance == null) {
                    instance = new Shipments_db();
                }
            }
        }
        return instance;
    }

    public boolean addShipment(Shipping new_s){
        shippings.add(new_s);
        return true;
    }

    public ArrayList<Shipping> getShippings() {
        return shippings;
    }

    public void setShippings(ArrayList<Shipping> shippings) {
        this.shippings = shippings;
    }

    public Shipping getShipment(int id){
        for(Shipping S : shippings){
            if(S.getID() == id) return S;
        }
        return null;
    }

    public void save(Shipping S) {
        for (int i = 0; i < shippings.size(); i++) {
            Shipping M = shippings.get(i);
            if (M.getID() == S.getID()) {
                shippings.set(i, S);
                break;
            }
        }
    }
}
