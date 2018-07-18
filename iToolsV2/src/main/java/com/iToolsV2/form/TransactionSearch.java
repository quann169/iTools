package com.iToolsV2.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionSearch {
	private String companyCode;
	private String toolCode; /*CTIID*/
	private int userId;
	private String transactionType;
	private String machineCode;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date fromDate;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date toDate;
	private String tray;
}
