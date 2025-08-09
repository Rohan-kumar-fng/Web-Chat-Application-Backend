package com.learning.WebChatApplication.Controller;

import com.learning.WebChatApplication.Entity.Message;
import com.learning.WebChatApplication.Service.MessageServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequiredArgsConstructor  // Very important, it will auto generate constructor
public class ChatController {

    private final MessageServices messageServices;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public") // This is the Message Broker,
    public Message sendtoFrontend(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        // Add session ofr better tracking of user joining
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Session: " + sessionId + ", From: " + message.getSender() + ", Status: " + message.getStatus());

        // If timestamp is not provided
        if(message.getSendTime() == null){
            message.setSendTime(LocalDateTime.now());
        }

        return messageServices.getResponse(new Message(
                message.getSender(),
                message.getContent(),
                message.getChatRoom()
        ));
    }

    @MessageMapping("/private-message")
    public Message sendToPrivate(@Payload Message message) throws Exception {
        System.out.println("From: "+message.getSender()+", To:"+message.getReceiver()+", Message:"+message.getContent());
        simpMessagingTemplate.convertAndSendToUser( message.getReceiver().getUsername() , "/private", message);
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
