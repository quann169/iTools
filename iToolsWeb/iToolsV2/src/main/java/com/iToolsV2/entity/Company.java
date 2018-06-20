package com.iToolsV2.entity;
 
import java.io.Serializable;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "Company")
public class Company implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1439300125994840601L;

	@Id
    @Column(name = "CompanyID", nullable = false)
    private int companyID;
 
    @Column(name = "CompanyCode", length = 100, nullable = false)
    private String companyCode;
 
    @Column(name = "CompanyName", length = 100, nullable = true)
    private String companyName;
    
    @Column(name = "CompanyType", length = 100, nullable = true)
    private String companyType;
    
    @Column(name = "Address", length = 100, nullable = true)
    private String address;
    
    @Column(name = "Location", length = 100, nullable = true)
    private String location;

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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