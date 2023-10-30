package com.example.eventOrganizer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "message")
    // @NotEmpty(message = "Message is Required !!")
    private String message;

    public ContactEntity() {
    }

    public ContactEntity(Long contactID, Long userID, String email, String subject, String message) {
        this.contactID = contactID;
        this.userID = userID;
        this.email = email;
        this.subject = subject;
        this.message = message;
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

    @Override
    public String toString() {
        return "ContactEntity [contactID=" + contactID + ", UserID=" + userID + ", Email=" + email + ", Subject="
                + subject + ", Message=" + message + "]";
    }

}
