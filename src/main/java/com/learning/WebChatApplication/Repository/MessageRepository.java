package com.learning.WebChatApplication.Repository;

import com.learning.WebChatApplication.Entity.ChatRoom;
import com.learning.WebChatApplication.Entity.Message;
import com.learning.WebChatApplication.Entity.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // What are the differnet things You need from the Message??
    // Think about it

    // All Message by specific sender
    // All Message by of past 10 minute from specific sender
    // All Message by ChatRoom
    // All Private Message between Users
    // All UnReaded Message
    // Count unreaded message

    List<Message> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);

    // One thing, this JPA Also Support Pagable
    Page<Message> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);

    // Find the private Message between two users
    @Query("Select ms from Message ms WHERE (ms.sender=:user1 AND ms.receiver=:user2) OR (ms.receiver=:user1 AND ms.sender=:user2) ORDER BY ms.createdAt ASC")
    List<Message> findPrivateMessageBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    // Find the private Message between two users (Pageable)
    @Query("Select ms from Message ms " +
            "WHERE (ms.sender=:user1 AND ms.receiver=:user2) " +
            "OR (ms.receiver=:user1 AND ms.sender=:user2) " +
            "ORDER BY ms.createdAt ASC")
    Page<Message> findPrivateMessageBetweenUsers(@Param("user1") User user1, @Param("user2") User user2, Pageable pageable);

    // Find Message By Sender
    List<Message> findBySenderOrderByCreatedAtDesc(User sender);

    // Find all the unreaded message from the user
    @Query("Select ms from Message ms " +
            "WHERE ms.receiver = :user " +
            "AND ms.status != 'READ' " +
            "ORDER BY ms.createdAt DESC")
    List<Message> findUnreadMessageForUser(@Param("user") User user);

    // Count Unread message from the User
    @Query("Select count(ms) from Message ms " +
            "WHERE ms.receiver = :user " +
            "AND ms.status != 'READ'")
    Long countUnreadMessageForUser(@Param("user") User user);

    // Find recent message in the chat
    @Query("Select ms from Message ms " +
            "WHERE ms.chatRoom = :chatRoom " +
            "AND ms.createdAt >= :since " +
            "ORDER BY ms.createdAt DESC")
    List<Message> findRecentMessageInChatRoom (@Param("chatRoom") ChatRoom chatRoom,@Param("since") LocalDateTime since);

    // Find message by type
    List<Message> findByMessageTypeOrderByCreatedAtDesc(Message.MessageType type);

    // search message by content
    @Query("Select ms from Message ms " +
            "WHERE ms.content LIKE %:content% " +
            "ORDER BY ms.createdAt DESC")
    List<Message> findByMessageByContent(@Param("content") String content);

    // Find last message of the ChatRoom
    @Query("Select ms from Message ms " +
            "WHERE ms.chatRoom = :chatRoom " +
            "ORDER BY ms.createdAt DESC " +
            "LIMIT 1")
    Optional<Message> findLastMessageInChatRoom(@Param("chatRoom") ChatRoom chatRoom);

    // Update Message Status
    @Query("UPDATE Message ms " +
            "SET ms.status =:status " +
            "WHERE ms.id = :messageId")
    void updateMessageStatus(@Param("messageId") Long messageId, @Param("status") Message.MessageStatus status);

    // Delete old message for cleanUp
    @Query("DELETE FROM Message ms " +
            "WHERE ms.createdAt <= :cutOffDate")
    void deleteMessageOlderThan(@Param("cutOffDate") LocalDateTime cutOffDate);

}
