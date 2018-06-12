package com.iToolsV2.model;
 
public class AssessorInfo {
 
    private int assessorId;
    private String userName;
    private String encrytedPassword;
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
 
}