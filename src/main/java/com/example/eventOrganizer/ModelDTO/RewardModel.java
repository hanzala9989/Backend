package com.example.eventOrganizer.ModelDTO;

public class RewardModel {
    private Long rewardID;
    private Long UserID;
    private int rewardPoint;
    private String rewardName;
    private String rewardType;
    private String rewardAvailability;
    private String expirationDate;

    public Long getRewardID() {
        return rewardID;
    }

    public void setRewardID(Long rewardID) {
        this.rewardID = rewardID;
    }

    public Long getUserID() {
        return UserID;
    }

    public void setUserID(Long userID) {
        UserID = userID;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getRewardAvailability() {
        return rewardAvailability;
    }

    public void setRewardAvailability(String rewardAvailability) {
        this.rewardAvailability = rewardAvailability;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

}
