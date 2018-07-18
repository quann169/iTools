package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
@Entity
@Table(name = "Roles", //
		uniqueConstraints = { //
        @UniqueConstraint(columnNames = "RoleName") })
public class Roles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5288767913364483996L;
 
	@Id
    @Column(name = "RoleID", nullable = false)
    private int roleID;
 
    @Column(name = "RoleName", length = 100, nullable = false)
    private String roleName;
 
    @Column(name = "RoleType", nullable = true)
    private Integer roleType;
    
    @Column(name = "IsRole", nullable = true)
    private int isRole;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
 
    public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public int getIsRole() {
		return isRole;
	}

	public void setIsRole(int isRole) {
		this.isRole = isRole;
	}
 
}