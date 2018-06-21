package com.iToolsV2.form;
 
import com.iToolsV2.model.AssessorInfo;
 
public class AssessorForm {
 
    private String name;
    private String firstName;
    private String lastName;
    private String address;
    private String emailAddress;
    private String phone;
    private String companyCode;
    private boolean active; 
 
    public AssessorForm() {
 
    }
 
    public AssessorForm(AssessorInfo assessorInfo) {
        if (assessorInfo != null) {
            this.name = assessorInfo.getUserName();
            this.firstName = assessorInfo.getFirstName();
            this.lastName = assessorInfo.getLastName();
            this.address = assessorInfo.getAddress();
            this.emailAddress = assessorInfo.getEmailAddress();
            this.phone = assessorInfo.getPhone();
            this.companyCode = assessorInfo.getCompanyCode();
            this.active = assessorInfo.isActive();
        }
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