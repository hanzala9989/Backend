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
import com.example.eventOrganizer.Entity.RewardHistory;
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
            queryBuilder.append("  SELECT ");
            queryBuilder.append("    u.user_id, ");
            queryBuilder.append("    u.user_name, ");
            queryBuilder.append("    u.total_reward_point, ");
            queryBuilder.append("    RANK() OVER (ORDER BY u.total_reward_point DESC, u.user_name) AS calculated_rank ");
            queryBuilder.append("  FROM users u ");
            queryBuilder.append("  WHERE u.role_name = 'Volunteer' ");
            queryBuilder.append("    AND u.total_reward_point <> 0 ");
            queryBuilder.append("), ");
            queryBuilder.append("RewardsWithRank AS ( ");
            queryBuilder.append("  SELECT ");
            queryBuilder.append("    r.rewards_id, ");
            queryBuilder.append("    r.rewards_name, ");
            queryBuilder.append("    ROW_NUMBER() OVER (ORDER BY r.rewards_id) AS reward_rank ");
            queryBuilder.append("  FROM rewards r ");
            queryBuilder.append(") ");
            queryBuilder.append("SELECT ");
            queryBuilder.append("  ru.calculated_rank, ");
            queryBuilder.append("  ru.user_name, ");
            queryBuilder.append("  r.rewards_id, ");
            queryBuilder.append("  r.rewards_name, ");
            queryBuilder.append("  ru.total_reward_point, ");
            queryBuilder.append("  CASE ");
            queryBuilder.append("    WHEN '2030-11-24' >= CURRENT_DATE THEN 'Active' ");
            queryBuilder.append("    ELSE 'Expired' ");
            queryBuilder.append("  END AS STATUS ");
            queryBuilder.append("FROM RankedUsers ru ");
            queryBuilder.append("JOIN RewardsWithRank r ON ru.calculated_rank = r.reward_rank");
            
            String queryString = queryBuilder.toString();
            System.out.println(queryString);
            

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

    @Override
    @Transactional
    public boolean saveLeaderBoardHistory() {
        StringBuilder queryBuilder = new StringBuilder();
        logger.info("RewardDAOImpl :: saveLeaderBoardHistory() :: START :: ");
        try {
            queryBuilder.append("CREATE TEMPORARY TABLE IF NOT EXISTS TempLeaderBoard AS ");
            queryBuilder.append("SELECT u.user_id, u.user_name, u.total_reward_point, ");
            queryBuilder.append("       RANK() OVER (ORDER BY u.total_reward_point DESC) AS calculated_rank ");
            queryBuilder.append("FROM users u ");

            queryBuilder.append(
                    "INSERT INTO leader_board (calculated_rank, user_name, rewards_id, rewards_name, total_reward_point, STATUS, SEASON) ");
            queryBuilder.append("SELECT RANK() OVER (ORDER BY u.total_reward_point DESC) AS calculated_rank, ");
            queryBuilder.append("       u.user_name, r.rewards_id, r.rewards_name, ");
            queryBuilder.append("       u.total_reward_point, ");
            queryBuilder.append("       CASE ");
            queryBuilder.append("        WHEN CURRENT_DATE = CONCAT(@target_year, '-08-16') THEN 'Expired' ");
            queryBuilder.append("        WHEN CURRENT_DATE = CONCAT(@target_year + 1, '-08-16') THEN 'Expired' ");
            queryBuilder.append("        WHEN CURRENT_DATE = CONCAT(@target_year + 1, '-02-01') THEN 'Expired' ");
            queryBuilder.append("        WHEN CURRENT_DATE = CONCAT(@target_year + 1, '-05-16') THEN 'Expired' ");
            queryBuilder.append("       ELSE 'Active' END AS STATUS, ");
            queryBuilder.append("       CASE ");
            queryBuilder.append(
                    "        WHEN CURRENT_DATE BETWEEN CONCAT(@target_year, '-06-01') AND CONCAT(@target_year, '-08-15') THEN 'Summer' ");
            queryBuilder.append(
                    "        WHEN CURRENT_DATE BETWEEN CONCAT(@target_year, '-08-16') AND CONCAT(@target_year + 1, '-12-15') THEN 'Fall' ");
            queryBuilder.append(
                    "        WHEN CURRENT_DATE BETWEEN CONCAT(@target_year + 1, '-12-16') AND CONCAT(@target_year + 1, '-01-31') THEN 'Winter' ");
            queryBuilder.append(
                    "        WHEN CURRENT_DATE BETWEEN CONCAT(@target_year + 1, '-02-01') AND CONCAT(@target_year + 1, '-05-15') THEN 'Spring' ");
            queryBuilder.append("        WHEN CURRENT_DATE > CONCAT(@target_year + 1, '-05-15') THEN 'Summer' ");
            queryBuilder.append("       END AS SEASON ");
            queryBuilder.append("FROM users u ");
            queryBuilder.append("JOIN rewards r ON 1=1 ");

            queryBuilder.append("ON DUPLICATE KEY UPDATE ");
            queryBuilder.append("    leader_board.total_reward_point = TempLeaderBoard.total_reward_point, ");
            queryBuilder.append("    leader_board.STATUS = TempLeaderBoard.STATUS, ");
            queryBuilder.append("    leader_board.SEASON = TempLeaderBoard.SEASON ");

            queryBuilder.append("DROP TEMPORARY TABLE IF EXISTS TempLeaderBoard");

            String queryString = queryBuilder.toString();

            Query dropTempTableQuery = em.createNativeQuery(queryString);
            dropTempTableQuery.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.info(
                    "RewardDAOImpl :: saveLeaderBoardHistory() :: Exception :: Failed Pass Record To leader_board :: "
                            + e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }
   
    @Override
    public List<RewardHistory> getAllRewardHistory() {
        Query query = null;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append("select * from leader_board;");
            query = em.createNativeQuery(builder.toString(), RewardHistory.class);
            logger.info("getAllReward() :: GETALL :: Query :: ", builder.toString()); 
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

}
