package com.learning.WebChatApplication.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    // This will add STOMP support
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:5173")  // this should go inside the application yml file
                .withSockJS();
    } // This endpoint the client to join the Wensocket connection for first time(Using HTTP, later shift to websocket)

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/chatroom", "/user"); // Enable simple broker for message broadcasting
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for sending messages (add /app/hello, this /hello is coming from controller receiving address from client)
        registry.setUserDestinationPrefix("/user");
    }

     // Client1 --- /app/hello --->  server  ---/topic/greeting   -> Client2 (subscribed)
//    `@EnableWebSocketMessageBroker` enables WebSocket message handling with a message broker.
//    `registerStompEndpoints` registers the endpoint clients will connect to (`/chat`), with SockJS fallback.
//    `configureMessageBroker` sets up a simple in-memory broker (`/topic`) and prefixes for application destinations (`/app`).
}
