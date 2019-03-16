package com.iToolsV2.model;
 
import java.util.Date;

import com.iToolsV2.entity.Tools;
 
public class ToolInfo {
    private int toolID;
    private String toolCode;
    private String model;
    private String barcode;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private boolean active;
    private String companyCode;
 
    public ToolInfo() {
    }
 
    public ToolInfo(Tools tool) {
        this.toolID = tool.getToolID();
        this.toolCode = tool.getToolCode();
        this.model = tool.getModel();
        this.barcode = tool.getBarcode();
        this.description = tool.getDescription(); 
        this.createdDate =tool.getCreatedDate();
        this.updatedDate = tool.getUpdatedDate();
        this.active = tool.isActive();
        this.companyCode = tool.getCompanyCode();
    }
 
    public ToolInfo(String toolCode, String description, boolean active) {
    	this.toolCode = toolCode;
        this.description = description; 
        this.active = active;
    }
    
    public ToolInfo(int toolID, String toolCode, String model, String barcode, String description, boolean active, Date createdDate, Date updatedDate) {
    	this.toolID = toolID;
    	this.toolCode = toolCode;
    	this.model= model;
    	this.barcode = barcode;
        this.description = description; 
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
    
    public ToolInfo(int toolID, String toolCode, String companyCode, String model, String barcode, String description, boolean active, Date createdDate, Date updatedDate) {
    	this.toolID = toolID;
    	this.toolCode = toolCode;
    	this.companyCode = companyCode;
    	this.model= model;
    	this.barcode = barcode;
        this.description = description; 
        this.active = active;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
 
}