package com.example.eventOrganizer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "leader_board")
public class LeaderBoardEntity {
    @Id
    @Column(name = "calculated_rank")
    private Long rank;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "rewards_id")
    private String rewardID;
    @Column(name = "rewards_name")
    private String rewardName;
    @Column(name = "total_reward_point")
    private Long totalRewardPoint;
    @Column(name = "status")
    private String status;

    public LeaderBoardEntity() {
    }



    public LeaderBoardEntity(Long rank, String userName, String rewardID, String rewardName, Long totalRewardPoint,
            String status) {
        this.rank = rank;
        this.userName = userName;
        this.rewardID = rewardID;
        this.rewardName = rewardName;
        this.totalRewardPoint = totalRewardPoint;
        this.status = status;
    }



    public void setRank(Long rank) {
        this.rank = rank;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public void setTotalRewardPoint(Long totalRewardPoint) {
        this.totalRewardPoint = totalRewardPoint;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRank() {
        return rank;
    }

    public String getUserName() {
        return userName;
    }

    public String getRewardName() {
        return rewardName;
    }

    public Long getTotalRewardPoint() {
        return totalRewardPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }

    public String getRewardID() {
        return rewardID;
    }



    @Override
    public String toString() {
        return "LeaderBoardEntity [rank=" + rank + ", userName=" + userName + ", rewardID=" + rewardID + ", rewardName="
                + rewardName + ", totalRewardPoint=" + totalRewardPoint + ", status=" + status + "]";
    }

    
}
