package com.project.shopping.service;

import com.project.shopping.Repositories.Messages_db;
import com.project.shopping.model.OrderShipmentMessage;
import com.project.shopping.model.PlaceOrderMessage;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class MessageStatistics {
    private static Messages_db MessageDB;

    public MessageStatistics() {
        MessageDB = Messages_db.getInstance();
    }

    public String getMaxInHashMap(HashMap<String, Integer> hashMap) {
        String MaxStr = "NONE";
        int mx = 0;
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (mx < entry.getValue()) {
                mx = entry.getValue();
                MaxStr = entry.getKey();
            }
        }
        return MaxStr;
    }

    public String MostNotifiedPhone() {
        return getMaxInHashMap(MessageDB.getPhonesNotified());
    }

    public String MostNotifiedEmail() {
        return getMaxInHashMap(MessageDB.getEmailNotified());
    }

    public String MaxTemplateUsed() {
        int[] counter = MessageDB.getTemplatesCounter();
        if (counter[0] > counter[1]) {
            PlaceOrderMessage placeOrderMessage = new PlaceOrderMessage();
            return placeOrderMessage.getTemplate();
        } else if (counter[0] < counter[1]) {
            OrderShipmentMessage OrderShipment = new OrderShipmentMessage();
            return OrderShipment.getTemplate();
        } else {
            return "BOTH EQUAL";
        }
    }
}
