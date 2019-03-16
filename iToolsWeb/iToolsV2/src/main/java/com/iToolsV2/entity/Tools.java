package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "Tools")
public class Tools implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 9185785625192837289L;

	@Id
    @Column(name = "ToolID", nullable = false)
    private int toolID;
 
    @Column(name = "ToolCode", length = 100, nullable = true)
    private String toolCode;
    
    @Column(name = "CompanyCode", length = 100, nullable = true)
    private String companyCode;
    
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
	
    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getToolID() {
		return toolID;
	}

	public void setToolID(int toolID) {
		this.toolID = toolID;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
 
}