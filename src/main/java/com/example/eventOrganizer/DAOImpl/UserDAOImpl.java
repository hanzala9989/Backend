package com.example.eventOrganizer.DAOImpl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.eventOrganizer.DAO.EventDAO;
import com.example.eventOrganizer.DAO.UserDAO;
import com.example.eventOrganizer.Entity.EventEntity;
import com.example.eventOrganizer.Entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.persistence.criteria.Predicate;

@Repository
public class UserDAOImpl implements UserDAO {

    private static Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    EventDAO eventDAO;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    @Transactional
    public UserEntity addUser(UserEntity userEntity) {
        try {
            logger.info("Adding userEntity :: {}", userEntity);
            em.persist(userEntity);
            logger.info("userEntity added successfully");
            return userEntity;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in UserDAOImpl :: addUser() ::", e);
            return null;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding userEntity :: {}", e.getMessage(), e);
            return null;

        } catch (Exception e) {
            logger.error("Exception in UserDAOImpl :: addUser()", e);
            throw new RuntimeException("Failed to add userEntity", e);
        }
    }

    @Override
    @Transactional
    public UserEntity editUser(UserEntity userEntity) {
        try {
            logger.info("userEntity :: EDIT :: ", userEntity);

            if (userEntity == null) {
                logger.error("userEntity is null.");
                return null;
            }

            UserEntity userObject = em.find(UserEntity.class, userEntity.getUserID());

            if (userObject == null) {
                logger.error("User not found for ID: " + userEntity.getUserID());
                return null;
            }


            userObject.getJoiningEventUser().forEach(ele->{
                logger.error("getJoiningEventUser ID: " + ele);
            });


            userObject
                    .setUserID(userEntity.getUserID())
                    .setUsername(userEntity.getUsername())
                    .setUserEmail(userEntity.getUserEmail())
                    .setdOB(userEntity.getdOB())
                    .setGender(userEntity.getGender())
                    .setPhoneNumber(userEntity.getPhoneNumber())
                    .setUserPassword(userEntity.getUserPassword())
                    .setAccountStatus(userEntity.getAccountStatus());
            try {
                em.merge(userObject);
                logger.info("userEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in userEntity  :: EDIT :: userObject :: ", e);
                return null;
            }
            return userObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in userEntity :: editUser() ::", e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in userEntity :: editUser() ::", e);
            return null;
        }
    }

    @Override
    public UserEntity getUser(Long userID) {
        logger.info("userID :: GET :: " + userID);
        try {
            UserEntity UserEntity = em.find(UserEntity.class, userID);
            return UserEntity;
        } catch (NoResultException nre) {
            logger.info("NoResultException in UserDAOImpl :: getUser() ::", nre);
            return null;
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in UserDAOImpl :: getUser() ::", npe);
            throw npe;
        } catch (Exception e) {
            logger.error("Exception in UserDAOImpl :: getUser() ::", e);
            throw new RuntimeException("An error occurred while fetching User", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userID) {
        try {
            logger.info("Deleting User with ID: {}", userID);

            UserEntity UserEntity = em.find(UserEntity.class, userID);
            if (UserEntity != null) {
                em.remove(UserEntity); // Delete the entity from the database
                logger.info("User deleted successfully.");
                return true;
            } else {
                logger.warn("User with ID {} not found.", userID);
                return false;
            }
        } catch (EntityNotFoundException e) {
            logger.warn("Entity not found for ID: {}", userID, e);
            return false;
        } catch (Exception e) {
            logger.error("Exception in UserDAOImpl::deleteUser()", e);
            return false;
        }
    }

    @Override
    public List<UserEntity> getAllUser(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from users LIMIT " + pageSize + " OFFSET " + firstResult);
            logger.info("users :: GETALL :: {}", builder); // Use {} to format the log message
            query = em.createNativeQuery(builder.toString(), UserEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in UserDAOImpl :: getAllUser()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in UserDAOImpl :: getAllUser()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    public List<UserEntity> filterUsersByAttributesDAO(UserEntity filterParams) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with "AND" operation

        if (filterParams.getUserID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("userID"), filterParams.getUserID()));
        }

        if (filterParams.getUsername() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("username"), filterParams.getUsername()));
        }

        if (filterParams.getUserEmail() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("userEmail"), filterParams.getUserEmail()));
        }

        if (filterParams.getRoleName() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("roleName"), filterParams.getRoleName()));
        }

        if (filterParams.getdOB() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("dOB"), filterParams.getdOB()));
        }

        if (filterParams.getGender() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("gender"), filterParams.getGender()));
        }

        if (filterParams.getAccountStatus() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("accountStatus"), filterParams.getAccountStatus()));
        }

        if (filterParams.getUserPassword() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("phoneNumber"), filterParams.getUserPassword()));
        }

        // Repeat the above pattern for other filter attributes

        query.where(predicate);

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<UserEntity> getAllUser() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from users;");
            logger.info("users :: GETALL :: {}", builder); // Use {} to format the log message
            query = em.createNativeQuery(builder.toString(), UserEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in UserDAOImpl :: getAllUser()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in UserDAOImpl :: getAllUser()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    @Override
    public String assignUserToEvents(Long userID, Long eventID) {
        Set<EventEntity> eventSet = null;
        UserEntity user = em.find(UserEntity.class, userID);
        EventEntity event = em.find(EventEntity.class, eventID);
        Boolean foundEvent = user.getJoiningEventUser()
                .stream()
                .filter(eventObject -> eventObject.getEventID() == eventID)
                .findFirst().isPresent();

        if (foundEvent) {
            logger.error("Exception in UserDAOImpl :: assignUserToEvents() :: You Have Already Registrated",
                    foundEvent);
            return "You Have Already Registrated !";
        }
        if (event.getNumberOfUserRegister() < event.getNumberOfGuests()) {
            incrementAssignUserToEvents(event);
            eventSet = user.getJoiningEventUser();
            eventSet.add(event);
            user.setJoiningEventUser(eventSet);
            em.merge(user);
            return "Registration Done successfully Please Wait For Approvel !";
        } else {
            return "The Volunteer Limit For This Event Has Been Exceeded !";
        }
    }

    public void incrementAssignUserToEvents(EventEntity event) {
        event.setNumberOfUserRegister(event.getNumberOfUserRegister() + 1);
        em.merge(event);
    }

    @Override
    public UserEntity findByUsername(String username) {
        logger.info("userID :: GET :: " + username);
        try {
            UserEntity UserEntity = em.find(UserEntity.class, username);
            return UserEntity;
        } catch (NoResultException nre) {
            logger.info("NoResultException in UserDAOImpl :: getUser() ::", nre);
            return null;
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in UserDAOImpl :: getUser() ::", npe);
            throw npe;
        } catch (Exception e) {
            logger.error("Exception in UserDAOImpl :: getUser() ::", e);
            throw new RuntimeException("An error occurred while fetching User", e);
        }
    }



}
