package com.learning.WebChatApplication.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false) // Why sender_id, Is it other table columns name???
    private User sender; // BEtter to store the sender Id instead of sender name, Why?? I anyway get the user from userid

    @ManyToOne(fetch = FetchType.LAZY)   // By the way fetch lazy is by default
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // Every chartRoom have his own chats message, ! chat room have multipke messages

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType = MessageType.TEXT; // By default its Text message

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;

    public enum MessageType {
        TEXT, IMAGE, FILE, AUDIO, VIDEO
    }

    public enum MessageStatus {
        SENT, DELIVERED, READ
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "received_time")
    private LocalDateTime receivedTime;


    // Based on ChatRoom
    public Message(User sender, String content, ChatRoom chatRoom){
        this.sender = sender;
        this.content = content;
        this.chatRoom = chatRoom;
        this.status = MessageStatus.SENT;
    }

    // Private Message
    public Message(User sender, String content, User receiver){
        this.sender = sender;
        this.content = content;
        this.receiver = receiver;
    }
}
