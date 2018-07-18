package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "CompanyMachine")
public class CompanyMachine implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -7577067579104501727L;

	@Id
    @Column(name = "CompanyMachineID", nullable = false)
    private int companyMachineID;
 
    @Column(name = "MachineCode", length = 100, nullable = true)
    private String machineCode;
    
    @Column(name = "CompanyCode", length = 100, nullable = true)
    private String companyCode;
 
    @Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getCompanyMachineID() {
		return companyMachineID;
	}

	public void setCompanyMachineID(int companyMachineID) {
		this.companyMachineID = companyMachineID;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
 

 
 
}