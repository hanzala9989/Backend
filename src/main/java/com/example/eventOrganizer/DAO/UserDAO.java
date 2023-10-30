package com.example.eventOrganizer.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import com.example.eventOrganizer.Entity.UserEntity;

public interface UserDAO {
    public UserEntity addUser(UserEntity userEntity);

    public UserEntity editUser(UserEntity userEntity);

    public UserEntity getUser(Long userID);

    public UserEntity findByUsername(String userName);

    public boolean deleteUser(Long userID);

    public List<UserEntity> getAllUser(int pageNumber, int pageSize);

    public List<UserEntity> getAllUser();

    public EntityManager getEntityManager();

    public List<UserEntity> filterUsersByAttributesDAO(UserEntity userEntity);

    public String assignUserToEvents(Long userID, Long eventID);

    public void addPointRewardToUser(Long userID);
}
