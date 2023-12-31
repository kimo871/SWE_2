package com.project.shopping.model;

public class EmailChannel implements IChannel {
    public EmailChannel(String Email) {
        this.Email = Email;
    }
    private String Email;
    @Override
    public void sendNotification(String content) {

    }

    void setEmail(String Email) {
        this.Email = Email;
    }
    public String getKey() {
        return this.Email;
    }

}