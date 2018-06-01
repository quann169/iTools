package com.models;

public class Tool {

	private int toolId;
	private String toolName;

	/**
	 * @return the toolId
	 */
	public int getToolId() {
		return toolId;
	}

	/**
	 * @param toolId
	 *            the toolId to set
	 */
	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	/**
	 * @return the toolName
	 */
	public String getToolName() {
		return toolName;
	}

	/**
	 * @param toolName
	 *            the toolName to set
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tool [toolId=" + toolId + ", " + (toolName != null ? "toolName=" + toolName : "") + "]";
	}

}
