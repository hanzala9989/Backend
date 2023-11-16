package com.example.eventOrganizer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "leader_board")
public class RewardHistory {
    @Id
    @Column(name = "calculated_rank")
    private Long rank;
    @Column(name = "user_name")
    private String username;
    @Column(name = "rewards_id")
    private String rewardID;
    @Column(name = "rewards_name")
    private String rewardName;
    @Column(name = "total_reward_point")
    private String totalRewardPoint;
    @Column(name = "season")
    private String season;
    @Column(name = "STATUS")
    private String status;
    public RewardHistory() {
    }
    public RewardHistory(Long rank, String username, String rewardID, String rewardName, String totalRewardPoint,
            String season, String status) {
        this.rank = rank;
        this.username = username;
        this.rewardID = rewardID;
        this.rewardName = rewardName;
        this.totalRewardPoint = totalRewardPoint;
        this.season = season;
        this.status = status;
    }
    public Long getRank() {
        return rank;
    }
    public void setRank(Long rank) {
        this.rank = rank;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRewardID() {
        return rewardID;
    }
    public void setRewardID(String rewardID) {
        this.rewardID = rewardID;
    }
    public String getRewardName() {
        return rewardName;
    }
    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }
    public String getTotalRewardPoint() {
        return totalRewardPoint;
    }
    public void setTotalRewardPoint(String totalRewardPoint) {
        this.totalRewardPoint = totalRewardPoint;
    }
    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "RewardHistory [rank=" + rank + ", username=" + username + ", rewardID=" + rewardID + ", rewardName="
                + rewardName + ", totalRewardPoint=" + totalRewardPoint + ", season=" + season + ", status=" + status
                + "]";
    }
   
}
