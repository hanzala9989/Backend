package com.example.eventOrganizer.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.RoleDAO;
import com.example.eventOrganizer.Entity.RoleEntity;
import com.example.eventOrganizer.Service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDAO roleDAO;

    @Override
    public RoleEntity addRoleService(RoleEntity roleEntity) {
        return roleDAO.addRole(roleEntity);
    }

    @Override
    public RoleEntity editRoleService(RoleEntity roleEntity) {
        return roleDAO.editRole(roleEntity);
    }

    @Override
    public RoleEntity getRoleService(Long roleID) {
        return roleDAO.getRole(roleID);
    }

    @Override
    public boolean deleteRoleService(Long roleID) {
        return roleDAO.deleteRole(roleID);
    }

    @Override
    public List<RoleEntity> getAllRoleService(int pageNumber, int pageSize) {
        return roleDAO.getAllRole(pageNumber,pageSize);
    }

    @Override
    public List<RoleEntity> filterRoleByAttributesService(RoleEntity roleEntity) {
        return roleDAO.filterRoleByAttributesDAO(roleEntity);
    }

}
