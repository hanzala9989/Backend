package com.example.eventOrganizer.DAO;

import java.util.List;

import com.example.eventOrganizer.Entity.RewardEntity;
import com.example.eventOrganizer.ModelDTO.LeaderBoardModel;

public interface RewardDAO {
    public RewardEntity addReward(RewardEntity rewardEntity);

    public RewardEntity editReward(RewardEntity rewardEntity);

    public RewardEntity getReward(Long rewardID);

    public boolean deleteReward(Long rewardID);

    public List<RewardEntity> getAllReward(int pageSize, int pageNumber);

    public List<RewardEntity> filterRewardsByAttributesDAO(RewardEntity rewardEntity);

    public List<LeaderBoardModel> getLeaderBoardDetailDAO();

    public boolean saveLeaderBoardHistory();

}
