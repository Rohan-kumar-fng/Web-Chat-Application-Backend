package com.learning.WebChatApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

// Now I am going to attach it to the Database using the JPA using the @Entity and the @Table
// I am using this information to store for login Purpose

@Entity
@Table(name = "users")
@Data // It contains Getter, Setter, toString(), equals() and hashCode()[Compare based on field no on reference)
public class User {

    @Id // Telling it to Take it as Primary Key Hmmm
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length=50)  // SAme username may exist, But If We are using it for Login then we must mark i as unique
    private String username;

    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true, length = 12)
    private String mobileNo;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING) // Its better to tell that the status can be a Enum
    @Column(nullable = false) // User must have some status, Online, Offline, Connected, Active etc, think later
    private Userstatus status = Userstatus.OFFLINE ;  // Restrict to the Enum, Good

    @CreationTimestamp // Good property of JPA
    @Column(name = "created_at", nullable = false, updatable = false) // Not able to update later Hmm, Good
    private LocalDateTime createdAt;

    @UpdateTimestamp // Good property of JPA
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "bio")
    private String bio;

    public enum Userstatus{
        ONLINE, OFFLINE, AWAY
    }

    public User(String username, String email, String mobileNo, Userstatus status){
        this.username = username;
        this.email = email;
        this.mobileNo = mobileNo;
        this.status = status;
    }
}
