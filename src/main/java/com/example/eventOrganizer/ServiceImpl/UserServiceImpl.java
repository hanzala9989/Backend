package com.example.eventOrganizer.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventOrganizer.DAO.UserAssignedEventDAO;
import com.example.eventOrganizer.DAO.UserDAO;
import com.example.eventOrganizer.Entity.UserEntity;
import com.example.eventOrganizer.ModelDTO.LoginModel;
import com.example.eventOrganizer.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserAssignedEventDAO userAssignedEventDAO;

    @Override
    @Transactional
    public UserEntity addUserService(UserEntity userEntity) {
        return userDAO.addUser(userEntity);
    }

    @Override
    public UserEntity editUserService(UserEntity userEntity) {
        return userDAO.editUser(userEntity);
    }

    @Override
    public UserEntity getUserService(Long userID) {
        return userDAO.getUser(userID);
    }

    @Override
    public boolean deleteUserService(Long userID) {
        userAssignedEventDAO.deleteUserAllAssignedEvent(userID);
        return userDAO.deleteUser(userID);
    }

    @Override
    public List<UserEntity> getAllUserService(int pageNumber, int pageSize) {
        return userDAO.getAllUser(pageNumber, pageSize);
    }

    @Override
    public List<UserEntity> filterUsersByAttributesService(UserEntity userEntity) {
        return userDAO.filterUsersByAttributesDAO(userEntity);
    }

    @Override
    public Optional<UserEntity> loginUserService(LoginModel loginModel) {
        List<UserEntity> listObject = userDAO.getAllUser();
        logger.info("listObject :: " + listObject);
        Optional<UserEntity> foundUser = listObject.stream()
                .filter(user -> user.getUserEmail().equals(loginModel.getUsername())
                        && user.getUserPassword().equals(loginModel.getPassword()))
                .findFirst();
        return foundUser;
    }

    @Override
    @Transactional
    public String assignEventToUser(Long userID, Long eventID) {
        return userDAO.assignUserToEvents(userID, eventID);
    }

    public boolean isEmailUnique(String email) {
        List<UserEntity> userList = userDAO.getAllUser(10, 1);

        for (UserEntity user : userList) {
            System.out.println(email + " ::: " + user.getUserEmail());
            if (user.getUserEmail().equals(email)) {
                System.out.println(email + " ::: " + user.getUserEmail());
                return true;
            }
        }
        return false;
    }

}
