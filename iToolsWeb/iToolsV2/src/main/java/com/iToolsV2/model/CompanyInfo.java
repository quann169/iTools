package com.iToolsV2.model;
 
public class CompanyInfo {
 
    private int companyId;
    private String companyName;
    private String companyCode;
    private String address;
    private String location;
 
    public CompanyInfo() {
 
    }
 
    public CompanyInfo(int companyId, String companyName, String companyCode, String address, String location) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyCode = companyCode;
        this.address = address;
        this.location = location;
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