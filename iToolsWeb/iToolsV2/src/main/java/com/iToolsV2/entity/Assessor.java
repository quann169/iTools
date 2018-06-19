package com.iToolsV2.entity;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    private int assessorID;
    
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
    
    @Column(name = "CompanyId", length = 100, nullable = false)
    private String companyCode;
    
    @Column(name = "LastPassword", length = 255, nullable = false)
    private String lastPassword;
 
    @Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
    
    public int getAssessorID() {
		return assessorID;
	}

	public void setAssessorID(int assessorID) {
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