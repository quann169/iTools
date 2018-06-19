package com.iToolsV2.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "MasterLog")
public class MasterLog implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -6833815154678374357L;

	@Id
    @Column(name = "LogID", nullable = false)
    private int logID;
 
    @Column(name = "AssessorName", length = 100, nullable = false)
    private String assessorName;
 
    @Column(name = "TblName", length = 100, nullable = true)
    private String tblName;
    
    @Column(name = "RecordID", nullable = true)
    private int RecordID;
    
    @Column(name = "ColumnName", length = 100, nullable = true)
    private String columnName;
    
    @Column(name = "OldValue", length = 255, nullable = true)
    private String oldValue;
    
    @Column(name = "NewValue", length = 255, nullable = false)
    private String newValue;
    
    @Column(name = "LogStatus", nullable = true)
    private int logStatus;
    
    @Column(name = "ApprovedBy", length = 255, nullable = true)
    private String approvedBy;
 
    public int getLogID() {
		return logID;
	}

	public void setLogID(int logID) {
		this.logID = logID;
	}

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public int getRecordID() {
		return RecordID;
	}

	public void setRecordID(int recordID) {
		RecordID = recordID;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public int getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(int logStatus) {
		this.logStatus = logStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	@Column(name = "LogDate", nullable = true)
    private Date logDate;
    
    @Column(name = "ApprovedDate", nullable = true)
    private Date approvedDate;
 
}