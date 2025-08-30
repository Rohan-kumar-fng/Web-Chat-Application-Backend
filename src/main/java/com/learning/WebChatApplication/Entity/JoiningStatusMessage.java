package com.learning.WebChatApplication.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JoiningStatusMessage {
    private String sender;
    private String content;
    private ChatRoom chatRoom;
    private LocalDateTime sendTime;
    private JoiningStatus type;

    public enum JoiningStatus {
        JOIN, MESSAGE, LEAVE
    }
}
