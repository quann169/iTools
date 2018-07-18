package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "RoleAssessor")
/*@Table(name = "RoleAssessor", //
		uniqueConstraints = { //
        @UniqueConstraint(columnNames = { "AssessorID", "RoleID" }) })*/
public class RoleAssessor implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4226993732257359366L;

	@Id
    @Column(name = "RoleAssessorID", nullable = false)
    private int roleAssessorID;
 
    @Column(name = "RoleID", nullable = true)
    private int roleID;
    
    @Column(name = "AssessorID", nullable = true)
    private int assessorID;
    
	@Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssessorID", nullable = false)
    private Assessor assessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", nullable = false)
    private Roles roles;*/
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
 
    public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getRoleAssessorID() {
		return roleAssessorID;
	}

	public void setRoleAssessorID(int roleAssessorID) {
		this.roleAssessorID = roleAssessorID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int getAssessorID() {
		return assessorID;
	}

	public void setAssessorID(int assessorID) {
		this.assessorID = assessorID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

/*	public Assessor getAssessor() {
		return assessor;
	}

	public void setAssessor(Assessor assessor) {
		this.assessor = assessor;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}*/
 
}