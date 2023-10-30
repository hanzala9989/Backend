package com.example.eventOrganizer.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "rewards")
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rewards_id")
    // @NotEmpty(message = "Reward ID is Required !!")
    private Long rewardID;
    
    @Column(name = "user_id")
    // @NotEmpty(message = "User ID is Required !!")
    private Long userID;
    
    @Column(name = "rewards_point")
    // @NotEmpty(message = "Rewards point is Required !!")
    private int rewardPoint;
    
    @Column(name = "rewards_name")
    // @NotEmpty(message = "Rewards Name is Required !!")
    private String rewardName;
    
    @Column(name = "rewards_type")
    // @NotEmpty(message = "Rewards Type is Required !!")
    private String rewardType;
    
    @Column(name = "rewards_availability")
    // @NotEmpty(message = "Rewards AvailabilityType is Required !!")
    private String rewardAvailability;
    
    @Column(name = "rewards_expiration_date")
    // @NotEmpty(message = "Rewards Expire Date is Required !!")
    private String expirationDate;

    @Column(name = "rewards_last_Update_Timestamp")
    // @NotEmpty(message = "Rewards Expire Date is Required !!")
    LocalDateTime lastUpdateTimestamp = LocalDateTime.now();
    
    @Column(name = "rewards_description")
    // @NotEmpty(message = "Rewards Expire Date is Required !!")
    private String description;                                          ;

    public RewardEntity() {
    }

    public RewardEntity(Long rewardID, Long userID, int rewardPoint, String rewardName, String rewardType,
            String rewardAvailability, String expirationDate) {
        this.rewardID = rewardID;
        this.userID = userID;
        this.rewardPoint = rewardPoint;
        this.rewardName = rewardName;
        this.rewardType = rewardType;
        this.rewardAvailability = rewardAvailability;
        this.expirationDate = expirationDate;
    }

    public RewardEntity setRewardID(Long rewardID) {
        this.rewardID = rewardID;
        return this;
    }

    public RewardEntity setUserID(Long userID) {
        this.userID = userID;
        return this;
    }

    public RewardEntity setRewardPoint(int rewardPoint) {
        this.rewardPoint = rewardPoint;
        return this;
    }

    public RewardEntity setRewardName(String rewardName) {
        this.rewardName = rewardName;
        return this;
    }

    public RewardEntity setRewardType(String rewardType) {
        this.rewardType = rewardType;
        return this;
    }

    public RewardEntity setRewardAvailability(String rewardAvailability) {
        this.rewardAvailability = rewardAvailability;
        return this;
    }

    public RewardEntity setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public Long getRewardID() {
        return rewardID;
    }

    public Long getUserID() {
        return userID;
    }

    public int getRewardPoint() {
        return rewardPoint;
    }

    public String getRewardName() {
        return rewardName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public String getRewardAvailability() {
        return rewardAvailability;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "RewardEntity [rewardID=" + rewardID + ", UserID=" + userID + ", rewardPoint=" + rewardPoint
                + ", rewardName=" + rewardName + ", rewardType=" + rewardType + ", rewardAvailability="
                + rewardAvailability + ", expirationDate=" + expirationDate + "]";
    }

}
