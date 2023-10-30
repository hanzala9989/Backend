package com.example.eventOrganizer.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    // @NotEmpty(message = "Role ID is Required !!")
    private Long roleID;

    @Column(name = "role_name")
    // @NotEmpty(message = "Role Name is Required !!")
    private String roleName;

    @Column(name = "role_description")
    // @NotEmpty(message = "Description is Required !!")
    private String description;

    public RoleEntity(Long roleID, String roleName, String description) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.description = description;
    }

    public RoleEntity setRoleID(Long roleID) {
        this.roleID = roleID;
        return this;
    }

    public RoleEntity setRoleName(String roleName) {
        this.roleName = roleName;
        return this;

    }

    public RoleEntity setDescription(String description) {
        this.description = description;
        return this;

    }

    public Long getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RoleEntity [RoleID=" + roleID + ", RoleName=" + roleName + ", description=" + description + "]";
    }

}
