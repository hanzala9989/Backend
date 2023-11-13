package com.example.eventOrganizer.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.example.eventOrganizer.Entity.UserAssignedEvent;
import com.example.eventOrganizer.Entity.UserEntity;
import com.example.eventOrganizer.ModelDTO.JwtRequest;
import com.example.eventOrganizer.ModelDTO.LoginModel;
import com.example.eventOrganizer.Service.UserAssignedEventService;
import com.example.eventOrganizer.Service.UserService;
import com.example.eventOrganizer.ServiceImpl.EmailServiceImpl;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@ResponseBody
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {
    private static Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    UserAssignedEventService userAssignedEventService;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ResponseHandler> loginUser(@RequestBody LoginModel loginModel) {
        logger.info("UserController :: START :: loginUser() ::");
        try {
            if (loginModel != null) {
                logger.info("UserController :: PROCESS :: loginUser() ::");
                Optional<UserEntity> userObject = userService.loginUserService(loginModel);
                JwtRequest jwtRequest = new JwtRequest("hanzala", "root");
                userObject.get().setToken(this.authenticationController.createAuthenticationToken(jwtRequest));
                // this.authenticationController.createAuthenticationToken(jwtRequest);
                logger.info("UserController :: END :: loginUser() ::"
                        + this.authenticationController.createAuthenticationToken(jwtRequest));
                return ResponseEntity
                        .ok(new ResponseHandler("200", "User Login Successfully", List.of(userObject)));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: loginUser() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Login User: " + ex.getMessage()));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<ResponseHandler> addUser(@RequestBody UserEntity userEntity) {
        logger.info("UserController :: START :: AddUser() ::");
        try {
            if (userEntity != null) {
                String originalPassword = userEntity.getUserPassword();
                System.out.println("Original Password: " + originalPassword);
                String encodedPassword = passwordEncoder.encode(originalPassword);
                System.out.println("Encoded Password: " + encodedPassword);
                userEntity.setUserPassword(encodedPassword);
                logger.info("UserController :: PROCESS :: AddUser() ::");
                UserEntity userObject = userService.addUserService(userEntity);
                if (userObject != null) {
                    LocalDateTime now = LocalDateTime.now();
                    String to = userEntity.getUserEmail().trim();
                    String subject = "Welcome to XYZ! Your application is ready";
                    emailService.sendRegistrationEmail(to, subject ,userEntity.getUsername(), now.toString(), "Our Good Neighbour");
                }
                logger.info("UserController :: END :: AddUser() ::" + userObject);
                return ResponseEntity.ok(new ResponseHandler("200", "User Added Successfully"));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: AddUser() :: Internal Server Error "+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Adding User: " + ex.getMessage()));
        }
    }

    @PutMapping("/editUser")
    public ResponseEntity<ResponseHandler> EditUser(@RequestBody UserEntity userEntity) {
        logger.info("UserController :: START :: EditUser() ::");
        try {
            if (userEntity != null) {
                userService.editUserService(userEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "User Updated Successfully"));
            }
            throw new NullPointerException("userEntity is NULL Exception");
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: EditUser() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Update User :: " + ex.getMessage()));
        }
    }

    @GetMapping("/getAllUser")
    public ResponseHandler GetAllUser(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("UserController :: START :: GetAllUser() ::");
        try {
            logger.info("UserController :: PROCESS :: GetAllUser() :: Success");
            List<UserEntity> usersList = userService.getAllUserService(pageNumber, pageSize);
            return new ResponseHandler("200", "Success", List.of(usersList));
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: GetAllUser() :: Data Not Found");
            return new ResponseHandler("500", "Data Not Found", List.of());
        }
    }

    @GetMapping("/getUserByID")
    public ResponseHandler getUserByID(@RequestParam Long userID) {
        try {
            logger.info("UserController :: START :: GetAllUser() ::");
            UserEntity user = userService.getUserService(userID);
            if (user != null) {
                logger.info("UserController:: PROCESS :: getUserByID() :: User SuccessFully");
                return new ResponseHandler("200", "Success", List.of(user));
            } else {
                logger.error("Exception in UserController :: FAILED :: getUserByID() :: Data not found");
                return new ResponseHandler("500", "Data not found", List.of());
            }
        } catch (Exception e) {
            logger.error("Exception in UserController :: FAILED :: getUserByID() :: Internal Server Error");

            return new ResponseHandler("500", "Internal Server Error", List.of());
        }
    }

    @DeleteMapping("/deleteUserByID")
    public ResponseHandler DeleteUserByID(@RequestParam Long userID) {
        logger.info("UserController :: START :: DeleteUserByID() ::");
        try {
            boolean User = userService.deleteUserService(userID);

            if (User) {
                logger.info("UserController:: PROCESS :: DeleteUserByID() :: User Deleted SuccessFully");
                return new ResponseHandler("200", "User Deleted SuccessFully");
            } else {
                logger.error("Exception in UserController :: FAILED :: DeleteUserByID() :: Data not found");
                return new ResponseHandler("500", "Data not found", List.of());
            }
        } catch (Exception e) {
            logger.error("Exception in UserController :: FAILED :: DeleteUserByID() :: Internal Server Error");
            return new ResponseHandler("500", "Internal Server Error", List.of());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseHandler> filterUsers(@RequestBody UserEntity filterDTO) {
        try {
            logger.info("UserController :: PROCESS :: filterUsers() :: Success");
            List<UserEntity> usersList = userService.filterUsersByAttributesService(filterDTO);
            if (usersList.size() > 0) {
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(usersList)));
            } else {
                return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of(usersList)));
            }
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: filterUsers() :: Data Not Found");
            return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of()));
        }

    }

    @GetMapping("/assignUserEvent")
    public ResponseEntity<ResponseHandler> assignEventToUser(@RequestParam Long userID, @RequestParam Long eventID) {
        logger.info("UserController :: START :: assignEventToUser()");

        try {
            logger.info("UserController :: PROCESS :: assignEventToUser()");
            String userObject = userService.assignEventToUser(userID, eventID);
            logger.info("UserController :: END :: assignEventToUser()");
            return ResponseEntity.ok(new ResponseHandler("200", userObject));
            // handle the exception hanzala
        } catch (Exception ex) {
            logger.error(
                    "Exception in UserController :: FAILED :: assignEventToUser() :: The volunteer limit for this event has been exceeded");
            return ResponseEntity
                    .ok(new ResponseHandler("500", "The volunteer limit for this event has been exceeded ", List.of()));
        }
    }

    @GetMapping("/getAllUserEventsByID")
    public ResponseEntity<ResponseHandler> getAllUserEventsByID(@RequestParam Long eventID) {
        logger.info("UserController :: START :: getAllUserEventsByID()");

        try {
            logger.info("UserController :: PROCESS :: getAllUserEventsByID()");
            List<UserAssignedEvent> userObject = userAssignedEventService.getAllUserEventsByID(eventID);
            logger.info("UserController :: END :: getAllUserEventsByID()");
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(userObject)));
            // handle the exception hanzala
        } catch (Exception ex) {
            logger.error(
                    "Exception in UserController :: FAILED :: getAllUserEventsByID() ::");
            return ResponseEntity
                    .ok(new ResponseHandler("500", "No Event Register", List.of()));
        }
    }

    @GetMapping("/getAllUserFromEvent")
    public ResponseEntity<ResponseHandler> findUserEventsByEventId(@RequestParam Long eventID) {
        logger.info("UserController :: START :: findUserEventsByEventId()");

        try {
            logger.info("UserController :: PROCESS :: findUserEventsByEventId()");
            List<UserEntity> userObject = userAssignedEventService.getEventsByID(eventID);
            logger.info("UserController :: END :: findUserEventsByEventId()");
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(userObject)));
            // handle the exception hanzala
        } catch (Exception ex) {
            logger.error(
                    "Exception in UserController :: FAILED :: assignEventToUser() :: The volunteer limit for this event has been exceeded");
            return ResponseEntity
                    .ok(new ResponseHandler("500", "No Event Register", List.of()));
        }
    }

    @DeleteMapping("/deleteUserFromEvent")
    public ResponseHandler DeleteUserFromEvent(@RequestParam Long userID, @RequestParam Long eventID) {
        logger.info("UserController :: START :: DeleteUserFromEvent() ::");
        try {
            boolean User = userAssignedEventService.deleteUserFromEvent(userID, eventID);

            if (User) {
                logger.info("UserController:: PROCESS :: DeleteUserFromEvent() :: User Deleted SuccessFully");
                return new ResponseHandler("200", "User Deleted SuccessFully");
            } else {
                logger.error("Exception in UserController :: FAILED :: DeleteUserFromEvent() :: Data not found");
                return new ResponseHandler("500", "Data not found", List.of());
            }
        } catch (Exception e) {
            logger.error("Exception in UserController :: FAILED :: DeleteUserByID() :: Internal Server Error");
            return new ResponseHandler("500", "Internal Server Error", List.of());
        }
    }

    @CrossOrigin("*")
    @PutMapping("/processUserFromEvent")
    public ResponseEntity<ResponseHandler> processUserFromEvent(
            @RequestBody UserAssignedEvent userAssignedEventEntity) {
        logger.info("UserController :: START :: processUserFromEvent() ::");
        try {
            if (userAssignedEventEntity != null) {
                userAssignedEventService.processUserFromEventService(userAssignedEventEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "User Maker As Attended"));
            } else {
                throw new NullPointerException("userEntity is NULL Exception");

            }
        } catch (Exception ex) {
            logger.error("Exception in UserController :: FAILED :: processUserFromEvent() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Update User :: " + ex.getMessage()));
        }
    }

}
