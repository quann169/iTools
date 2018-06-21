package com.iToolsV2.form;
 
import com.iToolsV2.entity.Machine;
 
public class MachineForm {
    private String machineCode;
    private String machineName;
    private boolean active;
 
    private boolean newMachine = false;
 
    public MachineForm() {
        this.newMachine= true;
    }
 
    public MachineForm(Machine machine) {
        this.machineCode = machine.getMachineCode();
        this.machineName = machine.getMachineName();
        this.active = machine.isActive();
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