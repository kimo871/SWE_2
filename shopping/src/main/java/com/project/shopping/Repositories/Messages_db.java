package com.project.shopping.Repositories;

import com.project.shopping.model.*;

import java.security.PublicKey;
import java.util.*;

public class Messages_db {
    static final int placeOrderMessage = 0;
    static final int oderShipmentMessage = 1;
    int [] templatesCounter;

    HashMap<String,Integer> emailAddress ;
    HashMap<String,Integer> phoneNumber ;

    static Messages_db instance ;

    private Messages_db(){
        emailAddress = new HashMap<String,Integer>();
        phoneNumber = new HashMap<String,Integer>();
        templatesCounter = new int[2] ;
    }
    public static Messages_db getInstance(){
        if (instance == null){
            instance = new Messages_db();
        }
        return instance;
    }
    public void addMessage(Message message){
        String key = message.getChannel().getKey() ;

        if (message.getChannel() instanceof EmailChannel){
            if (emailAddress.containsKey(key))
                emailAddress.put(key , emailAddress.get(key) + 1) ;
            else{
                emailAddress.put(key , 1) ;
            }
        }
        else{
            if (phoneNumber.containsKey(key))
                phoneNumber.put(key , phoneNumber.get(key) + 1) ;
            else{
                phoneNumber.put(key , 1) ;
            }
        }
    }
    public void updateTemplate(int type){
        if (type == placeOrderMessage)
            templatesCounter[placeOrderMessage] ++;
        else if (type == oderShipmentMessage)
            templatesCounter[oderShipmentMessage]++;
    }

    public HashMap<String,Integer> getPhonesNotified(){
        return phoneNumber;
    }

    public HashMap<String,Integer> getEmailNotified(){
        return emailAddress;
    }

    public int[] getTemplatesCounter() {
        return templatesCounter;
    }
}
