package com.project.shopping.model;

public class SMSChannel implements IChannel {
    private String PhoneNumber;

    public SMSChannel(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void sendNotification(String content) {

    }

    void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }
    public String getKey() {
        return this.PhoneNumber ;
    }
}
