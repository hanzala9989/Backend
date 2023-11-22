package com.example.eventOrganizer.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.EventDAO;
import com.example.eventOrganizer.DAO.UserAssignedEventDAO;
import com.example.eventOrganizer.DAO.UserDAO;
import com.example.eventOrganizer.DAOImpl.UserDAOImpl;
import com.example.eventOrganizer.Entity.EventEntity;
import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;
import com.example.eventOrganizer.Service.UserAssignedEventService;

@Service
public class UserAssignedEventServiceImpl implements UserAssignedEventService {
    private static Logger logger = LogManager.getLogger(UserAssignedEventServiceImpl.class);

    @Autowired
    UserAssignedEventDAO userAssignedEventDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserDAOImpl userDAOImpl;

    @Autowired
    EventDAO eventDAO;

    @Override
    public List<UserEntity> getEventsByID(Long eventID) {
        List<UserAssignedEvent> list = userAssignedEventDAO.findUserEventsByEventId(eventID);
        List<Long> userIDs = new ArrayList<Long>();
        if (userIDs.isEmpty()) {
            for (UserAssignedEvent node : list) {
                userIDs.add(node.getUserID());
            }
        } else {
            System.out.println("list is empty");
        }
        List<UserEntity> listofUser = userAssignedEventDAO.findUsersByUserIds(userIDs);
        return listofUser;
    }

    @Override
    public List<UserAssignedEvent> getAllUserAssignedList(Long userID) {
        List<UserAssignedEvent> listofAssignedUser = userAssignedEventDAO.findUsersByUserID(userID);

        return listofAssignedUser;
    }

    @Override
    public List<UserAssignedEvent> getAllUserEventsByID(Long eventID) {
        List<UserAssignedEvent> userAssignedEvent = userAssignedEventDAO.findUserEventsByEventId(eventID);

        return userAssignedEvent;
    }

    @Override
    public boolean deleteUserFromEvent(Long userID, Long eventID) {
        logger.info("UserAssignedEventServiceImpl :: START :: deleteUserFromEvent() ::");
        EventEntity eventEntity = eventDAO.getEvent(eventID);
        userDAOImpl.decrementAssignUserToEvents(eventEntity);
        logger.info("UserAssignedEventServiceImpl :: END :: deleteUserFromEvent() ::");
        return userAssignedEventDAO.deleteUserFromEvent(userID, eventID);
    }

    @Override
    public UserAssignedEvent processUserFromEventService(UserAssignedEvent userAssignedEventEntity) {
        logger.info("UserAssignedEventServiceImpl :: START :: processUserFromEventService() ::");
        UserAssignedEvent e = userAssignedEventDAO.processUserFromEvent(userAssignedEventEntity);
        logger.info("UserAssignedEventServiceImpl :: END :: processUserFromEventService() ::");
        this.addPointReward(e.getUserID());
        return e;
    }

    void addPointReward(Long userID) {
        logger.info("UserAssignedEventServiceImpl :: START :: addPointReward() ::");
        userDAO.addPointRewardToUser(userID);
        logger.info("UserAssignedEventServiceImpl :: END :: addPointReward() ::");
    }

}
