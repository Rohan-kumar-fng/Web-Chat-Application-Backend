package com.learning.WebChatApplication.Repository;

import com.learning.WebChatApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Find User by Name
    Optional<User> findByUsername(String username);

    // Find User by email
    List <User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Find User by Status
    List <User> findByStatus(User.Userstatus status);

    // Find all the online user
    @Query("Select u from User u " +
            "WHERE u.status = :status " +
            "ORDER BY u.createdAt DESC")
    List <User> findOnLineUser(@Param("status")User.Userstatus status);

}
