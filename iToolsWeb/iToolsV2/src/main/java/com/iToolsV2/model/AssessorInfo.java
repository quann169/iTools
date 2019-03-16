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
    private String machineCode;
    private String lastPassword;
    private boolean active;
    private boolean locked;
    private String password;
    private String confirmPassword;
    private String rolesList;
 
    public AssessorInfo() {
 
    }
 
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active) {
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode,     		
    		boolean locked) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        this.companyCode = companyCode;
        this.locked = locked;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode,     		
    		boolean locked,
    		String rolesList) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        this.companyCode = companyCode;
        this.locked = locked;
        this.rolesList = rolesList;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode, String machineCode,    		
    		boolean locked,
    		String rolesList) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        this.companyCode = companyCode;
        this.machineCode = machineCode;
        this.locked = locked;
        this.rolesList = rolesList;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode, 
    		boolean locked, 
    		String password, String confirmPassword) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        //this.company = this.getCompanyByCode(companyCode);
        this.companyCode = companyCode;
        this.locked = locked;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode, 
    		boolean locked, 
    		String password, String confirmPassword,
    		String rolesList) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        //this.company = this.getCompanyByCode(companyCode);
        this.companyCode = companyCode;
        this.locked = locked;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.rolesList = rolesList;
    }
    
    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode, String machineCode,
    		boolean locked, 
    		String password, String confirmPassword,
    		String rolesList) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        //this.company = this.getCompanyByCode(companyCode);
        this.companyCode = companyCode;
        this.machineCode = machineCode;
        this.locked = locked;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.rolesList = rolesList;
    }

/*    public AssessorInfo(int assessorId, String userName, String encrytedPassword, boolean active, 
    		String firstName, String lastName, String emailAddress, String address, String phone, 
    		String companyCode, 
    		boolean locked,
    		String password, String confirmPassword) {
    	
        this.assessorId = assessorId;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.address = address;
        this.phone = phone;
        this.companyCode = companyCode;
        this.locked = locked;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }*/
    
/*    private Company getCompanyByCode(String companyCode) {
    	Company currentCompany = null;
    	currentCompany = companyDAO.findCompanyByCode(companyCode);
    	return currentCompany;
    }*/    
    
	public String getCompanyCode() {
		return companyCode;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
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

	public String getLastPassword() {
		return lastPassword;
	}

	public void setLastPassword(String lastPassword) {
		this.lastPassword = lastPassword;
	}

	public String getRolesList() {
		return rolesList;
	}

	public void setRolesList(String rolesList) {
		this.rolesList = rolesList;
	}
	
	
 
}