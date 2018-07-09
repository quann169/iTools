package com.iToolsV2.entity;
 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
 
@Entity
@Table(name = "Assessor", //
		uniqueConstraints = { //
				@UniqueConstraint(columnNames = "UserName") })
public class Assessor implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -7010154086721560047L;
	public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    
    @Id
    @Column(name = "AssessorID", nullable = false)
    private Integer assessorID;
    
    @Column(name = "UserName", length = 100, nullable = false)
    private String userName;
    
    @Column(name = "FingerID", length = 100, nullable = false)
    private String fingerID;
 
    @Column(name = "Password", length = 255, nullable = false)
    private String encrytedPassword;
    
    @Column(name = "FirstName", length = 255, nullable = false)
    private String firstName;
    
    @Column(name = "LastName", length = 255, nullable = false)
    private String lastName;
    
    @Column(name = "EmailAddress", length = 255, nullable = false)
    private String emailAddress;
    
    @Column(name = "Address", length = 255, nullable = false)
    private String address;
    
	@Column(name = "Phone", length = 255, nullable = false)
    private String phone;
    
    @Column(name = "CompanyCode", length = 255, nullable = true)
    private String companyCode;
    
    @Column(name = "LastPassword", length = 255, nullable = true)
    private String lastPassword;
 
    @Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
    
    @Column(name = "IsLocked", length = 1, nullable = false)
    private boolean locked;
    
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CompanyID", nullable = false, //
            foreignKey = @ForeignKey(name = "Assessor_ibfk_1"))
    private Company company;*/
    
/*    @Column(name = "IsFirstTimeLogin", length = 1, nullable = true)
    private boolean isFirstTimeLogin;*/
    
    public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

/*	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}*/

	public Integer getAssessorID() {
		return assessorID;
	}

	public void setAssessorID(Integer assessorID) {
		this.assessorID = assessorID;
	}

	public String getFingerID() {
		return fingerID;
	}

	public void setFingerID(String fingerID) {
		this.fingerID = fingerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}	
 
    /*@Column(name = "IsFirstChange", length = 1, nullable = true)
    private boolean isFirstChange;
    
    public boolean isFirstChange() {
		return isFirstChange;
	}

	public void setFirstChange(boolean isFirstChange) {
		this.isFirstChange = isFirstChange;
	}*/
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
 
    public boolean isActive() {
        return active;
    }
 
    public void setActive(boolean active) {
        this.active = active;
    }
 
    @Override
    public String toString() {
        return "[" + this.userName + "," + this.encrytedPassword + "]";
    }
 
}