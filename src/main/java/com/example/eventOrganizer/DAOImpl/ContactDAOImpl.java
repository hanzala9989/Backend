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

import com.example.eventOrganizer.DAO.ContactDAO;
import com.example.eventOrganizer.Entity.ContactEntity;

@Repository
public class ContactDAOImpl implements ContactDAO {
    private static Logger logger = LogManager.getLogger(ContactDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public ContactEntity addContact(ContactEntity contactEntity) {
        try {
            logger.info("Adding contactEntity :: {}", contactEntity);
            em.persist(contactEntity);
            logger.info("contactEntity added successfully");
            return contactEntity;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in ContactDAOImpl :: addContact() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding ContactDAOImpl :: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in ContactDAOImpl :: addContact()", e);
            // You may want to rethrow the exception or handle it differently based on your
            // use case
            throw new RuntimeException("Failed to add ContactDAOImpl", e);
        }
    }

    @Override
    @Transactional
    public ContactEntity editContact(ContactEntity contactEntity) {
        try {
            logger.info("ContactEntity :: EDIT :: ", contactEntity);

            // Check if ContactEntity is null
            if (contactEntity == null) {
                logger.error("ContactEntity is null.");
                return null;
            }

            ContactEntity contactObject = em.find(ContactEntity.class, contactEntity.getContactID());

            // Check if contactObject is null
            if (contactObject == null) {
                logger.error("Contact not found for ID: " + contactObject.getUserID());
                return null;
            }

            // Update the contactObject with values from ContactEntity
            contactObject
                    .setContactID(contactEntity.getContactID())
                    .setUserID(contactEntity.getUserID())
                    .setEmail(contactEntity.getEmail())
                    .setSubject(contactEntity.getSubject())
                    .setMessage(contactEntity.getMessage());

            try {
                em.merge(contactObject);
                logger.info("ContactEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in ContactDAOImpl  :: EDIT :: contactObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }
            return contactObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in ContactDAOImpl :: editContact() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in ContactDAOImpl :: editContact() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }
    }

    @Override
    public ContactEntity getContact(Long contactID) {
        logger.info("contactID :: GET :: " + contactID);
        try {
            List<ContactEntity> listContact = this.getAllContact();
            ContactEntity contactObject = listContact.stream()
                    .filter(contact -> contactID == contact.getContactID())
                    .findFirst()
                    .orElse(null);
            return contactObject;

        } catch (NoResultException nre) {
            logger.info("NoResultException in ContactDAOImpl :: getContact() ::", nre);
            return null; // Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in ContactDAOImpl :: getContact() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in ContactDAOImpl :: getContact() ::", e);
            throw new RuntimeException("An error occurred while fetching Contact", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteContact(Long contactID) {
        logger.info("contactID :: GET :: ", contactID);
        try {
            ContactEntity contactObject = em.find(ContactEntity.class, contactID);
            if (contactObject != null) {
                em.remove(contactObject);
                logger.info("Contact deleted successfully.");
                return true;

            } else {
                logger.warn("Contact with ID {} not found.", contactID);
                return false;
            }

        } catch (NoResultException nre) {
            logger.info("NoResultException in ContactDAOImpl :: getContact() ::", nre);
            throw nre;// Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in ContactDAOImpl :: getContact() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in ContactDAOImpl :: getContact() ::", e);
            throw new RuntimeException("An error occurred while fetching Contact", e);
        }
    }

    @Override
    public List<ContactEntity> getAllContact(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from contact_master LIMIT " + pageSize + " OFFSET " + firstResult);
            query = em.createNativeQuery(builder.toString(), ContactEntity.class);
            logger.info("ContactEntity :: GETALL :: {}", builder.toString());
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in ContactDAOImpl :: getAllContact()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in ContactDAOImpl :: getAllContact()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    public List<ContactEntity> getAllContact() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from contact_master;");
            logger.info("getAllContact() :: GETALL :: {}", builder.toString());
    
            query = em.createNativeQuery(builder.toString(), ContactEntity.class);
            List<ContactEntity> resultList = query.getResultList();
            return resultList;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in ContactDAOImpl :: getAllContact()", e);
            throw e;
        } catch (Exception e) {
            logger.error("Exception in ContactDAOImpl :: getAllContact()", e);
            return Collections.emptyList();
        }
    }
    

    public List<ContactEntity> filterContactByAttributesDAO(ContactEntity filterParams) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<ContactEntity> query = criteriaBuilder.createQuery(ContactEntity.class);
        Root<ContactEntity> root = query.from(ContactEntity.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with "AND" operation

        if (filterParams.getUserID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("userID"), filterParams.getUserID()));
        }

        if (filterParams.getEmail() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("email"), filterParams.getEmail()));
        }

        if (filterParams.getSubject() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("subject"), filterParams.getSubject()));
        }

        if (filterParams.getMessage() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("message"), filterParams.getMessage()));
        }
        // Repeat the above pattern for other filter attributes
        query.where(predicate);

        return em.createQuery(query).getResultList();
    }
}
