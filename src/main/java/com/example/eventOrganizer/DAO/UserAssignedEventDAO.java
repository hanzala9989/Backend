package com.example.eventOrganizer.DAO;

import java.util.List;

import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;

public interface UserAssignedEventDAO {

    public List<UserAssignedEvent> findUserEventsByEventId(Long eventId);

    public List<UserEntity> findUsersByUserIds(List<Long> userIds);

    public List<UserAssignedEvent> findUsersByUserID(Long userID);

    public boolean deleteUserFromEvent(Long userID, Long eventID);

    public boolean deleteUserAllAssignedEvent(Long userID);

    public UserAssignedEvent processUserFromEvent(UserAssignedEvent userAssignedEventEntity);

}
