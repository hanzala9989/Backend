package com.example.eventOrganizer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "contact_master")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_id")
    private Long contactID;

    @Column(name = "user_id")
    // @NotEmpty()
    private Long userID;

    @Column(name = "email")
    // @NotEmpty
    // @Email(message = "Email is not Valid !!")
    private String email;

    @Column(name = "subject")
    // @NotEmpty(message = "Subject is Required !!")
    private String subject;

    @Column(name = "message", columnDefinition = "TEXT")
    // @NotEmpty(message = "Message is Required !!")
    private String message;
    @Transient
    private String role;

    public ContactEntity() {
    }



    public ContactEntity(Long contactID, Long userID, String email, String subject, String message, String role) {
        this.contactID = contactID;
        this.userID = userID;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.role = role;
    }



    public ContactEntity setContactID(Long contactID) {
        this.contactID = contactID;
        return this;
    }

    public ContactEntity setUserID(Long userID) {
        this.userID = userID;
        return this;

    }

    public ContactEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public ContactEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public Long getContactID() {
        return contactID;
    }

    public Long getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "ContactEntity [contactID=" + contactID + ", userID=" + userID + ", email=" + email + ", subject="
                + subject + ", message=" + message + ", role=" + role + "]";
    }

    
}
