package com.project.shopping.model;

public interface IChannel {
    public void sendNotification(String content);
    public String getKey() ;
}
