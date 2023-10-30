package com.example.eventOrganizer.ModelDTO;

public class RoleModel {
    private Long RoleID;
    private String RoleName;
    private String description;

    public Long getRoleID() {
        return RoleID;
    }

    public void setRoleID(Long roleID) {
        RoleID = roleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
