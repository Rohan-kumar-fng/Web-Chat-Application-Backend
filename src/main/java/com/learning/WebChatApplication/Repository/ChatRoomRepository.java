package com.learning.WebChatApplication.Repository;

import com.learning.WebChatApplication.Entity.ChatRoom;
import com.learning.WebChatApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> { // You know here Long shows the id (public key)
    // Why this is interface and not class
    // Do interface only extends interface in Java?

    // JpaRepository is a interface, It extends all the CRUD interface implementation
    // like findall, saveall, etc

    Optional<ChatRoom> findByName(String name);
    // Optional is from java.util represents a containers that may or maynot hold a value.
    // https://www.perplexity.ai/search/what-does-this-means-in-jparep-Vofqj_NsQouUS8Bbk1if3A

    List<ChatRoom> findByType(ChatRoom.ChatRoomType type);
    // Here ChatRoom is a enum inside the ChatRoom

    List<ChatRoom> findByCreatedBy(User user);

    @Query("Select cr from ChatRoom cr WHERE cr.type = :type ORDER BY cr.createdAt DESC")
    List<ChatRoom> findByTypeOrderByCreatedAtDesc(@Param("type") ChatRoom.ChatRoomType type);

    @Query("Select cr from ChatRoom cr WHERE cr.name = :name")
    List<ChatRoom> searchByName(@Param("name") String name);

    boolean existsByName(String name);
    // Oh exist also present here
}
