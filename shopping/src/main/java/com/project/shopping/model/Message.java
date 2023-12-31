package com.project.shopping.model;

import java.nio.channels.Channel;

public class Message {
    IChannel channel;
    String Content;

    public Message(IChannel channel, String content) {
        this.channel = channel;
        this.Content = content;
    }

    public IChannel getChannel() {
        return channel;
    }

    public void setChannel(IChannel channel) {
        this.channel = channel;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void sendMessage() {
        channel.sendNotification(Content);
    }
}
