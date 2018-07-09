package com.iToolsV2.form;
 
//import com.iToolsV2.entity.Assessor;
import com.iToolsV2.model.AssessorInfo;
 
public class AssessorForm {
	private int assessorId;
    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String emailAddress;
    private String phone;
    private String companyCode;    
    private boolean active;
    private boolean locked;
	private String password;
    private String confirmPassword;
 
    public AssessorForm() {
 
    }
 
    public AssessorForm(AssessorInfo assessorInfo) {
        if (assessorInfo != null) {
        	this.assessorId = assessorInfo.getAssessorId();
            this.name = assessorInfo.getUserName();
            this.firstName = assessorInfo.getFirstName();
            this.lastName = assessorInfo.getLastName();
            this.address = assessorInfo.getAddress();
            this.emailAddress = assessorInfo.getEmailAddress();
            this.phone = assessorInfo.getPhone();
            this.companyCode = assessorInfo.getCompanyCode();
            this.active = assessorInfo.isActive();
            this.password = assessorInfo.getPassword();
            this.confirmPassword = assessorInfo.getConfirmPassword();
            this.assessorId = assessorInfo.getAssessorId();
            this.locked = assessorInfo.isLocked();
        }
    }
    
    /*public AssessorForm(Assessor assessor) {
            this.name = assessor.getUserName();
            this.firstName = assessor.getFirstName();
            this.lastName = assessor.getLastName();
            this.address = assessor.getAddress();
            this.emailAddress = assessor.getEmailAddress();
            this.phone = assessor.getPhone();
            this.companyCode = assessor.getCompanyCode();
            this.active = assessor.isActive();
            this.password = assessor.getEncrytedPassword();
            this.confirmPassword = assessor.getEncrytedPassword();
            this.assessorId = assessor.getAssessorID();
    }*/
    
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

	public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
    
    
 
}