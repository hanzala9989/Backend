package com.example.eventOrganizer.ModelDTO;

import org.springframework.stereotype.Component;

@Component
public class LoginModel {
    private String username;
    private String password;

    
    public LoginModel() {
    }


    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }


    public LoginModel setUsername(String username) {
        this.username = username;
        return this;
    }


    public String getPassword() {
        return password;
    }


    public LoginModel setPassword(String password) {
        this.password = password;
        return this;
    }

    
}
