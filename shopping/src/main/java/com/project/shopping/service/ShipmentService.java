package com.project.shopping.service;

import com.project.shopping.Repositories.Orders_db;
import com.project.shopping.Repositories.Shipments_db;
import com.project.shopping.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.time.Duration;

@Service
public class ShipmentService {
    private OrderService orders_service;
    private Shipments_db shipments_db;
    private CustomersService customer_service;

    public ShipmentService(){
        shipments_db = Shipments_db.getInstance();
        orders_service = new OrderService();
    }

    private JwtAuthentication jwtService = JwtAuthentication.getInstance();

    public Shipping process_shipment(Shipping S){
        Order res = orders_service.getOrderById(S.getOrderId());
       if(res!=null){
            shipments_db.addProduct(S);
            return S;
       }
       return null;
    }

   public Shipping getShipmentDetails(int shipmentId){
        return shipments_db.getShipment(shipmentId);
   }

    public Shipping completeShipment(int shipmentId){
       Shipping S = shipments_db.getShipment(shipmentId);
      if(S!=null) {
          S.setStatus("completed");
          shipments_db.save(S);
      }
      return null;
    }

    public boolean deductFees(Shipping S){
         Order res = orders_service.getOrderById(S.getOrderId());
        boolean flag=false;
        if(res!=null){
            double result;
            if(res instanceof SimpleOrder) {
                Customer c = customer_service.getCustomer(res.getID());
                result = c.getBalance() - S.getShippingFees();
                flag = (result<0) ? false : true;
            }
            else{
                

            }
        }
         return flag;
    }

    public ResponseEntity<String> cancelShipment(int shipment_id){
        int idx = -1;
        ArrayList<Shipping> shippings = shipments_db.getShippings();
        for(int i = 0; i < shippings.size(); i++){
            if(shippings.get(i).getID() == shipment_id){
                Duration duration = Duration.between(shippings.get(i).getStartTime(), LocalDateTime.now());
                if(duration.toSeconds() < 30){
                    idx = i;
                } else {
                    shippings.get(i).setStatus("progressed");
                    return ResponseEntity.status(400).body("shipment can't be deleted 30 seconds passed");
                }
                break;
            }
        }
        if(idx > -1){
            shippings.remove(idx);
            shipments_db.setShippings(shippings);
            return ResponseEntity.ok("Shipment has been deleted");
        }
        return ResponseEntity.status(400).body("shipment is not found");
    }
}
