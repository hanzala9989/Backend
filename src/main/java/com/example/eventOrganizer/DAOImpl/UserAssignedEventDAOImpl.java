package com.example.eventOrganizer.DAOImpl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.example.eventOrganizer.DAO.UserAssignedEventDAO;
import com.example.eventOrganizer.Entity.EventEntity;
import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;

@Repository
public class UserAssignedEventDAOImpl implements UserAssignedEventDAO {

    private static Logger logger = LogManager.getLogger(UserAssignedEventDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<UserAssignedEvent> findUserEventsByEventId(Long eventId) {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("SELECT * FROM user_event WHERE event_id = " + eventId);
            query = em.createNativeQuery(builder.toString(), UserAssignedEvent.class);
            logger.info("users :: GETALL :: {}", builder);
            return query.getResultList();
        } catch (NullPointerException e) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: findUserEventsByEventId()");
            throw e;
        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: findUserEventsByEventId()");
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<UserEntity> findUsersByUserIds(List<Long> userIds) {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            String userIdsString = String.join(",", userIds.stream().map(String::valueOf).collect(Collectors.toList()));
            builder.append("SELECT * FROM users WHERE user_id IN (" + userIdsString + ")");
            query = em.createNativeQuery(builder.toString(), UserEntity.class);
            logger.info("users :: GETALL :: {}", builder);
            return query.getResultList();
        } catch (NullPointerException e) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: findUsersByUserIds()");
            throw e;
        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: findUsersByUserIds()");
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public boolean deleteUserFromEvent(Long userID, Long eventID) {
        logger.info("UserAssignedEventDAOImpl :: DELETE :: getUserFromEvent() :: userID :: " + userID + " :: eventID ::"
                + eventID);

        try {
            boolean userFlag = this.getUserFromEvent(userID, eventID);
            if (userFlag == false) {
                return false;
            }
            String queryString = "DELETE FROM user_event WHERE user_id = " + userID + " AND event_id = " + eventID;
            em.createNativeQuery(queryString)
                    .executeUpdate();
            logger.info("users :: GETALL :: getUserFromEvent()", queryString);
            return true;

        } catch (NoResultException nre) {
            logger.info("NoResultException in UserAssignedEventDAOImpl :: deleteUserFromEvent() ::");
            throw nre;// Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: deleteUserFromEvent() ::");
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: deleteUserFromEvent() ::");
            throw new RuntimeException("An error occurred while fetching Event");
        }
    }

    @Transactional
    public boolean getUserFromEvent(Long userID, Long eventID) {
        logger.info("UserAssignedEventDAOImpl :: GET :: :: getUserFromEvent() :: userID :: " + userID + ":: eventID ::"
                + eventID);
        try {
            String queryString = "SELECT * FROM user_event WHERE user_id = " + userID + " AND event_id = " + eventID;
            em.createNativeQuery(queryString, UserAssignedEvent.class).getSingleResult();
            logger.info("users :: GETALL :: {}", queryString);
            return true;

        } catch (NoResultException nre) {
            logger.error("NoResultException in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return false;
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return false;

        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return false;
        }

    }

    public UserAssignedEvent getUserFromEventObject(Long userID, Long eventID) {
        logger.info("UserAssignedEventDAOImpl :: GET :: :: getUserFromEvent() :: userID :: " + userID + ":: eventID ::"
                + eventID);
        try {
            String queryString = "SELECT * FROM user_event WHERE user_id = " + userID + " AND event_id = " + eventID;
            em.createNativeQuery(queryString, UserAssignedEvent.class).getSingleResult();
            logger.info("users :: GETALL :: {}", queryString);
            return (UserAssignedEvent) em.createNativeQuery(queryString, UserAssignedEvent.class).getSingleResult();

        } catch (NoResultException nre) {
            logger.error("NoResultException in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return null;
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return null;

        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: getUserFromEvent() ::");
            return null;
        }

    }

    @Override
    @Transactional
    public UserAssignedEvent 
    processUserFromEvent(UserAssignedEvent userAssignedEventEntity) {
        try {
            logger.info("processUserFrom() :: EDIT :: ", userAssignedEventEntity);
            // Check if EventEntity is null
            if (userAssignedEventEntity == null) {
                logger.error("EventEntity is null.");
                return null;
            }
            UserAssignedEvent userAssignedEventObject = getUserFromEventObject(userAssignedEventEntity.getUserID(),
                    userAssignedEventEntity.getEventID());

            userAssignedEventObject.setUserID(userAssignedEventEntity.getUserID());
            userAssignedEventObject.setEventID(userAssignedEventEntity.getEventID());
            userAssignedEventObject.setAttendend(userAssignedEventEntity.getAttendend());
            userAssignedEventObject.setStatus(userAssignedEventEntity.isStatus());

            try {
                em.merge(userAssignedEventObject);
                logger.info("processUserFromEvent updated successfully.");
            } catch (Exception e) {
                logger.error("processUserFromEvent in UserAssignedEventDAOImpl  :: EDIT :: EventObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }
            return userAssignedEventObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in UserAssignedEventDAOImpl :: processUserFromEvent() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in UserAssignedEventDAOImpl :: processUserFromEvent() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }
    }

}
