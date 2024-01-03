package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.RewardEntity;
import com.example.eventOrganizer.Entity.RewardHistory;
import com.example.eventOrganizer.ModelDTO.LeaderBoardModel;

public interface RewardService {
    public RewardEntity addRewardService(RewardEntity rewardEntity);

    public RewardEntity editRewardService(RewardEntity rewardEntity);

    public RewardEntity getRewardService(Long rewardID);

    public boolean deleteRewardService(Long rewardID);

    public List<RewardEntity> getAllRewardService(int pageSize, int pageNumber);

    public List<RewardEntity> filterRewardsByAttributesService(RewardEntity rewardEntity);

    public List<LeaderBoardModel> getLeaderBoardDetailService();

    public void saveLeaderBoardHistoryService();
    
    public List<RewardHistory> getAllRewardHistory();

    public List<RewardHistory> filterRewardsHistoryByAttributes(RewardHistory filterParams);

}
