package com.learning.WebChatApplication.Entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class Chat {
    private Integer id;
    private User sender;
    private User receiver;
    private Message message;
    private LocalDateTime chatTime;
}
