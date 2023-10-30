package com.example.eventOrganizer.DAO;

import java.util.List;

import com.example.eventOrganizer.Entity.RoleEntity;

public interface RoleDAO {
    public RoleEntity addRole(RoleEntity roleEntity);

    public RoleEntity editRole(RoleEntity roleEntity);

    public RoleEntity getRole(Long roleID);

    public boolean deleteRole(Long roleID);

    public List<RoleEntity> getAllRole(int pageNumber, int pageSize);

    public List<RoleEntity> filterRoleByAttributesDAO(RoleEntity roleEntity);

}
