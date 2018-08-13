package com.iToolsV2.form;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionReport {
	private int transactionId;
	private String companyName;
	private String userName;
	private String machineName;
	private String tray;
	private String toolCode;
	private int quantity;
	private String typeTransaction;
	private Date transactionDate;
	private String transactionStatus;
	private String woCode;
	private String opCode;
}
