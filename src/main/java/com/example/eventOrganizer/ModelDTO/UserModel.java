package com.example.eventOrganizer.ModelDTO;


public class UserModel {
    private Long userID;
    private String username;
    private String userEmail;
    private String roleName;

    public UserModel() {
    }

    public UserModel(Long userID, String username, String userEmail, String roleName) {
        this.userID = userID;
        this.username = username;
        this.userEmail = userEmail;
        this.roleName = roleName;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
