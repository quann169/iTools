package com.iToolsV2.form;
 
import java.util.Date;

import com.iToolsV2.entity.Machine;
 
public class MachineForm {
    private String machineCode;
    private String machineName;
    private Integer machineID;
    private boolean active;
    private String model;
    private String location;
    private String description;
    private Date createdDate;
    private Date updatedDate;
    private String companyCode;
 
    private boolean newMachine = false;
 
    public MachineForm() {
        this.newMachine= true;
    }
 
    public MachineForm(Machine machine) {
        this.machineCode = machine.getMachineCode();
        this.machineName = machine.getMachineName();
        this.active = machine.isActive();
    }
 
    public MachineForm(Integer machineID, String machineCode, String machineName,
    					String model, String location, String description, boolean active,
    					Date createdDate, Date updatedDate) {
    	this.machineID = machineID;
    	this.machineCode = machineCode;
        this.machineName = machineName;
        this.model = model;
        this.location = location;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.active = active;
    }
    
    public MachineForm(Integer machineID, String machineCode, String machineName,
			String model, String location, String description, boolean active,
			Date createdDate, Date updatedDate, String companyCode) {
		this.machineID = machineID;
		this.machineCode = machineCode;
		this.machineName = machineName;
		this.model = model;
		this.location = location;
		this.description = description;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.active = active;
		this.companyCode = companyCode;
	}
    
    public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Integer getMachineID() {
		return machineID;
	}

	public void setMachineID(Integer machineID) {
		this.machineID = machineID;
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

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isNewMachine() {
        return newMachine;
    }
 
    public void setNewMachine(boolean newMachine) {
        this.newMachine = newMachine;
    }
 
}