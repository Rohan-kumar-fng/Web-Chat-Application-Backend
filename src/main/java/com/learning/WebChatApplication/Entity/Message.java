package com.learning.WebChatApplication.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Long id;
    private String from;
    private String receiverName;
    private String content;
    private LocalDateTime receivedTime;
    private LocalDateTime sendTime;
    private String status;

    public Message(String from, String content, LocalDateTime receivedTime, String status){
        this.from = from;
        this.content = content;
        this.receivedTime = receivedTime;
        this.status = status;
    }
}
