package com.models;

import java.util.List;

public class Assessor {

	private int assessorId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private boolean isActive;
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
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Assessor [assessorId=" + assessorId + ", " + (username != null ? "username=" + username + ", " : "")
				+ (companyCode != null ? "companyCode=" + companyCode + ", " : "")
				+ (listRoles != null ? "listRoles=" + listRoles + ", " : "")
				+ (email != null ? "email=" + email + ", " : "") + "isActive=" + isActive + "]";
	}

}
