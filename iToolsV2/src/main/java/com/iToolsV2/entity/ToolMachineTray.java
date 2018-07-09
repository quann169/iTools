package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "ToolsMachineTray")
public class ToolMachineTray implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5840111934473135619L;
 
	@Id
    @Column(name = "ToolsMachineTrayID", nullable = false)
    private int toolsMachineTrayID;
 
	@Column(name = "ToolsMachineID", nullable = false)
    private int toolsMachineID;
 
    @Column(name = "TrayIndex", length = 100, nullable = true)
    private String trayIndex;
    
    @Column(name = "Quantity", nullable = true)
    private int quantity;
    
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;
    
    @Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
    
    public int getToolsMachineTrayID() {
		return toolsMachineTrayID;
	}

	public void setToolsMachineTrayID(int toolsMachineTrayID) {
		this.toolsMachineTrayID = toolsMachineTrayID;
	}

	public int getToolsMachineID() {
		return toolsMachineID;
	}

	public void setToolsMachineID(int toolsMachineID) {
		this.toolsMachineID = toolsMachineID;
	}

	public String getTrayIndex() {
		return trayIndex;
	}

	public void setTrayIndex(String trayIndex) {
		this.trayIndex = trayIndex;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
 
}