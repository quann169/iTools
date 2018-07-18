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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrayService {
	
	@Value("${itool.sql.getTray}")
	private String sqlGetTray;
	
	@Autowired
	private DataSource dataSource;
	
	public List<String> findTrayByMachineCode(String machineCode) throws Exception {
		List<String> lstResult = new ArrayList<>();
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			Object[] objectParams = new Object[1];
			objectParams[0] = machineCode;
			lstResult = jdbcTemplate.query(sqlGetTray, objectParams, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString(1) == null? "" : rs.getString(1);
				}
			});
			return lstResult;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
