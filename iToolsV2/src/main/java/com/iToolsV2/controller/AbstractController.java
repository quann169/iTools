package com.iToolsV2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.dao.CompanyDAO;
import com.iToolsV2.dao.MachineDAO;
import com.iToolsV2.dao.ToolDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.Machine;
import com.iToolsV2.entity.Tools;

@Controller
public class AbstractController {
	@Autowired
	private CompanyDAO companyDao;
	@Autowired
	private ToolDAO toolDao;
	@Autowired
	private AssessorDAO assessorDao;
	@Autowired
	private MachineDAO machineDao;
	
	@ModelAttribute("lstCompany")
	public List<Company> getAllCompany() {
		return companyDao.findAllCompany();
	}
	
	@ModelAttribute("lstTool")
	public List<Tools> getAllTool() {
		return toolDao.findAll();
	}
	
	@ModelAttribute("lstUser")
	public List<Assessor> getAllUser() {
		return assessorDao.findAllAccountID();
	}
	
	@ModelAttribute("lstMachine")
	public List<Machine> getAllMachine() {
		return machineDao.findAll();
	}
}
