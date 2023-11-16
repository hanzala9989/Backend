package com.example.eventOrganizer.DAOImpl;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.example.eventOrganizer.DAO.RoleDAO;
import com.example.eventOrganizer.Entity.RoleEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private static Logger logger = LogManager.getLogger(RoleDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public RoleEntity addRole(RoleEntity roleEntity) {
        try {
            logger.info("Adding roleEntity :: {}", roleEntity);
            em.persist(roleEntity);

            logger.info("roleEntity added successfully");
            return roleEntity;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in RoleDAOImpl :: addRole() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding roleEntity :: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in RoleEntity :: addRole()", e);
            // You may want to rethrow the exception or handle it differently based on your
            // use case
            throw new RuntimeException("Failed to add roleEntity", e);
        }
    }

    @Override
    public RoleEntity editRole(RoleEntity roleEntity) {
        try {
            logger.info("roleEntity :: EDIT :: ", roleEntity);

            // Check if roleEntity is null
            if (roleEntity == null) {
                logger.error("roleEntity is null.");
                return null;
            }

            RoleEntity roleObject = em.find(RoleEntity.class, roleEntity.getRoleID());

            // Check if roleObject is null
            if (roleObject == null) {
                logger.error("Role not found for ID: " + roleEntity.getRoleID());
                return null;
            }

            // Update the roleObject with values from roleEntity
            roleObject
                    .setRoleID(roleEntity.getRoleID())
                    .setRoleName(roleEntity.getRoleName())
                    .setDescription(roleEntity.getDescription());

            try {
                em.merge(roleObject);
                logger.info("roleEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in RoleEntity  :: EDIT :: roleObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }

            return roleObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in RoleEntity :: editRole() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in RoleEntity :: editRole() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }
    }

    @Override
    public RoleEntity getRole(Long roleID) {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        logger.info("roleID :: GET :: " + roleID);
        try {
            builder.append("select * from roles where role_id= " + roleID);
            logger.info("roleID :: GET :: " + builder);
            query = em.createNativeQuery(builder.toString(), RoleEntity.class);
            return (RoleEntity) query.getSingleResult();
        } catch (NoResultException nre) {
            logger.info("NoResultException in RoleDAOImpl :: getRole() ::", nre);
            return null; // Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in RoleDAOImpl :: getRole() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in RoleDAOImpl :: getRole() ::", e);
            throw new RuntimeException("An error occurred while fetching Role", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteRole(Long roleID) {
        try {
            logger.info("Deleting Role with ID :: ", roleID);

            RoleEntity RoleEntity = em.find(RoleEntity.class, roleID);
            if (RoleEntity != null) {
                em.remove(RoleEntity); // Delete the entity from the database
                logger.info("Role deleted successfully.");
                return true;
            } else {
                logger.warn("Role with ID {} not found.", roleID);
                return false;
            }
        } catch (EntityNotFoundException e) {
            logger.warn("Entity not found for ID ::", e);
            return false;
        } catch (Exception e) {
            logger.error("Exception in RoleDAOImpl :: deleteRole()", e);
            return false;
        }
    }

    @Override
    public List<RoleEntity> getAllRole(int pageNumber, int pageSize) {
        int firstResult = (pageNumber - 1) * pageSize;
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from roles;");
            // builder.append("select * from roles LIMIT " + pageSize + " OFFSET " + firstResult);
            logger.info("roles :: GETALL :: {}", builder); // Use {} to format the log message
            query = em.createNativeQuery(builder.toString(), RoleEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in RoleDAOImpl :: getAllRole()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in RoleDAOImpl :: getAllRole()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    public List<RoleEntity> filterRoleByAttributesDAO(RoleEntity filterParams) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<RoleEntity> query = criteriaBuilder.createQuery(RoleEntity.class);
        Root<RoleEntity> root = query.from(RoleEntity.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with "AND" operation

        if (filterParams.getRoleID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("roleID"), filterParams.getRoleID()));
        }

        if (filterParams.getRoleName() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("roleName"), filterParams.getRoleName()));
        }

        if (filterParams.getDescription() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("description"), filterParams.getDescription()));
        }
        // Repeat the above pattern for other filter attributes
        query.where(predicate);

        return em.createQuery(query).getResultList();
    }

}
