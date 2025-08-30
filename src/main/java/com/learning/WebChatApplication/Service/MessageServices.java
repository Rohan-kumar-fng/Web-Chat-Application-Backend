package com.learning.WebChatApplication.Service;

//import com.learning.WebChatApplication.Entity.Chat;
import com.learning.WebChatApplication.Entity.JoiningStatusMessage;
import com.learning.WebChatApplication.Entity.Message;
//import com.learning.WebChatApplication.Entity.User;
import com.learning.WebChatApplication.Entity.User;
import com.learning.WebChatApplication.Repository.MessageRepository;
import com.learning.WebChatApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;
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

    public Message getResponse(JoiningStatusMessage message) {
        System.out.println("Received the Request");
        System.out.println("From: " + message.getSender() + ", " + message.getContent() + ", " + message.getType());

        try {
            if (message.getType() == JoiningStatusMessage.JoiningStatus.JOIN) {
                // Check if user already exists
                Optional<User> existingUser = userRepository.findByUsername(message.getSender());
                if (existingUser.isPresent()) {
                    // Instead of throwing exception, return system message
                    System.out.println("User is Already Present");

                    Message msg = new Message();
                    msg.setContent(message.getSender() + " is already in the Chat.");
                    msg.setStatus(Message.MessageStatus.READ);
                    return msg;
                }

                // Save new user
                User user = new User();
                user.setUsername(message.getSender());
                user.setStatus(User.Userstatus.ONLINE);
                userRepository.save(user);

                // Return join notification
                Message msg = new Message();
                msg.setContent(message.getSender() + " Joined the Chat.");
                msg.setStatus(Message.MessageStatus.READ);
                return msg;

            } else if (message.getType() == JoiningStatusMessage.JoiningStatus.MESSAGE) {
                // Find user
                Optional<User> user = userRepository.findByUsername(message.getSender());
                if (user.isEmpty()) {
                    throw new RuntimeException("User not found: " + message.getSender());
                }

                // Save message
                Message msg = new Message();
                msg.setChatRoom(message.getChatRoom());
                msg.setSender(user.get());
                msg.setSendTime(LocalDateTime.now());
                msg.setContent(message.getContent());
                msg.setStatus(Message.MessageStatus.SENT);

                return messageRepository.save(msg);
            }

            throw new RuntimeException("Unsupported message type: " + message.getType());

        } catch (Exception e) {
            System.out.println("User Not able to save in the DB " + e);
            throw new RuntimeException(e);
        }
    }
}
