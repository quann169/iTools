package com.iToolsV2.model;
 
import java.util.Objects;

import com.iToolsV2.entity.Machine;
 
public class MachineInfo {
	private int machineID;
	private String machineCode;
    private String machineName;
    private String model;
    private String location;
    private String description;
    private boolean active;
 
    public MachineInfo() {
    }
 
    public MachineInfo(Machine machine) {
        this.machineCode = machine.getMachineCode();
        this.machineName = machine.getMachineName();
        this.model = machine.getModel();
        this.location = machine.getLocation();
        this.description = machine.getDescription();
        this.active = machine.isActive();
        this.machineID = machine.getMachineID();
    }
 
    public MachineInfo(String machineCode, String machineName, boolean active) {
    	this.machineCode = machineCode;
        this.machineName = machineName;
        //this.model = machine.getModel();
        //this.location = machine.getLocation();
        //this.description = machine.getDescription();
        this.active = active;
        //this.machineID = machine.getMachineID();
    }
    
    public MachineInfo(int machineID, String machineCode, String machineName, boolean active) {
    	this.machineID = machineID;
    	this.machineCode = machineCode;
        this.machineName = machineName;
        //this.model = machine.getModel();
        //this.location = machine.getLocation();
        //this.description = machine.getDescription();
        this.active = active;
        //this.machineID = machine.getMachineID();
    }

	public int getMachineID() {
		return machineID;
	}

	public void setMachineID(int machineID) {
		this.machineID = machineID;
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MachineInfo other = (MachineInfo) obj;
		if (!Objects.equals(this.machineCode, other.machineCode)) {
			return false;
		}
		return true;
	}
}