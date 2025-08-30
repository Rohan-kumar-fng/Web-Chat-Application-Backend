package com.learning.WebChatApplication.Service;

import com.learning.WebChatApplication.Entity.ChatRoom;
import com.learning.WebChatApplication.Entity.JoiningStatusMessage;
import com.learning.WebChatApplication.Entity.Message;
import com.learning.WebChatApplication.Entity.User;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    public ChatRoom join(JoiningStatusMessage joiningStatusMessage){
        // I nned to generate one chatRoom For this User
        // First Need to create User
        // Then Create CahtRoom
        // Now add the message inside the ChatRoom
        // return That chatRoom

        // Here I have to handle the case of JOIN and LEAVE
        if(joiningStatusMessage.getType().equals(JoiningStatusMessage.JoiningStatus.JOIN)) {
            User user = new User(joiningStatusMessage.getSender(), null,null, User.Userstatus.ONLINE );

            Message message = new Message();
            message.setSender(user);
            message.setContent(joiningStatusMessage.getContent());

            ChatRoom chatRoom = new ChatRoom(); // Why I am creating new chatRoom everytime?
            chatRoom.getMessages().add(message);
            chatRoom.setCreatedBy(user);

            return chatRoom;
        } else {
            return null;
        }
    }
}
