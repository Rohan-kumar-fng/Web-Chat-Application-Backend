package com.learning.WebChatApplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated
    @Column(nullable = false)
    private ChatRoomType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;  // Same user can make multiple chat rooms

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // This ChatRooms have many messages, Hmm
    @OneToMany(mappedBy = "chatRoom",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    public enum ChatRoomType {
        PUBLIC, PRIVATE, GROUP
    }

    public ChatRoom(String name, ChatRoomType type, User createBy){
        this.name = name;
        this.type = type;
        this.createdBy = createBy;
    }


}
