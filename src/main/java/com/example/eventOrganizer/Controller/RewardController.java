package com.example.eventOrganizer.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventOrganizer.Entity.RewardEntity;
import com.example.eventOrganizer.ModelDTO.LeaderBoardModel;
import com.example.eventOrganizer.Service.RewardService;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@ResponseBody
@RequestMapping("/reward")
@CrossOrigin("*")
public class RewardController {
    private static Logger logger = LogManager.getLogger(RewardController.class);

    @Autowired
    RewardService rewardService;

    @PostMapping("/createReward")
    public ResponseEntity<ResponseHandler> AddReward(@RequestBody RewardEntity rewardEntity) {
        logger.info("RewardController :: START :: AddReward() ::");
        try {
            if (rewardEntity != null) {
                rewardService.addRewardService(rewardEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Reward Added Successfully"));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: AddReward() :: Internal Server Error ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Adding Reward: " + ex.getMessage()));
        }
    }

    @PutMapping("/editReward")
    public ResponseEntity<ResponseHandler> EditReward(@RequestBody RewardEntity rewardEntity) {
        logger.info("RewardController :: START :: EditReward() ::");
        try {
            if (rewardEntity != null) {
                rewardService.editRewardService(rewardEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Reward Updated Successfully"));
            }
            throw new NullPointerException("RewardEntity is NULL Exception");
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: EditReward() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Add Reward :: " + ex.getMessage()));
        }
    }

    @GetMapping("/getAllReward")
    public ResponseHandler getAllReward(@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
        logger.info("RewardController :: START :: getAllReward() ::");
        try {
            logger.info("RewardController :: PROCESS :: getAllReward() :: Success");
            List<RewardEntity> rewardsList = rewardService.getAllRewardService(pageSize, pageNumber);
            return new ResponseHandler("200", "Success", List.of(rewardsList));
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: getAllReward() :: Data Not Found");
            return new ResponseHandler("500", "Data Not Found", List.of());
        }
    }

    @GetMapping("/getRewardByID")
    public ResponseHandler getRewardByID(@RequestParam Long rewardID) {
        try {
            logger.info("RewardController :: START :: getRewardByID() ::");
            RewardEntity rewardObject = rewardService.getRewardService(rewardID);
            if (rewardObject != null) {
                logger.info("RewardController:: PROCESS :: getRewardByID() :: Reward SuccessFully");
                return new ResponseHandler("200", "Success", List.of(rewardObject));
            } else {
                logger.error("Exception in RewardController :: FAILED :: getRewardByID() :: Data not found");
                return new ResponseHandler("500", "Data not found", List.of());
            }
        } catch (Exception e) {
            logger.error("Exception in RewardController :: FAILED :: getRewardByID() :: Internal Server Error");

            return new ResponseHandler("500", "Internal Server Error", List.of());
        }
    }

    @DeleteMapping("/deleteRewardByID")
    public ResponseHandler deleteRewardByID(@RequestParam Long RewardID) {
        logger.info("RewardController :: START :: DeleteRewardByID() ::");
        try {
            boolean reward = rewardService.deleteRewardService(RewardID);

            if (reward) {
                logger.info("RewardController:: PROCESS :: DeleteRewardByID() :: Reward Deleted SuccessFully");
                return new ResponseHandler("200", "Reward Deleted SuccessFully");
            } else {
                logger.error("Exception in RewardController :: FAILED :: DeleteRewardByID() :: Data not found");
                return new ResponseHandler("500", "Data not found", List.of());
            }
        } catch (Exception e) {
            logger.error("Exception in RewardController :: FAILED :: DeleteRewardByID() :: Internal Server Error");
            return new ResponseHandler("500", "Internal Server Error", List.of());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseHandler> filterReward(@RequestBody RewardEntity filterDTO) {
        try {
            logger.info("RewardController :: PROCESS :: filterReward() :: Success");
            List<RewardEntity> rewardList = rewardService.filterRewardsByAttributesService(filterDTO);
            if (rewardList.size() > 0) {
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(rewardList)));
            } else {
                return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of(rewardList)));
            }
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: filterReward() :: Data Not Found");
            return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of()));
        }

    }

    @GetMapping("/getLeaderBoardDetails")
    public ResponseEntity<ResponseHandler> GetLeaderBoardDetails() {
        logger.info("RewardController :: START :: assignEventToUser()");

        try {
            logger.info("RewardController :: PROCESS :: GetLeaderBoardDetails()");
            List<LeaderBoardModel> leaderBoardModelsObject = rewardService.getLeaderBoardDetailService();
            logger.info("RewardController :: END :: GetLeaderBoardDetails()");
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(leaderBoardModelsObject)));
            // handle the exception hanzala
        } catch (Exception ex) {
            logger.error(
                    "Exception in RewardController :: FAILED :: GetLeaderBoardDetails() :: leaderboard error");
            return ResponseEntity
                    .ok(new ResponseHandler("500", "No Event Register", List.of()));
        }
    }
}
