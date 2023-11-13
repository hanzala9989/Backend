package com.example.eventOrganizer.MongoDBEntity;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    private String id;
    private String username;
    private String userEmail;
    private String userPassword;
    private String dOB;
    private String gender;
    private String roleName;
    private String phoneNumber;
    private String accountStatus;

    public User() {
    }

    public User(String id, String username, String userEmail, String userPassword, String dOB, String gender,
            String roleName, String phoneNumber, String accountStatus) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.dOB = dOB;
        this.gender = gender;
        this.roleName = roleName;
        this.phoneNumber = phoneNumber;
        this.accountStatus = accountStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", userEmail=" + userEmail + ", userPassword="
                + userPassword + ", dOB=" + dOB + ", gender=" + gender + ", roleName=" + roleName + ", phoneNumber="
                + phoneNumber + ", accountStatus=" + accountStatus + "]";
    }

    // getters and setters
}
