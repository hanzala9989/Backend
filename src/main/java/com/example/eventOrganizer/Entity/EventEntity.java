package com.example.eventOrganizer.Entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private Long eventID;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_date")
    private String eventDate;

    @Column(name = "event_venue")
    private String eventVenue;

    @Column(name = "number_of_guests")
    private int numberOfGuests;

    @Column(name = "reward_point")
    private int rewardPoint = 100;

    @Column(name = "number_Of_User_Register")
    private int numberOfUserRegister = 0;

    @Column(name = "event_last_Update_Timestamp")
    LocalDateTime lastUpdateTimestamp = LocalDateTime.now();

    @JsonIgnore
    @ManyToMany(mappedBy = "joiningEventUser")
    private Set<UserEntity> user = new HashSet<>();

    public EventEntity() {
    }

    public EventEntity(Long eventID, String eventName, String eventDate, String eventVenue, int numberOfGuests,
            int rewardPoint, int numberOfUserRegister, LocalDateTime lastUpdateTimestamp, Set<UserEntity> user) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.numberOfGuests = numberOfGuests;
        this.rewardPoint = rewardPoint;
        this.numberOfUserRegister = numberOfUserRegister;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.user = user;
    }



    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Set<UserEntity> getUser() {
        return user;
    }

    public void setUser(Set<UserEntity> user) {
        this.user = user;
    }

    public int getNumberOfUserRegister() {
        return numberOfUserRegister;
    }

    public void setNumberOfUserRegister(int numberOfUserRegister) {
        this.numberOfUserRegister = numberOfUserRegister;
    }



    @Override
    public String toString() {
        return "EventEntity [eventID=" + eventID + ", eventName=" + eventName + ", eventDate=" + eventDate
                + ", eventVenue=" + eventVenue + ", numberOfGuests=" + numberOfGuests + ", rewardPoint=" + rewardPoint
                + ", numberOfUserRegister=" + numberOfUserRegister + ", lastUpdateTimestamp=" + lastUpdateTimestamp
                + ", user=" + user + "]";
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

}
