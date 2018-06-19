package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "Machine")
public class Machine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5861421164557379439L;
	
	@Id
    @Column(name = "MachineID", nullable = false)
    private int machineID;
 
    @Column(name = "Name", length = 100, nullable = false)
    private String machineName;
 
    @Column(name = "MachineCode", length = 100, nullable = true)
    private String machineCode;
    
    @Column(name = "Model", length = 100, nullable = true)
    private String model;
    
    @Column(name = "Location", length = 100, nullable = true)
    private String location;
    
    @Column(name = "Description", length = 100, nullable = true)
    private String description;
    
    @Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;

	public int getMachineID() {
		return machineID;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}