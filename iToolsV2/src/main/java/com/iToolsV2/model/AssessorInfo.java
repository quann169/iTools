package com.iToolsV2.model;
 
public class AssessorInfo {
 
    private int assessorId;
    private String userName;
    private String encrytedPassword;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;
    private String phone;
    private String companyCode;
    private String lastPassword;
    private boolean active;
 
    public AssessorInfo() {
 
    }
 
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active) {
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
    }
 
	public int getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(int assessorId) {
		this.assessorId = assessorId;
	}

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
 
}