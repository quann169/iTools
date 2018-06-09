package com.models;

public class Machine {

	private String machineCode;
	private String machineName;
	private String machineLocation;

	/**
	 * @param machineCode
	 */
	public Machine(String machineCode) {
		super();
		this.machineCode = machineCode;
	}

	/**
	 * @return the machineCode
	 */
	public String getMachineCode() {
		return machineCode;
	}

	/**
	 * @param machineCode
	 *            the machineCode to set
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	/**
	 * @return the machineName
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * @param machineName
	 *            the machineName to set
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * @return the machineLocation
	 */
	public String getMachineLocation() {
		return machineLocation;
	}

	/**
	 * @param machineLocation
	 *            the machineLocation to set
	 */
	public void setMachineLocation(String machineLocation) {
		this.machineLocation = machineLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Machine [" + (machineCode != null ? "machineCode=" + machineCode + ", " : "")
				+ (machineName != null ? "machineName=" + machineName + ", " : "")
				+ (machineLocation != null ? "machineLocation=" + machineLocation : "") + "]";
	}

}
