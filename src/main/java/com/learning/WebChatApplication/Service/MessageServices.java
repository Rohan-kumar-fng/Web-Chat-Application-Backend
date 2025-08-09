package com.learning.WebChatApplication.Service;

//import com.learning.WebChatApplication.Entity.Chat;
import com.learning.WebChatApplication.Entity.Message;
//import com.learning.WebChatApplication.Entity.User;
import org.springframework.stereotype.Service;

@Service
public class MessageServices {

//    public Chat generateChat(User u1, User u2, String messageContent){
//
//        Message message = new Message();
//        message.setContent(messageContent);
//        message.setSendTime(LocalDateTime.now());
//
//        Chat chat = new Chat();
//        chat.setSender(u1);
//        chat.setReceiver(u2);
//        chat.setMessage(message);
//        chat.setId(UUID.randomUUID().hashCode());
//        chat.setChatTime(LocalDateTime.now());
//
//        return chat;
//    }

    public Message getResponse(Message message) throws Exception{
        System.out.println("Received the Request");
        System.out.println("From: "+ message.getSender()+", "+message.getContent()+", "+message.getStatus());
        Thread.sleep(1000);
        message.setContent(message.getContent() + ", Thanks for your Response");
        message.setStatus(message.getStatus());
        return message;
    }
}
