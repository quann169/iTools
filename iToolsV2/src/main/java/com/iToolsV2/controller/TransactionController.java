package com.iToolsV2.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.form.TransactionReport;
import com.iToolsV2.form.TransactionSearch;
import com.iToolsV2.service.TransactionReportService;
import com.iToolsV2.service.TrayService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TransactionController extends AbstractController {
	
	@Autowired
	private TrayService trayService;
	@Autowired
	private TransactionReportService transactionservice;
	
	@Autowired
    private AssessorDAO assessorDAO;
	
	@GetMapping("/transaction")
	public String getReport(Model model, @ModelAttribute TransactionSearch searchForm) {
		List<TransactionReport> lstTransaction = new ArrayList<>();
		model.addAttribute("transactionSearch", new TransactionSearch());
		
		model.addAttribute("lstTray", null);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Assessor loginAssessor = null;
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin") || role.getAuthority().equalsIgnoreCase("ROLE_Accounting")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());                
        	}
        }
        
        if(loginAssessor != null  && !(loginAssessor.getCompanyCode().equalsIgnoreCase("Master Company"))) {
        	searchForm.setCompanyCode(loginAssessor.getCompanyCode());
    	}
		
		try {
			lstTransaction = transactionservice.searchTransaction(searchForm);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("lstTransaction", lstTransaction);
		
		return "transaction/transaction";
	}
	
	@GetMapping("/getTrayByMachineCode")
    @ResponseBody
	public List<String> getTrayByMachineCode(@RequestParam String machineCode) {
		try {
			return trayService.findTrayByMachineCode(machineCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/transaction/query")
	public String searchTransaction(Model model, @ModelAttribute TransactionSearch searchForm) {
		List<TransactionReport> lstTransaction = new ArrayList<>();
		model.addAttribute("transactionSearch", searchForm);
		
		List<String> lstTray = new ArrayList<>();
		try {
			lstTray = trayService.findTrayByMachineCode(searchForm.getMachineCode());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		model.addAttribute("lstTray", lstTray);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Assessor loginAssessor = null;
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin") || role.getAuthority().equalsIgnoreCase("ROLE_Accounting")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());                
        	}
        }
        
        if(loginAssessor != null  && !(loginAssessor.getCompanyCode().equalsIgnoreCase("Master Company"))) {
        	searchForm.setCompanyCode(loginAssessor.getCompanyCode());
    	}
		
        try {
			lstTransaction = transactionservice.searchTransaction(searchForm);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("lstTransaction", lstTransaction);
		
		return "transaction/transaction";
	}
}
