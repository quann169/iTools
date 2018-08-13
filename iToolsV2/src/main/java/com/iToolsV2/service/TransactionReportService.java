package com.iToolsV2.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.iToolsV2.form.TransactionReport;
import com.iToolsV2.form.TransactionSearch;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionReportService {
	
	@Value("${itool.sql.getTransaction}")
	private String sqlGetTransaction;
	
	@Autowired
	private DataSource dataSource;
	
	public List<TransactionReport> searchTransaction(TransactionSearch search) throws Exception {
		List<TransactionReport> lstResult = new ArrayList<>();
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			
			int sizeParam = (search.getCompanyCode().isEmpty()? 0 : 1) + (search.getToolCode().isEmpty()? 0 : 1) +
					(search.getUserId() == 0 ? 0 : 1) + (search.getTransactionType().isEmpty()? 0 : 1) + 
					(search.getMachineCode().isEmpty()? 0 : 1) + (search.getTray().isEmpty()? 0 : 1) + 
					(search.getFromDate() == null ? 0 : 1) + (search.getToDate() == null? 0 : 1);
			
			Object[] objectParams = new Object[sizeParam];
			
			String where = "";
			int indexParams = 0;
			if (!search.getCompanyCode().isEmpty()) {
				where += " AND wk.CompanyCode = ?";
				objectParams[indexParams++] = search.getCompanyCode();
				log.info("wk.CompanyCode = " + search.getCompanyCode());
				System.out.println("wk.CompanyCode = " + search.getCompanyCode());
			}
			if (!search.getToolCode().isEmpty()) {
				where += " AND wk.ToolCode = ?";
				objectParams[indexParams++] = search.getToolCode();
				log.info("wk.ToolCode = " + search.getToolCode());
				System.out.println("wk.ToolCode = " + search.getToolCode());
			}
			if (search.getUserId() > 0) {
				where += " AND wk.AssessorID = ?";
				objectParams[indexParams++] = search.getUserId();
				log.info("wk.AssessorID = " + search.getUserId());
				System.out.println("wk.AssessorID = " + search.getUserId());
			}
			if (!search.getTransactionType().isEmpty()) {
				where += " AND wk.TransactionType = ?";
				objectParams[indexParams++] = search.getTransactionType();
				log.info("wk.TransactionType = " + search.getTransactionType());
				System.out.println("wk.TransactionType = " + search.getTransactionType());
			}
			if (!search.getMachineCode().isEmpty()) {
				where += " AND wk.MachineCode = ?";
				objectParams[indexParams++] = search.getMachineCode();
				log.info("wk.MachineCode = " + search.getMachineCode());
				System.out.println("wk.MachineCode = " + search.getMachineCode());
			}
			if (!search.getTray().isEmpty()) {
				where += " AND wk.TrayIndex = ?";
				objectParams[indexParams++] = search.getTray();
				log.info("wk.TrayIndex = " + search.getTray());
				System.out.println("wk.TrayIndex = " + search.getTray());
			}
			if (search.getFromDate() != null) {
				where += " AND wk.TransactionDate >= ?";
				objectParams[indexParams++] = search.getFromDate();
				log.info("wk.TransactionDate >= " + search.getFromDate());
				System.out.println("wk.TransactionDate >= " + search.getFromDate());
			}
			if (search.getToDate() != null) {
				where += " AND wk.TransactionDate <= ?";
				objectParams[indexParams++] = search.getToDate();
				log.info("wk.TransactionDate <= " + search.getToDate());
				System.out.println("wk.TransactionDate <= " + search.getToDate());
			}
			log.info(sqlGetTransaction + where);
			System.out.println(sqlGetTransaction + where);
			
			lstResult = jdbcTemplate.query(sqlGetTransaction + where, objectParams, new RowMapper<TransactionReport>() {
				@Override
				public TransactionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
					TransactionReport report = new TransactionReport();
					report.setTransactionId(rs.getInt("WorkingTransactionID"));
					report.setCompanyName(rs.getString("CompanyName"));
					report.setUserName(rs.getString("UserName"));
					report.setMachineName(rs.getString("MachineName"));
					report.setTray(rs.getString("TrayIndex"));
					report.setToolCode(rs.getString("ToolCode"));
					report.setQuantity(rs.getInt("Quantity"));
					report.setTypeTransaction(rs.getString("TransactionType"));
					report.setTransactionDate(rs.getDate("TransactionDate"));
					report.setTransactionStatus(rs.getString("TransactionStatus"));
					report.setWoCode(rs.getString("WOCode"));
					report.setOpCode(rs.getString("OPCode"));
					return report;
				}
			});
			return lstResult;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
