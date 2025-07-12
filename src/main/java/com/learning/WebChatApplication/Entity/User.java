package com.learning.WebChatApplication.Entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String status;
    private Timestamp lastUpdated;
    private Integer mobNo;
    private String bio;

    public User(String name, Integer mobNo, String bio){
        this.name = name;
        this.mobNo = mobNo;
        this.bio = bio;
    }
}
