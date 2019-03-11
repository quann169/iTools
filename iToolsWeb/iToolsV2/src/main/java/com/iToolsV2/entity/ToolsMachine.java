package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "ToolsMachine")
public class ToolsMachine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6431312635422507839L;

	@Id
    @Column(name = "ToolsMachineID", nullable = false)
    private int toolMachineID;
 
    @Column(name = "ToolCode", length = 100, nullable = true)
    private String toolCode;
    
    @Column(name = "MachineCode", length = 100, nullable = true)
    private String machineCode;
    
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;

	@Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getToolMachineID() {
		return toolMachineID;
	}

	public void setToolMachineID(int toolMachineID) {
		this.toolMachineID = toolMachineID;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
 
}