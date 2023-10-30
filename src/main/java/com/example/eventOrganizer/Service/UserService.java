package com.example.eventOrganizer.Service;

import java.util.List;
import java.util.Optional;

import com.example.eventOrganizer.Entity.UserEntity;
import com.example.eventOrganizer.ModelDTO.LoginModel;

public interface UserService {
    public UserEntity addUserService(UserEntity userEntity);
    
    public Optional<UserEntity> loginUserService(LoginModel loginModel);

    public UserEntity editUserService(UserEntity userEntity);

    public UserEntity getUserService(Long userID);

    public boolean deleteUserService(Long userID);

    public List<UserEntity> getAllUserService(int pageNumber, int pageSize);

    public List<UserEntity> filterUsersByAttributesService(UserEntity userEntity);

    public String assignEventToUser(Long userID, Long eventID);


}
