package com.example.eventOrganizer.DAOImpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.example.eventOrganizer.DAO.LocationDAO;
import com.example.eventOrganizer.Entity.LocationEntity;



@Repository
public class LocationDAOImpl implements LocationDAO {

    private static Logger logger = LogManager.getLogger(LocationDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public LocationEntity addLocation(LocationEntity locationEntity) {
        try {
            logger.info("Adding locationEntity :: {}", locationEntity);
            em.persist(locationEntity);

            logger.info("LocationEntity added successfully");
            return locationEntity;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding LocationEntity :: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in LocationDAOImpl :: addLocation()", e);
            // You may want to rethrow the exception or handle it differently based on your
            // use case
            throw new RuntimeException("Failed to add locationEntity", e);
        }
    }

    @Override
    public LocationEntity editLocation(LocationEntity locationEntity) {
        try {
            logger.info("locationEntity :: EDIT :: ", locationEntity);

            // Check if locationEntity is null
            if (locationEntity == null) {
                logger.error("locationEntity is null.");
                return null;
            }

            LocationEntity locationObject = this.getLocation(locationEntity.getLocationID());

            // Check if locationObject is null
            if (locationObject == null) {
                logger.error("Location not found for ID: " + locationEntity.getLocationID());
                return null;
            }

            // Update the locationObject with values from locationEntity
            locationObject
                    .setName(locationEntity.getName())
                    .setAddress(locationEntity.getAddress())
                    .setLatitude(locationEntity.getLatitude())
                    .setLongitude(locationEntity.getLongitude())
                    .setDescription(locationEntity.getDescription());

            try {
                em.merge(locationObject);
                logger.info("LocationEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in LocationDAOImpl  :: EDIT :: locationObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }

            return locationObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in LocationDAOImpl :: editLocation() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in LocationDAOImpl :: editLocation() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }
    }

    @Override
    public LocationEntity getLocation(Long locationID) {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        logger.info("locationID :: GET :: " + locationID);

        try {
            builder.append("select * from locations where location_id=:locationID");
            logger.info("locationID :: GET :: " + builder);
            query = em.createNativeQuery(builder.toString(), LocationEntity.class).setParameter("locationID",
                    locationID);
            return (LocationEntity) query.getSingleResult();
        } catch (NoResultException nre) {
            logger.info("NoResultException in LocationDAOImpl :: getLocation() ::", nre);
            return null; // Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in LocationDAOImpl :: getLocation() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in LocationDAOImpl :: getLocation() ::", e);
            throw new RuntimeException("An error occurred while fetching location", e);
        }
    }

    @Override
    public boolean deleteLocation(Long locationID) {
        try {
            logger.info("Deleting location with ID: {}", locationID);

            LocationEntity locationEntity = em.find(LocationEntity.class, locationID);
            if (locationEntity != null) {
                em.remove(locationEntity); // Delete the entity from the database
                logger.info("Location deleted successfully.");
                return true;
            } else {
                logger.warn("Location with ID {} not found.", locationID);
                return false;
            }
        } catch (EntityNotFoundException e) {
            logger.warn("Entity not found for ID: {}", locationID, e);
            return false;
        } catch (Exception e) {
            logger.error("Exception in LocationDAOImpl::deleteLocation()", e);
            return false;
        }
    }

    @Override
    public List<LocationEntity> getAllLocation() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from location");
            logger.info("locationID :: GETALL :: {}", builder); // Use {} to format the log message
            query = em.createNativeQuery(builder.toString(), LocationEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in LocationDAOImpl :: getAllLocation()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in LocationDAOImpl :: getAllLocation()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

}
