package com.learning.WebChatApplication.Controller;

import com.learning.WebChatApplication.Entity.ChatRoom;
import com.learning.WebChatApplication.Entity.JoiningStatusMessage;
import com.learning.WebChatApplication.Entity.Message;
import com.learning.WebChatApplication.Service.ChatRoomService;
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
    private final ChatRoomService chatRoomService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public") // This is the Message Broker,
    public Message sendtoFrontend(@Payload JoiningStatusMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        // Add session ofr better tracking of user joining
        String sessionId = headerAccessor.getSessionId();
        System.out.println("Session: " + sessionId + ", From: " + message.getSender() + ", Status: " + message.getType());

        // If timestamp is not provided
        if(message.getSendTime() == null){
            message.setSendTime(LocalDateTime.now());
        }

        return messageServices.getResponse(message);
        // Hmm Why the fuck I am sending the response, If the user actually get added to the chatRoom
        // I need to tell the backend so that he storei in the DB

        // Ok I have to made the distinction like the list of message for that chatRoom I need to send??
        // Ok, Onec the user join for the first time, It get the list foa ll the histor message for that chatRoom
        // After that for every message it should not send anything to the frontend
        // Frontend maintain its array mfo teh chatRoom to Render in the UI
        // Althrough the fronend store itself and also send in the backend witht he label fo MESSAGE which means not to SEND any response, But to save the message for that chatroom to the DB


    }

    @MessageMapping("/private-message")
    public Message sendToPrivate(@Payload Message message) throws Exception {
        System.out.println("From: "+message.getSender()+", To:"+message.getReceiver()+", Message:"+message.getContent());
        simpMessagingTemplate.convertAndSendToUser( message.getReceiver().getUsername() , "/private", message);
        return message;
    }

    @MessageMapping("/status")
    @SendTo("/chatroom/public")
    public JoiningStatusMessage sendChatRoomInformation(@Payload JoiningStatusMessage joiningStatusMessage){
        // I think here I can make the use to join a chatRoom, There are differnt kind of chatRoom can be available??
        // First is the connection, which just need /chat, other is joining the chatRoom
        // Here I can return the chatRoom with the use rInformation
        // I think this will return some ChatRoom Id which is used by the customer to talk
        System.out.println(joiningStatusMessage.getSender() + " Joined the ChatRoom With Message : " + joiningStatusMessage.getContent());
        ChatRoom chatRoom = chatRoomService.join(joiningStatusMessage);
        System.out.println(chatRoom);
        joiningStatusMessage.setContent(joiningStatusMessage.getContent() + " What the Fuck");
        return joiningStatusMessage;
    }

    // instead of using sendTo  we used the simpMessagingTemplate to deliver the
    // message to a specific user
//    @MessageMapping("/hello")
//    public Message addUser(@Payload Message chatMessage) {
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverName(),"/topic/greetings", chatMessage);
//        return chatMessage;
//    }

}
