package com.models;

import java.util.List;

public class Assessor {

	private int assessorId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String isActive;
	private String isLocked;
	private List<Role> listRoles;
	private String companyCode;
	private boolean isFirstTimeLogin;

	/**
	 * @param username
	 * @param password
	 * @param companyId
	 */
	public Assessor(String username, String password, String companyCode) {
		super();
		this.username = username;
		this.password = password;
		this.companyCode = companyCode;
	}
	
	public Assessor(String username, String password, String companyCode, String isActive) {
		super();
		this.username = username;
		this.password = password;
		this.companyCode = companyCode;
		this.isActive = isActive;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the assessorId
	 */
	public int getAssessorId() {
		return assessorId;
	}

	/**
	 * @param assessorId
	 *            the assessorId to set
	 */
	public void setAssessorId(int assessorId) {
		this.assessorId = assessorId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the isActive
	 */
	public String isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(String isActive) {
		this.isActive = isActive;
	}
	
	/**
	 * @return the isActive
	 */
	public boolean isLocked() {
		if ("1".equals(isLocked) || isLocked.toUpperCase().equals("LOCKED")) {
			return true;
		}
		
		return false;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the listRoles
	 */
	public List<Role> getListRoles() {
		return listRoles;
	}

	/**
	 * @param listRoles
	 *            the listRoles to set
	 */
	public void setListRoles(List<Role> listRoles) {
		this.listRoles = listRoles;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the isFirstTimeLogin
	 */
	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	/**
	 * @param isFirstTimeLogin
	 *            the isFirstTimeLogin to set
	 */
	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	@Override
	public String toString() {
		return "Assessor [assessorId=" + assessorId + ", username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", isActive=" + isActive
				+ ", isLocked=" + isLocked + ", listRoles=" + listRoles + ", companyCode=" + companyCode
				+ ", isFirstTimeLogin=" + isFirstTimeLogin + "]";
	}

}
