package com.models;

public class Role {
	
	private int roleId;
	private String roleName;
	private String roleType;
	private boolean isRole;
	/**
	 * @param roleId
	 * @param roleName
	 */
	public Role(int roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}
	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	/**
	 * @return the isRole
	 */
	public boolean isRole() {
		return isRole;
	}
	/**
	 * @param isRole the isRole to set
	 */
	public void setRole(boolean isRole) {
		this.isRole = isRole;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Roles [" + (roleName != null ? "roleName=" + roleName : "") + "]";
	}
	
	
}
