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
    @Column(name = "ToolID", nullable = false)
    private int ToolID;
 
    @Column(name = "ToolCode", length = 100, nullable = true)
    private String toolCode;
    
    @Column(name = "Model", length = 100, nullable = true)
    private String model;
    
    @Column(name = "Barcode", length = 100, nullable = true)
    private String barcode;
    
    @Column(name = "Description", length = 100, nullable = true)
    private String description;
    
    @Column(name = "CreatedDate", nullable = true)
    private Date createdDate;
    
    @Column(name = "UpdatedDate", nullable = true)
    private Date updatedDate;

	@Column(name = "IsActive", length = 1, nullable = false)
    private boolean active;
	
	public int getToolID() {
		return ToolID;
	}

	public void setToolID(int toolID) {
		ToolID = toolID;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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