package com.iToolsV2.form;
 
import com.iToolsV2.model.CompanyInfo;
 
public class CompanyForm {
 
	private int companyId;
    private String companyName;
    private String companyCode;
    private String address;
    private String location;
 
    public CompanyForm() {
 
    }
 
    public CompanyForm(CompanyInfo companyInfo) {
        if (companyInfo != null) {
            this.companyId = companyInfo.getCompanyId();
            this.companyName = companyInfo.getCompanyName();
            this.companyCode = companyInfo.getCompanyCode();
            this.address = companyInfo.getAddress();
            this.location = companyInfo.getLocation();
        }
    }

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
 
}