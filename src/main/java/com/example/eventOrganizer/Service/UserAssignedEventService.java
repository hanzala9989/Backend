package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;

public interface UserAssignedEventService {
    public List<UserEntity> getEventsByID(Long eventID);

    public List<UserAssignedEvent> getAllUserEventsByID(Long eventID);

    public boolean deleteUserFromEvent(Long userID, Long eventID);

    public UserAssignedEvent processUserFromEventService(UserAssignedEvent userAssignedEventEntity);

    public List<UserAssignedEvent> getAllUserAssignedList(Long userID);

}
