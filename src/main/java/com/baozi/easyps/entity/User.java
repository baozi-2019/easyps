package com.baozi.easyps.entity;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String userPassword;
    private String userEmail;
    private Integer userAge;

    public User() {
        super();
    }

    public User(String userName, String userPassword, String userEmail, Integer userAge) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userAge = userAge;
    }
}
