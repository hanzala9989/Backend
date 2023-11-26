package com.example.eventOrganizer.ModelDTO;

import org.springframework.data.annotation.Id;
public class LeaderBoardModel {
    @Id
    private Long id; // MongoDB document ID
    private Long rank;
    private String userName;
    private String rewardName;
    private Long totalRewardPoint;
    private String status;

    public LeaderBoardModel() {
    }

    public LeaderBoardModel(Long rank, String userName, String rewardName, Long totalRewardPoint, String status) {
        this.rank = rank;
        this.userName = userName;
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

    @Override
    public String toString() {
        return "leaderBoardEntity [rank=" + rank + ", userName=" + userName + ", rewardName=" + rewardName
                + ", totalRewardPoint=" + totalRewardPoint + ", status=" + status + "]";
    }

}
