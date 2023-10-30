package com.example.eventOrganizer.Entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userID;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_dob")
    private String dOB;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_phone")
    private String phoneNumber;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "user_status")
    private String accountStatus;
    
    @Column(name = "total_reward_point")
    private String totalRewardPoint;

    @Column(name = "user_last_Update_Timestamp")
    LocalDateTime lastUpdateTimestamp = LocalDateTime.now();

    @Transient
    private String token;

    @ManyToMany
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<EventEntity> joiningEventUser = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(Long userID, String username, String userEmail, String userPassword, String dOB, String gender,
            String phoneNumber, String roleName, String accountStatus, String token,
            Set<EventEntity> joiningEventUser) {
        this.userID = userID;
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.dOB = dOB;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.accountStatus = accountStatus;
        this.token = token;
        this.joiningEventUser = joiningEventUser;
    }

    public Long getUserID() {
        return userID;
    }

    public UserEntity setUserID(Long userID) {
        this.userID = userID;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public UserEntity setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getdOB() {
        return dOB;
    }

    public UserEntity setdOB(String dOB) {
        this.dOB = dOB;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public UserEntity setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public UserEntity setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    public Set<EventEntity> getJoiningEventUser() {
        return joiningEventUser;
    }

    public UserEntity setJoiningEventUser(Set<EventEntity> joiningEventUser) {
        this.joiningEventUser = joiningEventUser;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity [userID=" + userID + ", username=" + username + ", userEmail=" + userEmail
                + ", userPassword=" + userPassword + ", dOB=" + dOB + ", gender=" + gender + ", phoneNumber="
                + phoneNumber + ", roleName=" + roleName + ", accountStatus=" + accountStatus + ", joiningEventUser="
                + joiningEventUser + "]";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
