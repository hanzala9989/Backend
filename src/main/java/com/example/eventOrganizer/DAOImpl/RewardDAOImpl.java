package com.example.eventOrganizer.DAOImpl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.eventOrganizer.DAO.RewardDAO;
import com.example.eventOrganizer.Entity.LeaderBoardEntity;
import com.example.eventOrganizer.Entity.RewardEntity;
import com.example.eventOrganizer.ModelDTO.LeaderBoardModel;

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
public class RewardDAOImpl implements RewardDAO {

    private static Logger logger = LogManager.getLogger(RewardDAOImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public RewardEntity addReward(RewardEntity rewardEntity) {
        try {
            logger.info("Adding rewardEntity :: {}", rewardEntity);
            em.persist(rewardEntity);
            logger.info("rewardEntity added successfully");
            return rewardEntity;
        } catch (NullPointerException e) {
            logger.error("NullPointerException in RewardDAOImpl :: addReward() ::", e);
            return null;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            logger.error("Exception while adding RewardDAOImpl :: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Exception in RewardDAOImpl :: addContact()", e);
            throw new RuntimeException("Failed to add RewardDAOImpl", e);
        }
    }

    @Override
    @Transactional
    public RewardEntity editReward(RewardEntity rewardEntity) {
        try {
            logger.info("rewardEntity :: EDIT :: ", rewardEntity);

            // Check if rewardEntity is null
            if (rewardEntity == null) {
                logger.error("rewardEntity is null.");
                return null;
            }

            RewardEntity rewardObject = em.find(RewardEntity.class, rewardEntity.getRewardID());

            // Check if rewardObject is null
            if (rewardObject == null) {
                logger.error("Reward not found for ID: " + rewardEntity.getRewardID());
                return null;
            }

            // Update the rewardObject with values from rewardEntity
            rewardObject
                    .setRewardID(rewardEntity.getRewardID())
                    .setUserID(rewardEntity.getUserID())
                    .setExpirationDate(rewardEntity.getExpirationDate())
                    .setRewardAvailability(rewardEntity.getRewardAvailability())
                    .setRewardPoint(rewardEntity.getRewardPoint())
                    .setRewardType(rewardEntity.getRewardType());

            try {
                em.merge(rewardObject);
                logger.info("rewardEntity updated successfully.");
            } catch (Exception e) {
                logger.error("Exception in RewardDAOImpl  :: EDIT :: rewardObject :: ", e);
                // Handle the exception and possibly log it
                return null;
            }

            return rewardObject;

        } catch (NullPointerException e) {
            logger.error("NullPointerException in RewardEntity :: editReward() ::", e);
            // Handle the exception and possibly log it
            return null;
        } catch (Exception e) {
            logger.error("Exception in RewardEntity :: editReward() ::", e);
            // Handle other exceptions and possibly log them
            return null;
        }

    }

    @Override
    public RewardEntity getReward(Long rewardID) {

        logger.info("rewardID :: GET :: " + rewardID);
        try {
            List<RewardEntity> listReward = this.getAllReward();
            RewardEntity rewardObject = listReward.stream()
                    .filter(rewardObj -> rewardID == rewardObj.getRewardID())
                    .findFirst()
                    .orElse(null);
            return rewardObject;

        } catch (NoResultException nre) {
            logger.info("NoResultException in RewardDAOImpl :: getReward() ::", nre);
            return null; // Handle the case where no results are found
        } catch (NullPointerException npe) {
            logger.error("NullPointerException in RewardDAOImpl :: getReward() ::", npe);
            throw npe; // Rethrow NullPointerException to indicate a critical error
        } catch (Exception e) {
            logger.error("Exception in RewardDAOImpl :: getReward() ::", e);
            throw new RuntimeException("An error occurred while fetching Reward", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteReward(Long rewardID) {
        try {
            logger.info("Deleting Reward with ID: {}", rewardID);

            RewardEntity RewardEntity = em.find(RewardEntity.class, rewardID);
            if (RewardEntity != null) {
                em.remove(RewardEntity); // Delete the entity from the database
                logger.info("Reward deleted successfully.");
                return true;
            } else {
                logger.warn("Reward with ID {} not found.", rewardID);
                return false;
            }
        } catch (EntityNotFoundException e) {
            logger.warn("Entity not found for ID: {}", rewardID, e);
            return false;
        } catch (Exception e) {
            logger.error("Exception in RewardDAOImpl::deleteReward()", e);
            return false;
        }
    }

    @Override
    public List<RewardEntity> getAllReward(int pageSize, int pageNumber) {
        int firstResult = (pageNumber - 1) * pageSize;

        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from rewards LIMIT " + pageSize + " OFFSET " + firstResult);
            query = em.createNativeQuery(builder.toString(), RewardEntity.class);
            logger.info("getAllReward() :: GETALL :: Query :: ", builder.toString()); // Use {} to format the log
                                                                                      // message
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in RewardDAOImpl :: getAllReward()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in RewardDAOImpl :: getAllReward()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    public List<RewardEntity> getAllReward() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from rewards");
            query = em.createNativeQuery(builder.toString(), RewardEntity.class);
            logger.info("getAllReward() :: GETALL :: Query :: ", builder.toString()); // Use {} to format the log
                                                                                      // message
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in RewardDAOImpl :: getAllReward()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in RewardDAOImpl :: getAllReward()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    public List<RewardEntity> filterRewardsByAttributesDAO(RewardEntity filterParams) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<RewardEntity> query = criteriaBuilder.createQuery(RewardEntity.class);
        Root<RewardEntity> root = query.from(RewardEntity.class);

        Predicate predicate = criteriaBuilder.conjunction(); // Initialize with "AND" operation

        if (filterParams.getRewardID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("rewardID"), filterParams.getRewardID()));
        }

        if (filterParams.getUserID() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("userID"), filterParams.getUserID()));
        }

        if (filterParams.getRewardName() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("rewardPoint"), filterParams.getRewardName()));
        }

        if (filterParams.getRewardType() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("rewardType"), filterParams.getRewardType()));
        }

        if (filterParams.getRewardAvailability() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("rewardAvailability"), filterParams.getRewardAvailability()));
        }

        if (filterParams.getExpirationDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("expirationDate"), filterParams.getExpirationDate()));
        }
        // Repeat the above pattern for other filter attributes
        query.where(predicate);

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<LeaderBoardModel> getLeaderBoardDetailDAO() {
        Query query = null;
        StringBuilder queryBuilder = new StringBuilder();
        try {

            queryBuilder.append("WITH RankedUsers AS (");
            queryBuilder.append("    SELECT u.user_id, u.user_name, u.total_reward_point, ");
            queryBuilder.append("           RANK() OVER (ORDER BY u.total_reward_point DESC) AS calculated_rank ");
            queryBuilder.append("    FROM users u ");
            queryBuilder.append("    WHERE u.role_name = 'Volunteer' AND u.total_reward_point <> 0), ");
            queryBuilder.append("RewardsWithRank AS (");
            queryBuilder.append("    SELECT r.rewards_id, r.rewards_name, ");
            queryBuilder.append("           ROW_NUMBER() OVER (ORDER BY r.rewards_id) AS reward_rank ");
            queryBuilder.append("    FROM rewards r)");
            queryBuilder.append(
                    "SELECT ru.calculated_rank, ru.user_name, r.rewards_id, r.rewards_name, ru.total_reward_point, ");
            queryBuilder.append(
                    "       CASE WHEN '2023-11-24' >= CURRENT_DATE THEN 'Active' ELSE 'Expired' END AS STATUS ");
            queryBuilder.append("FROM RankedUsers ru ");
            queryBuilder.append("JOIN RewardsWithRank r ON ru.calculated_rank = r.reward_rank;");

            String queryString = queryBuilder.toString();
            System.out.println(queryBuilder.toString());
            logger.info("getLeaderBoardDetailDAO() :: GETALL :: Query :: ", queryBuilder.toString()); // Use {} to
                                                                                                      // format the log

            query = em.createNativeQuery(queryString, LeaderBoardEntity.class);
            return query.getResultList();
        } catch (NullPointerException e) {
            // Handle a specific NullPointerException if it occurs
            logger.error("NullPointerException in RewardDAOImpl :: getLeaderBoardDetailDAO()", e);
            throw e; // Re-throw the exception to propagate it further
        } catch (Exception e) {
            // Handle other exceptions (General Exception)
            logger.error("Exception in RewardDAOImpl :: getLeaderBoardDetailDAO()", e);
            return Collections.emptyList(); // Return an empty list to indicate an error
        }
    }

    private static int getYearFromDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return Integer.parseInt(dateFormat.format(date));
    }

    @Override
    @Transactional
    public boolean saveLeaderBoardHistory() {
        StringBuilder queryBuilder = new StringBuilder();
        logger.info("RewardDAOImpl :: saveLeaderBoardHistory() :: START :: ");
        try {
            // Set the target year
            int targetYear = getYearFromDate(new Date());

            // Create a temporary table to hold the data
            queryBuilder.append("CREATE TEMPORARY TABLE TempLeaderBoard AS ")
                    .append("WITH RankedUsers AS (")
                    .append("    SELECT u.user_id, u.user_name, u.total_reward_point,")
                    .append("           RANK() OVER (ORDER BY u.total_reward_point DESC) AS calculated_rank")
                    .append("    FROM users u")
                    .append("),")
                    .append("RewardsWithRank AS (")
                    .append("    SELECT r.rewards_id, r.rewards_name,")
                    .append("           ROW_NUMBER() OVER (ORDER BY r.rewards_id) AS reward_rank")
                    .append("    FROM rewards r")
                    .append(")")
                    .append("SELECT ru.calculated_rank, ru.user_name, r.rewards_id, r.rewards_name, ru.total_reward_point,")
                    .append("       CASE")
                    .append("        WHEN CURRENT_DATE = '").append(targetYear).append("-08-16' THEN 'Expired'")
                    .append("        WHEN CURRENT_DATE = '").append(targetYear + 1).append("-08-16' THEN 'Expired'")
                    .append("        WHEN CURRENT_DATE = '").append(targetYear + 1).append("-02-01' THEN 'Expired'")
                    .append("        WHEN CURRENT_DATE = '").append(targetYear + 1).append("-05-16' THEN 'Expired'")
                    .append("       ELSE 'Active' END AS STATUS,")
                    .append("       CASE")
                    .append("        WHEN CURRENT_DATE BETWEEN '").append(targetYear).append("-06-01' AND '")
                    .append(targetYear).append("-08-15' THEN 'Summer'")
                    .append("        WHEN CURRENT_DATE BETWEEN '").append(targetYear).append("-08-16' AND '")
                    .append(targetYear + 1).append("-12-15' THEN 'Fall'")
                    .append("        WHEN CURRENT_DATE BETWEEN '").append(targetYear + 1).append("-12-16' AND '")
                    .append(targetYear + 1).append("-01-31' THEN 'Winter'")
                    .append("        WHEN CURRENT_DATE BETWEEN '").append(targetYear + 1).append("-02-01' AND '")
                    .append(targetYear + 1).append("-05-15' THEN 'Spring'")
                    .append("        WHEN CURRENT_DATE > '").append(targetYear + 1).append("-05-15' THEN 'Summer'")
                    .append("       END AS SEASON")
                    .append("FROM RankedUsers ru")
                    .append("JOIN RewardsWithRank r ON ru.calculated_rank = r.reward_rank;");

            // Update and insert data into the leader_board table
            queryBuilder.append(
                    "INSERT INTO leader_board (calculated_rank, user_name, rewards_id, rewards_name, total_reward_point, STATUS, SEASON)")
                    .append("SELECT calculated_rank, user_name, rewards_id, rewards_name, total_reward_point, STATUS, SEASON")
                    .append("FROM TempLeaderBoard")
                    .append("ON DUPLICATE KEY UPDATE")
                    .append("    total_reward_point = VALUES(total_reward_point), STATUS = VALUES(STATUS), SEASON = VALUES(SEASON);");

            // Drop the temporary table
            queryBuilder.append("DROP TEMPORARY TABLE IF EXISTS TempLeaderBoard;");

            // Print the final query
            System.out.println(queryBuilder.toString());
            String sqlString = queryBuilder.toString();
            logger.info("RewardDAOImpl :: saveLeaderBoardHistory() :: Query :: " + sqlString);
            // em.createNativeQuery(sqlString).executeUpdate();
            em.createQuery(sqlString);
            logger.info("RewardDAOImpl :: saveLeaderBoardHistory() :: Successfully Pass Record To leader_board :: "
                    + sqlString);
            return true;
        } catch (Exception e) {
            logger.info(
                    "RewardDAOImpl :: saveLeaderBoardHistory() :: Exception :: Failed Pass Record To leader_board :: "
                            + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
}
