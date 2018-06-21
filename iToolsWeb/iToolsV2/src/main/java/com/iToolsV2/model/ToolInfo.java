package com.iToolsV2.model;
 
import java.util.Date;

import com.iToolsV2.entity.Tools;
 
public class ToolInfo {
    private int toolID;
    private String toolCode;
    private String model;
    private String barcode;
    private String decription;
    private Date createdDate;
    private Date updatedDate;
    private boolean active;
 
    public ToolInfo() {
    }
 
    public ToolInfo(Tools tool) {
        this.toolID = tool.getToolID();
        this.toolCode = tool.getToolCode();
        this.model = tool.getModel();
        this.barcode = tool.getBarcode();
        this.decription = tool.getDescription(); 
        this.createdDate =tool.getCreatedDate();
        this.updatedDate = tool.getUpdatedDate();
        this.active = tool.isActive();
    }
 
    public ToolInfo(String toolCode, String decription, boolean active) {
    	this.toolCode = toolCode;
        this.decription = decription; 
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

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
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