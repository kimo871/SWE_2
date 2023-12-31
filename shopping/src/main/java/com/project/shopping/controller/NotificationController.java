package com.project.shopping.controller;

import com.project.shopping.service.MessageStatistics;
import org.springframework.scheduling.annotation.Scheduled;
import com.project.shopping.model.Message;
import com.project.shopping.service.MessageService;
import com.project.shopping.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
@RequestMapping("/Notifications")
public class NotificationController {
    private final MessageService messageService;
    private  final MessageStatistics messageStatistics;
    @Autowired
    public NotificationController(MessageService ms) {
        this.messageService = MessageService.getInstance();
        this.messageStatistics = new MessageStatistics();
    }

    @GetMapping("/messageQueue")
    public Queue<Message> getMessageQueue(){
        return messageService.getMessageQueue() ;
    }

    @Scheduled(cron = "0/5 * * * * *") // Cron expression for running every 5 secs
    public void execute() {
        messageService.popMessage();
    }

    @GetMapping("/mostNotifiedPhone")
    public String getMostNotifiedPhone(){
        return messageStatistics.MostNotifiedPhone();
    }

    @GetMapping("/mostNotifiedEmail")
    public String getMostNotifiedEmail(){
        return messageStatistics.MostNotifiedEmail();
    }

}
