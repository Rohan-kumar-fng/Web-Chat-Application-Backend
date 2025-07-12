package com.learning.WebChatApplication.Controller;

import com.learning.WebChatApplication.Entity.Message;
import com.learning.WebChatApplication.Service.MessageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
public class ChatController {

    MessageServices messageServices;

    private SimpMessagingTemplate simpMessagingTemplate;

    ChatController(MessageServices messageServices){
        this.messageServices = messageServices;
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message send(Message message) throws Exception {
        return messageServices.getResponse(new Message(message.getFrom(), message.getContent(),message.getSendTime(), message.getStatus()));
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message sendtoFrontend(Message message) throws Exception {
        System.out.println("From: " + message.getFrom() + "Status: " + message.getStatus());
        return messageServices.getResponse(new Message(message.getFrom(), message.getContent(),message.getSendTime(), message.getStatus()));
    }

    @MessageMapping("/private-message")
    public Message sendToPrivate(@Payload Message message) throws Exception {
        System.out.println("From: "+message.getFrom()+", To:"+message.getReceiverName()+", Message:"+message.getContent());
        simpMessagingTemplate.convertAndSendToUser( message.getReceiverName() , "/private", message);
        return message;
    }

    // instead of using sendTo  we used the simpMessagingTemplate to deliver the
    // message to a specific user
//    @MessageMapping("/hello")
//    public Message addUser(@Payload Message chatMessage) {
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverName(),"/topic/greetings", chatMessage);
//        return chatMessage;
//    }

}
