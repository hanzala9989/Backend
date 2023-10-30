package com.example.eventOrganizer.DAOImpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.example.eventOrganizer.DAO.EventDAO;
import com.example.eventOrganizer.Entity.EventEntity;

@Repository
public class EventDAOImpl implements EventDAO {

    private static Logger logger = LogManager.getLogger(EventDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public EventEntity addEvent(EventEntity EventEntity) {
        try {
            logger.info("Adding EventEntity :: {}", EventEntity);
            em.persist(EventEntity);
            logger.info("EventEntity added successfully");
            return EventEntity;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in EventDAOImpl :: addEvent() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding EventDAOImpl :: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in EventDAOImpl :: addEvent()", e);
            // You may want to rethrow the exception or handle it differently based on your
            // use case
            throw new RuntimeException("Failed to add EventDAOImpl", e);
        }
    }

    @Override
    @Transactional
    public EventEntity editEvent(EventEntity eventEntity) {
        try {
            logger.info("EventEntity :: EDIT :: ", eventEntity);
            // Check if EventEntity is null
            if (eventEntity == null) {
                logger.error("EventEntity is null.");
                return null;
            }
            EventEntity eventObject = em.find(EventEntity.class, eventEntity.getEventID());

            eventObject.setEventName(eventEntity.getEventName());
            eventObject.setEventVenue(eventEntity.getEventVenue());
            eventObject.setNumberOfGuests(eventEntity.getNumberOfGuests());
            eventObject.setEventDate(eventEntity.getEventDate());
            eventObject.setUser(eventEntity.getUser());

            try {
                em.merge(eventObject);
                logger.info("EventEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in EventDAOImpl  :: EDIT :: EventObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }
            return eventObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in EventDAOImpl :: editEvent() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in EventDAOImpl :: editEvent() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }
    }

    @Override
    public EventEntity getEvent(Long eventID) {
        logger.info("EventID :: GET :: " + eventID);
        try {
            EventEntity eventObject = em.find(EventEntity.class, eventID);
            return eventObject;

        } catch (NoResultException nre) {
            logger.info("NoResultException in EventDAOImpl :: getEvent() ::", nre);
            return null; // Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in EventDAOImpl :: getEvent() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in EventDAOImpl :: getEvent() ::", e);
            throw new RuntimeException("An error occurred while fetching Event", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteEvent(Long eventID) {
        logger.info("EventID :: GET :: ", eventID);
        try {
            EventEntity eventObject = em.find(EventEntity.class, eventID);
            if (eventObject != null) {
                em.remove(eventObject);
                logger.info("Event deleted successfully.");
                return true;

            } else {
                logger.warn("Event with ID {} not found.", eventID);
                return false;
            }

        } catch (NoResultException nre) {
            logger.info("NoResultException in EventDAOImpl :: getEvent() ::", nre);
            throw nre;// Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in EventDAOImpl :: getEvent() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in EventDAOImpl :: getEvent() ::", e);
            throw new RuntimeException("An error occurred while fetching Event", e);
        }
    }

    @Override
    public List<EventEntity> getAllEvent(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from events LIMIT " + pageSize + " OFFSET " + firstResult);
            query = em.createNativeQuery(builder.toString(), EventEntity.class);
            logger.info("EventEntity :: GETALL :: {}", builder.toString());
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in EventDAOImpl :: getAllEvent()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in EventDAOImpl :: getAllEvent()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }
   @Override
    public List<EventEntity> getAllEvent() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from events;");
            logger.info("getAllEvent() :: GETALL :: {}", builder.toString());

            query = em.createNativeQuery(builder.toString(), EventEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            logger.error("NullPointerException in EventDAOImpl :: getAllEvent()", e);
            throw e;
        } catch (Exception e) {
            logger.error("Exception in EventDAOImpl :: getAllEvent()", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<EventEntity> filterEventByAttributesDAO(EventEntity filterParams) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<EventEntity> query = criteriaBuilder.createQuery(EventEntity.class);
        Root<EventEntity> root = query.from(EventEntity.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with "AND" operation

        if (filterParams.getEventID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("eventID"), filterParams.getEventID()));
        }

        if (filterParams.getEventName() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("eventName"), filterParams.getEventName()));
        }

        if (filterParams.getEventDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("eventDate"), filterParams.getEventDate()));
        }

        if (filterParams.getEventVenue() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("eventVenue"), filterParams.getEventVenue()));
        }

        if (filterParams.getEventDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("eventDate"), filterParams.getEventDate()));
        }

        // Repeat the above pattern for other filter attributes
        query.where(predicate);

        return em.createQuery(query).getResultList();
    }

}
