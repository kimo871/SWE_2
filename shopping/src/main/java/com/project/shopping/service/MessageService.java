package com.project.shopping.service;

import com.project.shopping.Repositories.Messages_db;
import com.project.shopping.model.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MessageService {
    Queue<Message>messageQueue ;
    Messages_db repositry ;
    public MessageService(){
        messageQueue = new LinkedList<>();
        repositry = Messages_db.getInstance();
    }

    public void createMessage(Order order , Customer customer){
        PlaceOrderMessage template = new PlaceOrderMessage();
        ArrayList<IChannel> availableChannels = customer.getMyChannels();
        for (int i = 0; i < availableChannels.size()  ; i++) {
            Message message = new Message(availableChannels.get(i) , template.getMessage(customer,order));
            repositry.updateTemplate(0);
            messageQueue.add(message) ;
            repositry.addMessage(message);
        }
    }

    public void createMessage(Shipping shipment , Customer customer){
        OrderShipmentMessage template = new OrderShipmentMessage();
        ArrayList<IChannel> availableChannels = customer.getMyChannels();
        for (int i = 0; i < availableChannels.size()  ; i++) {
            Message message = new Message(availableChannels.get(i) , template.getMessage(customer,shipment));
            repositry.updateTemplate(1);
            messageQueue.add(message) ;
            repositry.addMessage(message);
        }
    }

    public Queue<Message> getMessageQueue(){
        return messageQueue ;
    }

}
