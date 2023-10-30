package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.RoleEntity;

public interface RoleService {
    public RoleEntity addRoleService(RoleEntity roleEntity);

    public RoleEntity editRoleService(RoleEntity roleEntity);

    public RoleEntity getRoleService(Long roleID);

    public boolean deleteRoleService(Long roleID);

    public List<RoleEntity> getAllRoleService(int pageNumber, int pageSize);

    public List<RoleEntity> filterRoleByAttributesService(RoleEntity roleEntity);

}
