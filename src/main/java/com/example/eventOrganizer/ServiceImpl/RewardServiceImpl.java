package com.example.eventOrganizer.ServiceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.RewardDAO;
import com.example.eventOrganizer.Entity.RewardEntity;
import com.example.eventOrganizer.Entity.RewardHistory;
import com.example.eventOrganizer.ModelDTO.LeaderBoardModel;
import com.example.eventOrganizer.Service.RewardService;

@Service
public class RewardServiceImpl implements RewardService {
    @Autowired
    RewardDAO rewardDAO;

    @Override
    public RewardEntity addRewardService(RewardEntity rewardEntity) {
        return rewardDAO.addReward(rewardEntity);
    }

    @Override
    public RewardEntity editRewardService(RewardEntity rewardEntity) {
        return rewardDAO.editReward(rewardEntity);
    }

    @Override
    public RewardEntity getRewardService(Long rewardID) {
        return rewardDAO.getReward(rewardID);
    }

    @Override
    public boolean deleteRewardService(Long rewardID) {
        return rewardDAO.deleteReward(rewardID);
    }

    @Override
    public List<RewardEntity> getAllRewardService(int pageSize, int pageNumber) {
        return rewardDAO.getAllReward(pageSize, pageNumber);
    }

    @Override
    public List<RewardEntity> filterRewardsByAttributesService(RewardEntity rewardEntity) {
        return rewardDAO.filterRewardsByAttributesDAO(rewardEntity);
    }

    @Override
    public List<LeaderBoardModel> getLeaderBoardDetailService() {
        return rewardDAO.getLeaderBoardDetailDAO();
    }

    @Override
    public void saveLeaderBoardHistoryService() {
        System.out.println("I came to service");
        LocalDate currentDate = LocalDate.now();

        if (currentDate.getMonthValue() == 4 ||
                currentDate.getMonthValue() == 10 ||
                currentDate.getMonthValue() == 12) {
            boolean saveFlag = rewardDAO.saveLeaderBoardHistory();
            if (!saveFlag) {
                return;
            } else {
                System.out.println("Successfully Pass Record To leader_board");
            }

        } else {
            System.out.println("this mounth we can not excute the query. Sorry inconvience!!");
        }
    }

    @Override
    public List<RewardHistory> getAllRewardHistory() {
        return rewardDAO.getAllRewardHistory();
    }

    @Override
    public List<RewardHistory> filterRewardsHistoryByAttributes(RewardHistory filterParams) {
        return rewardDAO.filterRewardsHistoryByAttributesDAO(filterParams);
    }

}
