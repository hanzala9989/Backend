package com.example.eventOrganizer.MongoDBEntity;

import org.springframework.data.annotation.Id;

public class LeaderBoardModel {

    @Id
    private String id; // MongoDB document ID

    private Long rank;
    private String userName;
    private String rewardName;
    private Long totalRewardPoint;
    private String status;

    public LeaderBoardModel() {
    }

    public LeaderBoardModel(String id, Long rank, String userName, String rewardName, Long totalRewardPoint,
            String status) {
        this.id = id;
        this.rank = rank;
        this.userName = userName;
        this.rewardName = rewardName;
        this.totalRewardPoint = totalRewardPoint;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Long getTotalRewardPoint() {
        return totalRewardPoint;
    }

    public void setTotalRewardPoint(Long totalRewardPoint) {
        this.totalRewardPoint = totalRewardPoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeaderBoardModel [id=" + id + ", rank=" + rank + ", userName=" + userName + ", rewardName=" + rewardName
                + ", totalRewardPoint=" + totalRewardPoint + ", status=" + status + "]";
    }

    // Constructors, getters, and setters

    // You can generate constructors, getters, and setters using your IDE or
    // manually write them.
}
