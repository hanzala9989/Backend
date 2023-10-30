package com.example.eventOrganizer.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.UserAssignedEventDAO;
import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;
import com.example.eventOrganizer.Service.UserAssignedEventService;
@Service
public class UserAssignedEventServiceImpl implements UserAssignedEventService {
    @Autowired
    UserAssignedEventDAO userAssignedEventDAO;
    @Override
    public List<UserEntity> getEventsByID(Long eventID) {
        List<UserAssignedEvent> list = userAssignedEventDAO.findUserEventsByEventId(eventID);
        List<Long> userIDs = new ArrayList<>();
        for (UserAssignedEvent node : list) {
            userIDs.add(node.getUserID());
        }
        System.out.println("list :::::::::::::::::: " + userIDs); 
        List<UserEntity> listofUser= userAssignedEventDAO.findUsersByUserIds(userIDs);
        return listofUser;
    }
    @Override
    public boolean deleteUserFromEvent(Long userID, Long eventID) {
        return userAssignedEventDAO.deleteUserFromEvent(userID,eventID);
    }
    @Override
    public UserAssignedEvent processUserFromEventService(UserAssignedEvent userAssignedEventEntity) {
        return userAssignedEventDAO.processUserFromEvent(userAssignedEventEntity);
    }



}
