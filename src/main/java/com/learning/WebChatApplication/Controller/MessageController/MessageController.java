package com.learning.WebChatApplication.Controller.MessageController;

//import com.learning.WebChatApplication.Entity.Chat;
//import com.learning.WebChatApplication.Entity.Message;
//import com.learning.WebChatApplication.Entity.User;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    // this is a function that is used to create a api endpoint for messages
//    // I think here I have to call services because logic is written in services file
//    MessageServices messageServices;
//    UserServices userServices;
//
//    @Autowired
//    MessageController(MessageServices m, UserServices u){
//        this.messageServices = m;
//        this.userServices = u;
//    }
//
//    @PostMapping
//    Chat sentMessage(String sender, Integer senderMobNo, String receiver, Integer receiverMobNo, String content){
//        User senderUser = userServices.getUserInformation(sender,senderMobNo);
//        User receiverUSer = userServices.getUserInformation(receiver,receiverMobNo);
//        return messageServices.generateChat(senderUser,receiverUSer,content);
//    }
}
