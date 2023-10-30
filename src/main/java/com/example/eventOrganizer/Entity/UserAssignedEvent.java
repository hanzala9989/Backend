package com.example.eventOrganizer.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "user_event")
public class UserAssignedEvent {
    @Id
    @Column(name = "user_id")
    private Long userID;

    @Column(name = "event_id")
    private Long eventID;

    @Column(name = "user_attendend")
    private Boolean  attendend;

    @Column(name = "status")
    private Boolean  status;

    @Column(name = "user_last_Update_Timestamp")
    LocalDateTime lastUpdateTimestamp = LocalDateTime.now();

    public UserAssignedEvent() {
    }

    public UserAssignedEvent(Long userID, Long eventID, Boolean  attendend, Boolean  status,
            LocalDateTime lastUpdateTimestamp) {
        this.userID = userID;
        this.eventID = eventID;
        this.attendend = attendend;
        this.status = status;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public void setAttendend(Boolean  attendend) {
        this.attendend = attendend;
    }

    public void setStatus(Boolean  status) {
        this.status = status;
    }

    public Long getUserID() {
        return userID;
    }

    public Long getEventID() {
        return eventID;
    }

    public Boolean  getAttendend() {
        return attendend;
    }

    public Boolean  isStatus() {
        return status;
    }
}
