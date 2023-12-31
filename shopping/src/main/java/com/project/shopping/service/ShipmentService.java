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
    private MessageService messageService;
    private CustomersService customersService;

    public ShipmentService(){
        shipments_db = Shipments_db.getInstance();
        orders_service = new OrderService();
        messageService = MessageService.getInstance() ;
        customersService = new CustomersService();
    }

    private JwtAuthentication jwtService = JwtAuthentication.getInstance();

    public String process_shipment(Shipping S){
        Order res = orders_service.getOrderById(S.getOrderId());

        if(res!=null){
            shipments_db.addProduct(S);
            messageService.createMessage(S , customersService.getCustomer(res.getCustomerId()));
            if (!deductFees(S) ){
                return "Shipment placed successfully" ;
            }
            else
                return "Not enough balance" ;
        }
        return "";
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
        boolean flag;
        if(res!=null){
            double result;
            if(res instanceof SimpleOrder) {
                Customer c = customersService.getCustomer(res.getCustomerId());
                result = c.getBalance() - S.getShippingFees();
                flag = (result<0) ? false : true;
                if(flag)customersService.updateCustomerBalance(res.getCustomerId() , S.getShippingFees());
                return flag;
            }
            else{
                int numOfOrders = ((CompoundOrder)res).getOrders().size();
                for (Order simpleOrder : ((CompoundOrder) res).getOrders()) {

                    double price = S.getShippingFees()/numOfOrders;
                    double customerBalance = customersService.getCustomerBalance(simpleOrder.getCustomerId());
                    if (customerBalance <price)
                        return false;
                }
                for (Order simpleOrder : ((CompoundOrder) res).getOrders()) {

                    double price = S.getShippingFees()/numOfOrders;
                    customersService.updateCustomerBalance(simpleOrder.getCustomerId() , price);
                }
            }
        }
         return true;
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
            Shipping shipment = shipments_db.getShipment(idx) ;
            Order order = orders_service.getOrderById(shipment.getOrderId()) ;
            Customer customer = customersService.getCustomer(order.getCustomerId());
            shippings.remove(idx);
            shipments_db.setShippings(shippings);

            if (order instanceof SimpleOrder) {
                customersService.refundCustomerBalance(customer.getID(), shipment.getShippingFees());
            }
            else{
                int numOfOrders = ((CompoundOrder)order).getOrders().size();
                Double amount = shipment.getShippingFees()/numOfOrders ;
                for (Order simpleOrder : ((CompoundOrder) order).getOrders()) {
                    customersService.refundCustomerBalance(simpleOrder.getID(), amount);
                }
            }
            return ResponseEntity.ok("Shipment has been deleted");
        }
        return ResponseEntity.status(400).body("shipment is not found");
    }
}
