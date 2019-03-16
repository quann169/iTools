package com.iToolsV2.controller;
 
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.dao.CompanyDAO;
import com.iToolsV2.dao.MachineDAO;
import com.iToolsV2.dao.RolesAssessorDAO;
import com.iToolsV2.dao.RolesDAO;
import com.iToolsV2.dao.ToolDAO;
import com.iToolsV2.dao.ToolMachineDAO;
import com.iToolsV2.dao.ToolMachineTrayDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.Machine;
import com.iToolsV2.entity.Roles;
import com.iToolsV2.entity.ToolMachineTray;
import com.iToolsV2.entity.Tools;
import com.iToolsV2.entity.ToolsMachine;
import com.iToolsV2.form.AssessorForm;
import com.iToolsV2.form.CompanyForm;
import com.iToolsV2.form.MachineForm;
import com.iToolsV2.form.RolesAssessorForm;
import com.iToolsV2.form.ToolForm;
import com.iToolsV2.form.ToolMachineForm;
import com.iToolsV2.form.TrayForm;
import com.iToolsV2.model.MachineInfo;
import com.iToolsV2.model.ToolInfo;
import com.iToolsV2.pagination.PaginationResult;
import com.iToolsV2.validator.AssessorFormValidator;
import com.iToolsV2.validator.CompanyFormValidator;
import com.iToolsV2.validator.MachineFormValidator;
import com.iToolsV2.validator.ToolFormValidator;
 
@Controller
@Transactional
public class AdminController {
 
    @Autowired
    private AssessorFormValidator assessorFormValidator;
    
    @Autowired
    private CompanyFormValidator companyFormValidator;
    
    @Autowired
    private ToolFormValidator toolFormValidator;
    
    @Autowired
    private MachineFormValidator machineFormValidator;
    
    @Autowired
    private AssessorDAO assessorDAO;
    
    @Autowired
    private CompanyDAO companyDAO;
    
    @Autowired
    private RolesDAO rolesDAO;
    
    @Autowired
    private ToolDAO toolDAO;
    
    @Autowired
    private MachineDAO machineDAO;
    
    @Autowired
    private RolesAssessorDAO rolesAssessorDAO;
    
    @Autowired
    private ToolMachineDAO toolMachineDAO;
    
    @Autowired
    private ToolMachineTrayDAO toolMachineTrayDAO;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        
        if (target.getClass() == AssessorForm.class) {
            dataBinder.setValidator(assessorFormValidator); 
        }
        
        if (target.getClass() == CompanyForm.class) {
            dataBinder.setValidator(companyFormValidator); 
        }
        
        if (target.getClass() == ToolForm.class) {
            dataBinder.setValidator(toolFormValidator); 
        }
        
        if (target.getClass() == MachineForm.class) {
            dataBinder.setValidator(machineFormValidator); 
        }        
    }
 
    @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
    public String login(Model model) {
 
        return "login";
    }
 
    @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {
 
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
 
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
    
    @RequestMapping("/admin/registerAssessorSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerAssessorSuccessfull";
    }
 
    @RequestMapping(value = "/admin/registerUser", method = RequestMethod.GET)
    public String viewRegisterUser(Model model) {
 
    	AssessorForm form = new AssessorForm();
    	List<Company> companies = companyDAO.findAllCompany();
    	List<MachineInfo> machines = machineDAO.findAllMachineInfo();
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                if(assessor != null) {
                	companies = new ArrayList<Company>(); 
                	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
                	machines = machineDAO.findMachineByCompanyCode(assessor.getCompanyCode());
                }
        	}
        }
    	form.setActive(true);
        model.addAttribute("assessorForm", form);
        model.addAttribute("companies", companies);
        model.addAttribute("machines", machines);
 
        return "registerAssessor";
    }
    
    @RequestMapping(value = "/admin/assessorDetail", method = RequestMethod.GET)
    public String editAssessor(Model model, @RequestParam("assessorID") int assessorID) {
    	AssessorForm form = assessorDAO.findAssessorFormByID(assessorID);
    	String currentMachine  = "";
    	if(form.getMachinesID() != null && !form.getMachinesID().equals("")) {
    		if(form.getMachinesID().contains(";")) {
    			String[] listMachineCode = form.getMachinesID().split(";");
    			for (int i = 0; i < listMachineCode.length; i++) {
    				Machine thisMachine = machineDAO.findMachine(listMachineCode[i]);
    				if(thisMachine != null)
    					currentMachine = currentMachine + thisMachine.getMachineName() + " ; ";
    			}
    			if(currentMachine.length() > 3)
    				form.setCurrentMachine(currentMachine.substring(0, currentMachine.length() - 3));
    		} else {
    			Machine thisMachine = machineDAO.findMachine(form.getMachinesID());
    			if(thisMachine != null)
    				form.setCurrentMachine(thisMachine.getMachineName());
    			else
    				form.setCurrentMachine("");
    		}
    	} else {
    		form.setCurrentMachine("NONE.");
    	}
    	List<Company> companies = companyDAO.findAllCompany();
    	List<MachineInfo> machines = machineDAO.findAllMachineInfo();
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
    	Assessor loginAssessor = null;
    	Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
        		if(loginAssessor != null) {
        			if(loginAssessor.getCompanyCode().equalsIgnoreCase(form.getCompanyCode())) {
        				Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
    	                if(assessor != null) {
    	                	companies = new ArrayList<Company>(); 
    	                	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
    	                	machines = machineDAO.findMachineByCompanyCode(assessor.getCompanyCode());
    	                }
        			} else {
		        		model.addAttribute("errorMessage", "Error: You cannot view this page!!!");
		        		return "/error";
		        	}
        		} else {
	        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
	                if(assessor != null) {
	                	companies = new ArrayList<Company>(); 
	                	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
	                	machines = machineDAO.findMachineByCompanyCode(assessor.getCompanyCode());
	                }
        		}
        	}
        }
        model.addAttribute("assessorForm", form);
        model.addAttribute("companies", companies);
        model.addAttribute("machines", machines);
 
        return "assessorDetail";
    }

    @RequestMapping(value = "/admin/registerUser", method = RequestMethod.POST)
    public String saveRegisterUser(Model model, //
            @ModelAttribute("assessorForm") @Validated AssessorForm assessorForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
        	List<Company> companies = companyDAO.findAllCompany();
        	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
            Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
            for (GrantedAuthority role : roleList) {
            	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
            		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                    if(assessor != null) {
                    	companies = new ArrayList<Company>(); 
                    	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
                    }
            	}
            }
        	model.addAttribute("companies", companies);
            return "registerAssessor";
        }
        Assessor newAssessor= null;
        try {
        	newAssessor = assessorDAO.createAssessor(assessorForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());            
            List<Company> companies = companyDAO.findAllCompany();
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
            Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
            for (GrantedAuthority role : roleList) {
            	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
            		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                    if(assessor != null) {
                    	companies = new ArrayList<Company>(); 
                    	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
                    }
            	}
            }
        	model.addAttribute("companies", companies);
            return "registerAssessor";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newAssessor);
        System.out.println(assessorForm.getAssessorId());
        System.out.println(assessorForm.getName());
        System.out.println(assessorForm.isActive());
        System.out.println(assessorForm.isLocked());
        System.out.println(assessorForm.getPassword());
         
        return "redirect:/admin/registerAssessorSuccessful";
    }
    
    @RequestMapping(value = "/admin/assessorDetail", method = RequestMethod.POST)
    public String saveAssessor(Model model, //
            @ModelAttribute("assessorForm") @Validated AssessorForm assessorForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {

    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Assessor loginAssessor = null;
    	List<MachineInfo> machines = machineDAO.findAllMachineInfo();
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());                
        	}
        }
        // Validate result
        if (result.hasErrors()) {
        	List<Company> companies = companyDAO.findAllCompany();
        	if(loginAssessor != null) {
            	companies = new ArrayList<Company>(); 
            	companies.add(companyDAO.findCompanyByCode(loginAssessor.getCompanyCode()));
            	machines = machineDAO.findMachineByCompanyCode(assessorForm.getCompanyCode());
            }
        	model.addAttribute("companies", companies);
        	model.addAttribute("machines", machines);
            return "assessorDetail";
        }
        Assessor newAssessor= null;
        try {
        	if(loginAssessor != null) {
	        	AssessorForm oldForm = assessorDAO.findAssessorFormByID(assessorForm.getAssessorId());
	        	if (oldForm != null) {
	        		if(loginAssessor.getCompanyCode().equalsIgnoreCase(oldForm.getCompanyCode())) {
	        			String oldMachineList = oldForm.getMachinesID();
	        			String updatedMachineList = assessorForm.getMachinesID();
	        			if(updatedMachineList != null && !updatedMachineList.equals(""))
	        				updatedMachineList = updatedMachineList.replaceAll(",", ";");
	        			if(oldMachineList != null && (updatedMachineList == null || updatedMachineList.equals("")))
	        				assessorForm.setMachinesID(oldMachineList);
	        			else
	        				assessorForm.setMachinesID(updatedMachineList);
	        			newAssessor = assessorDAO.saveAssessor(assessorForm);
	        		} else {
		        		model.addAttribute("errorMessage", "Error: You cannot view this page!!!");
		        		return "/error";
		        	}
	        	} else {
	        		model.addAttribute("errorMessage", "Error: You cannot view this page!!!");
	        		return "/error";
	        	}
        	} else {
        		AssessorForm oldForm = assessorDAO.findAssessorFormByID(assessorForm.getAssessorId());
        		String oldMachineList = "";
        		oldMachineList = oldForm.getMachinesID();
    			String updatedMachineList = "";
    			updatedMachineList = assessorForm.getMachinesID();
    			if(updatedMachineList != null && !updatedMachineList.equals(""))
    				updatedMachineList = updatedMachineList.replaceAll(",", ";");
    			if(oldMachineList != null && !oldMachineList.equals("") && (updatedMachineList == null || updatedMachineList.equals("")))
    				assessorForm.setMachinesID(oldMachineList);
    			else
    				assessorForm.setMachinesID(updatedMachineList);
        		newAssessor = assessorDAO.saveAssessor(assessorForm);
        	}
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            List<Company> companies = companyDAO.findAllCompany();
            if(loginAssessor != null) {
            	companies = new ArrayList<Company>(); 
            	companies.add(companyDAO.findCompanyByCode(loginAssessor.getCompanyCode()));
            	machines = machineDAO.findMachineByCompanyCode(assessorForm.getCompanyCode());
            }
        	model.addAttribute("companies", companies);
        	model.addAttribute("machines", machines);
            return "assessorDetail";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newAssessor);
        System.out.println(assessorForm.getAssessorId());
        System.out.println(assessorForm.getName());
        System.out.println(assessorForm.isActive());
        System.out.println(assessorForm.isLocked());
        //System.out.println(assessorForm.getPassword());
         
        return "redirect:/userList";
    }
    
    @RequestMapping(value = "/admin/setRoleAssessor", method = RequestMethod.GET)
    public String setRoleAssessor(Model model, @RequestParam("assessorID") int assessorID) {
    	AssessorForm form = assessorDAO.findAssessorFormByID(assessorID);
    	
    	List<Roles> currentRoles = rolesDAO.findCurrentRoles(assessorID);
    	List<Roles> roles = null;
    	if(currentRoles == null || currentRoles.size() == 0)
    		roles = rolesDAO.findAllRoles(assessorID);
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                if(assessor != null) {
                	if(currentRoles == null || currentRoles.size() == 0)
                		roles = rolesDAO.findRolesNotAdmin(assessorID); 
                }
        	}
        }
    	RolesAssessorForm raForm = new RolesAssessorForm();
    	raForm.setAssessorID(assessorID);
    	raForm.setUserName(form.getName());
        model.addAttribute("raForm", raForm);
        model.addAttribute("roles", roles);
 
        return "setRoleAssessor";
    }

    
    @RequestMapping(value = "/admin/setRoleAssessor", method = RequestMethod.POST)
    public String setUserRoles(Model model, //
            @ModelAttribute("raForm") @Validated RolesAssessorForm raForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
    	
    	List<String> returnResult = null;
        // Validate result
        if (result.hasErrors()) {
            return "setRoleAssessor";
        }
        try {
        	System.out.println(raForm.getUserName());
        	returnResult = rolesAssessorDAO.saveRoleAssessor(raForm);
        	if(returnResult != null) {
        		if(returnResult.size() == 0)
        			returnResult = null;
        	}
        	if(returnResult != null) {
	        	for (String resultS : returnResult){
	        		System.out.println("Success assign roles " + resultS + " for user " + raForm.getUserName());
	        	}
        	}
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "setRoleAssessor";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", returnResult);
         
        return "redirect:/admin/setUserRolesSuccessfull";
    }
    
    @RequestMapping("/admin/setUserRolesSuccessfull")
    public String viewUserRolesSuccessfull(Model model) {
        return "setUserRolesSuccessfull";
    }
    
    @RequestMapping(value = "/admin/removeRoleAssessor", method = RequestMethod.GET)
    public String removeRoleAssessor(Model model, @RequestParam("assessorID") int assessorID) {
    	AssessorForm form = assessorDAO.findAssessorFormByID(assessorID);
    	
    	List<Roles> currentRoles = rolesDAO.findCurrentRoles(assessorID);
    	if(currentRoles != null || currentRoles.size() > 0) {
    		rolesDAO.removeCurrentRoles(assessorID);
    	}
    	
        return "redirect:/userList";
    }
    
    @RequestMapping(value = "/admin/registerCompany", method = RequestMethod.GET)
    public String viewRegisterCompany(Model model) {
 
    	CompanyForm form = new CompanyForm();
 
        model.addAttribute("companyForm", form);
 
        return "registerCompany";
    }
    
    @RequestMapping(value = "/admin/registerCompany", method = RequestMethod.POST)
    public String saveRegisterCompany(Model model, //
            @ModelAttribute("companyForm") @Validated CompanyForm companyForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
            return "registerCompany";
        }
        Company newCompany= null;
        try {
        	newCompany = companyDAO.createCompany(companyForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerCompany";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newCompany);
         
        return "redirect:/admin/registerCompanySuccessful";
    }
    
    @RequestMapping("/admin/registerCompanySuccessful")
    public String viewRegisterCompanySuccessful(Model model) {
        return "registerCompanySuccessfull";
    }
    
    @RequestMapping(value = "/admin/companyDetail", method = RequestMethod.GET)
    public String editCompany(Model model, @RequestParam("companyId") int companyId) {
    	CompanyForm form = companyDAO.findCompanyFormByID(companyId);
 
        model.addAttribute("companyForm", form);
 
        return "companyDetail";
    }
    
    @RequestMapping(value = "/admin/companyDetail", method = RequestMethod.POST)
    public String saveCompany(Model model, //
            @ModelAttribute("companyForm") @Validated CompanyForm companyForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
            return "companyDetail";
        }
        Company newCompany= null;
        try {
        	newCompany = companyDAO.saveCompany(companyForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "companyDetail";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newCompany);
         
        return "redirect:/companyList";
    }
    
    @RequestMapping(value = "/admin/registerTool", method = RequestMethod.GET)
    public String viewRegisterTool(Model model) {
 
    	ToolForm form = new ToolForm();
    	//List<Machine> machines = machineDAO.findAll();
    	List<Company> companies = companyDAO.findAllCompany();
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                if(assessor != null) {
                	companies = new ArrayList<Company>(); 
                	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
                }
        	}
        }
    	form.setActive(true);
        model.addAttribute("toolForm", form);
        //model.addAttribute("machines", machines);
        model.addAttribute("companies", companies);
        
        return "registerTool";
    }
    
    @RequestMapping(value = "/admin/registerTool", method = RequestMethod.POST)
    public String saveRegisterTool(Model model, //
            @ModelAttribute("toolForm") @Validated ToolForm toolForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
            return "registerTool";
        }
        Tools newTool= null;
        try {
        	newTool = toolDAO.creatTool(toolForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerTool";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newTool);
         
        return "redirect:/admin/registerToolSuccessful";
    }
    
    @RequestMapping("/admin/registerToolSuccessful")
    public String viewRegisterToolSuccessful(Model model) {
        return "registerToolSuccessfull";
    }
    
    @RequestMapping(value = "/admin/searchTool", method = RequestMethod.POST)
    public String searchTool(@RequestParam(value = "name", required = false) String toolID, Model model) {       
        final int maxResult = 100;
        final int maxNavigationPage = 100;
        System.out.println(toolID);
        String companyCode = "";
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                if(assessor != null) {
                	companyCode = assessor.getCompanyCode();
                }
        	}
        }
        PaginationResult<ToolInfo> result = null;
        if (companyCode.equals("")) {
	        result = toolDAO.queryTool(1, //
	                maxResult, maxNavigationPage, toolID);
        } else {
        	result = toolDAO.queryTool(1, //
	                maxResult, maxNavigationPage, toolID, companyCode);
        }
        model.addAttribute("name", toolID);
        model.addAttribute("paginationTool", result);
        return "toolList";
    }
    
    @RequestMapping(value = "/admin/viewTool", method = RequestMethod.GET)
    public String viewTool(Model model, @RequestParam("toolID") int toolID) {
    	ToolForm form = toolDAO.findToolFormByID(toolID);
 
        model.addAttribute("toolForm", form);
 
        return "viewTool";
    }
    
    @RequestMapping(value = "/admin/toolDetail", method = RequestMethod.GET)
    public String editTool(Model model, @RequestParam("toolID") int toolID) {
    	ToolForm form = toolDAO.findToolFormByID(toolID);
    	List<Company> companies = companyDAO.findAllCompany();
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin")) {
        		Assessor assessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());
                if(assessor != null) {
                	companies = new ArrayList<Company>(); 
                	companies.add(companyDAO.findCompanyByCode(assessor.getCompanyCode()));
                }
        	}
        }
        model.addAttribute("companies", companies);
        model.addAttribute("toolForm", form);
 
        return "toolDetail";
    }
    
    @RequestMapping(value = "/admin/toolDetail", method = RequestMethod.POST)
    public String saveTool(Model model, //
            @ModelAttribute("toolForm") @Validated ToolForm toolForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
            return "toolDetail";
        }
        Tools newTool= null;
        try {
        	newTool = toolDAO.saveTool(toolForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "toolDetail";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newTool);
         
        return "redirect:/ctidList";
    }
    
    @RequestMapping(value = "/admin/assignToolToMachine", method = RequestMethod.GET)
    public String assignToolToMachine(Model model, @RequestParam("toolID") int toolID) {
    	ToolForm form = toolDAO.findToolFormByID(toolID);
    	List<Machine> machines = machineDAO.findAll();
    	ToolMachineForm tmForm = new ToolMachineForm();
    	tmForm.setToolCode(form.getToolCode());
        model.addAttribute("toolForm", tmForm);
        model.addAttribute("machines", machines);
        
        return "assignToolToMachine";
    }
    
    @RequestMapping(value = "/admin/assignToolToMachine", method = RequestMethod.POST)
    public String assignTool(Model model, //
            @ModelAttribute("toolForm") @Validated ToolMachineForm toolForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
    	
    	List<String> returnResult = null;
        // Validate result
        if (result.hasErrors()) {
            return "assignToolToMachine";
        }
        try {
        	System.out.println(toolForm.getMachineCode());
        	returnResult = toolMachineDAO.saveToolMachine(toolForm);
        	if(returnResult != null) {
        		if(returnResult.size() == 0)
        			returnResult = null;
        	}
        	if(returnResult != null) {
	        	for (String resultS : returnResult){
	        		System.out.println("Success assign tool " +  toolForm.getToolCode() + " for machine " + resultS);
	        	}
        	}
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "assignToolToMachine";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", returnResult);
         
        return "redirect:/admin/assignToolToMachineSuccessful";
    }
    
    @RequestMapping("/admin/assignToolToMachineSuccessful")
    public String viewAssignToolToMachineSuccessful(Model model) {
        return "assignToolToMachineSuccessfull";
    }
    
    @RequestMapping(value = "/admin/machineDetail", method = RequestMethod.GET)
    public String editMachine(Model model, @RequestParam("machineID") int machineID) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Assessor loginAssessor = null;
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin") || role.getAuthority().equalsIgnoreCase("ROLE_Accounting")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());                
        	}
        }
        
    	MachineForm form = machineDAO.findMachineFormByID(machineID);
    	if(loginAssessor != null && !(loginAssessor.getCompanyCode().equalsIgnoreCase("Master Company")) && !loginAssessor.getCompanyCode().equalsIgnoreCase(form.getCompanyCode())) {
    		model.addAttribute("errorMessage", "Error: Fail authenticate!!!");
    		return "/error";
    	}
    	List<Company> companies = companyDAO.findAllCompany();
    	Company thisCom = companyDAO.findCompanyByCode(form.getCompanyCode());
    	List<Tools> tools = toolDAO.findToolsByMachineCode(form.getMachineCode());
    	ToolMachineTray toolMachineTray01 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_01");
    	ToolMachineTray toolMachineTray02 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_02");
    	ToolMachineTray toolMachineTray03 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_03");
    	ToolMachineTray toolMachineTray04 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_04");
    	ToolMachineTray toolMachineTray05 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_05");
    	ToolMachineTray toolMachineTray06 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_06");
    	ToolMachineTray toolMachineTray07 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_07");
    	ToolMachineTray toolMachineTray08 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_08");
    	ToolMachineTray toolMachineTray09 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_09");
    	ToolMachineTray toolMachineTray10 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_10");
    	ToolMachineTray toolMachineTray11 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_11");
    	ToolMachineTray toolMachineTray12 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_12");
    	ToolMachineTray toolMachineTray13 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_13");
    	ToolMachineTray toolMachineTray14 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_14");
    	ToolMachineTray toolMachineTray15 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_15");
    	ToolMachineTray toolMachineTray16 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_16");
    	ToolMachineTray toolMachineTray17 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_17");
    	ToolMachineTray toolMachineTray18 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_18");
    	ToolMachineTray toolMachineTray19 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_19");
    	ToolMachineTray toolMachineTray20 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_20");
    	ToolMachineTray toolMachineTray21 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_21");
    	ToolMachineTray toolMachineTray22 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_22");
    	ToolMachineTray toolMachineTray23 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_23");
    	ToolMachineTray toolMachineTray24 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_24");
    	ToolMachineTray toolMachineTray25 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_25");
    	ToolMachineTray toolMachineTray26 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_26");
    	ToolMachineTray toolMachineTray27 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_27");
    	ToolMachineTray toolMachineTray28 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_28");
    	ToolMachineTray toolMachineTray29 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_29");
    	ToolMachineTray toolMachineTray30 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_30");
    	ToolMachineTray toolMachineTray31 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_31");
    	ToolMachineTray toolMachineTray32 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_32");
    	ToolMachineTray toolMachineTray33 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_33");
    	ToolMachineTray toolMachineTray34 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_34");
    	ToolMachineTray toolMachineTray35 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_35");
    	ToolMachineTray toolMachineTray36 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_36");
    	ToolMachineTray toolMachineTray37 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_37");
    	ToolMachineTray toolMachineTray38 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_38");
    	ToolMachineTray toolMachineTray39 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_39");
    	ToolMachineTray toolMachineTray40 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_40");
    	ToolMachineTray toolMachineTray41 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_41");
    	ToolMachineTray toolMachineTray42 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_42");
    	ToolMachineTray toolMachineTray43 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_43");
    	ToolMachineTray toolMachineTray44 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_44");
    	ToolMachineTray toolMachineTray45 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_45");
    	ToolMachineTray toolMachineTray46 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_46");
    	ToolMachineTray toolMachineTray47 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_47");
    	ToolMachineTray toolMachineTray48 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_48");
    	ToolMachineTray toolMachineTray49 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_49");
    	ToolMachineTray toolMachineTray50 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_50");
    	ToolMachineTray toolMachineTray51 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_51");
    	ToolMachineTray toolMachineTray52 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_52");
    	ToolMachineTray toolMachineTray53 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_53");
    	ToolMachineTray toolMachineTray54 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_54");
    	ToolMachineTray toolMachineTray55 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_55");
    	ToolMachineTray toolMachineTray56 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_56");
    	ToolMachineTray toolMachineTray57 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_57");
    	ToolMachineTray toolMachineTray58 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_58");
    	ToolMachineTray toolMachineTray59 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_59");
    	ToolMachineTray toolMachineTray60 = toolMachineTrayDAO.findTMTByMachineCodeAndTrayIndex(form.getMachineCode(),"TRAY_60");
    	TrayForm trayForm = new TrayForm();
    	trayForm.setMachineCode(form.getMachineCode());
    	
    	if(toolMachineTray01 != null) {
    		trayForm.setQuantity01(toolMachineTray01.getQuantity());
    		trayForm.setTray01(toolMachineTray01.getToolCode());
    		/*ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray01.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray01(tm.getToolCode());
    		}*/
    	}
    	if(toolMachineTray02 != null) {
    		trayForm.setQuantity02(toolMachineTray02.getQuantity());
    		trayForm.setTray02(toolMachineTray02.getToolCode());
    		/*ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray02.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray02(tm.getToolCode());
    		}*/
    	}
    	if(toolMachineTray03 != null) {
    		trayForm.setQuantity03(toolMachineTray03.getQuantity());
    		trayForm.setTray03(toolMachineTray03.getToolCode());
    		/*ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray03.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray03(tm.getToolCode());
    		}*/
    	}
    	if(toolMachineTray04 != null) {
    		trayForm.setQuantity04(toolMachineTray04.getQuantity());
    		trayForm.setTray04(toolMachineTray04.getToolCode());
    	}
    	if(toolMachineTray05 != null) {
    		trayForm.setQuantity05(toolMachineTray05.getQuantity());
    		trayForm.setTray05(toolMachineTray05.getToolCode());
    	}
    	if(toolMachineTray06 != null) {
    		trayForm.setQuantity06(toolMachineTray06.getQuantity());
    		trayForm.setTray06(toolMachineTray06.getToolCode());
    	}
    	if(toolMachineTray07 != null) {
    		trayForm.setQuantity07(toolMachineTray07.getQuantity());
    		trayForm.setTray07(toolMachineTray07.getToolCode());
    	}
    	if(toolMachineTray08 != null) {
    		trayForm.setQuantity08(toolMachineTray08.getQuantity());
    		trayForm.setTray08(toolMachineTray08.getToolCode());
    	}
    	if(toolMachineTray09 != null) {
    		trayForm.setQuantity09(toolMachineTray09.getQuantity());
    		trayForm.setTray09(toolMachineTray09.getToolCode());
    	}
    	if(toolMachineTray10 != null) {
    		trayForm.setQuantity10(toolMachineTray10.getQuantity());
    		trayForm.setTray10(toolMachineTray10.getToolCode());
    	}
    	
    	if(toolMachineTray11 != null) {
    		trayForm.setQuantity11(toolMachineTray11.getQuantity());
    		trayForm.setTray11(toolMachineTray11.getToolCode());
    	}
    	if(toolMachineTray12 != null) {
    		trayForm.setQuantity12(toolMachineTray12.getQuantity());
    		trayForm.setTray12(toolMachineTray12.getToolCode());
    	}
    	if(toolMachineTray13 != null) {
    		trayForm.setQuantity13(toolMachineTray13.getQuantity());
    		trayForm.setTray13(toolMachineTray13.getToolCode());
    	}
    	if(toolMachineTray14 != null) {
    		trayForm.setQuantity14(toolMachineTray14.getQuantity());
    		trayForm.setTray14(toolMachineTray14.getToolCode());
    	}
    	if(toolMachineTray15 != null) {
    		trayForm.setQuantity15(toolMachineTray15.getQuantity());
    		trayForm.setTray15(toolMachineTray15.getToolCode());
    	}
    	if(toolMachineTray16 != null) {
    		trayForm.setQuantity16(toolMachineTray16.getQuantity());
    		trayForm.setTray16(toolMachineTray16.getToolCode());
    	}
    	if(toolMachineTray17 != null) {
    		trayForm.setQuantity17(toolMachineTray17.getQuantity());
    		trayForm.setTray17(toolMachineTray17.getToolCode());
    	}
    	if(toolMachineTray18 != null) {
    		trayForm.setQuantity18(toolMachineTray18.getQuantity());
    		trayForm.setTray18(toolMachineTray18.getToolCode());
    	}
    	if(toolMachineTray19 != null) {
    		trayForm.setQuantity19(toolMachineTray19.getQuantity());
    		trayForm.setTray19(toolMachineTray19.getToolCode());
    	}
    	if(toolMachineTray20 != null) {
    		trayForm.setQuantity20(toolMachineTray20.getQuantity());
    		trayForm.setTray20(toolMachineTray20.getToolCode());
    	}
    	
    	if(toolMachineTray21 != null) {
    		trayForm.setQuantity21(toolMachineTray21.getQuantity());
    		trayForm.setTray21(toolMachineTray21.getToolCode());
    	}
    	if(toolMachineTray22 != null) {
    		trayForm.setQuantity22(toolMachineTray22.getQuantity());
    		trayForm.setTray22(toolMachineTray22.getToolCode());
    	}
    	if(toolMachineTray23 != null) {
    		trayForm.setQuantity23(toolMachineTray23.getQuantity());
    		trayForm.setTray23(toolMachineTray23.getToolCode());
    	}
    	if(toolMachineTray24 != null) {
    		trayForm.setQuantity24(toolMachineTray24.getQuantity());
    		trayForm.setTray24(toolMachineTray24.getToolCode());
    	}
    	if(toolMachineTray25 != null) {
    		trayForm.setQuantity25(toolMachineTray25.getQuantity());
    		trayForm.setTray25(toolMachineTray25.getToolCode());
    	}
    	if(toolMachineTray26 != null) {
    		trayForm.setQuantity26(toolMachineTray26.getQuantity());
    		trayForm.setTray26(toolMachineTray26.getToolCode());
    	}
    	if(toolMachineTray27 != null) {
    		trayForm.setQuantity27(toolMachineTray27.getQuantity());
    		trayForm.setTray27(toolMachineTray27.getToolCode());
    	}
    	if(toolMachineTray28 != null) {
    		trayForm.setQuantity28(toolMachineTray28.getQuantity());
    		trayForm.setTray28(toolMachineTray28.getToolCode());
    	}
    	if(toolMachineTray29 != null) {
    		trayForm.setQuantity29(toolMachineTray29.getQuantity());
    		trayForm.setTray29(toolMachineTray29.getToolCode());
    	}
    	if(toolMachineTray30 != null) {
    		trayForm.setQuantity30(toolMachineTray30.getQuantity());
    		trayForm.setTray30(toolMachineTray30.getToolCode());
    	}
    	
    	if(toolMachineTray31 != null) {
    		trayForm.setQuantity31(toolMachineTray31.getQuantity());
    		trayForm.setTray31(toolMachineTray31.getToolCode());
    	}
    	if(toolMachineTray32 != null) {
    		trayForm.setQuantity32(toolMachineTray32.getQuantity());
    		trayForm.setTray32(toolMachineTray32.getToolCode());
    	}
    	if(toolMachineTray33 != null) {
    		trayForm.setQuantity33(toolMachineTray33.getQuantity());
    		trayForm.setTray33(toolMachineTray33.getToolCode());
    	}
    	if(toolMachineTray34 != null) {
    		trayForm.setQuantity34(toolMachineTray34.getQuantity());
    		trayForm.setTray34(toolMachineTray34.getToolCode());
    	}
    	if(toolMachineTray35 != null) {
    		trayForm.setQuantity35(toolMachineTray35.getQuantity());
    		trayForm.setTray35(toolMachineTray35.getToolCode());
    	}
    	if(toolMachineTray36 != null) {
    		trayForm.setQuantity36(toolMachineTray36.getQuantity());
    		trayForm.setTray36(toolMachineTray36.getToolCode());
    	}
    	if(toolMachineTray37 != null) {
    		trayForm.setQuantity37(toolMachineTray37.getQuantity());
    		trayForm.setTray37(toolMachineTray37.getToolCode());
    	}
    	if(toolMachineTray38 != null) {
    		trayForm.setQuantity38(toolMachineTray38.getQuantity());
    		trayForm.setTray38(toolMachineTray38.getToolCode());
    	}
    	if(toolMachineTray39 != null) {
    		trayForm.setQuantity39(toolMachineTray39.getQuantity());
    		trayForm.setTray39(toolMachineTray39.getToolCode());
    	}
    	if(toolMachineTray40 != null) {
    		trayForm.setQuantity40(toolMachineTray40.getQuantity());
    		trayForm.setTray40(toolMachineTray40.getToolCode());
    	}
    	
    	if(toolMachineTray41 != null) {
    		trayForm.setQuantity41(toolMachineTray41.getQuantity());
    		trayForm.setTray41(toolMachineTray41.getToolCode());
    	}
    	if(toolMachineTray42 != null) {
    		trayForm.setQuantity42(toolMachineTray42.getQuantity());
    		trayForm.setTray42(toolMachineTray42.getToolCode());
    	}
    	if(toolMachineTray43 != null) {
    		trayForm.setQuantity43(toolMachineTray43.getQuantity());
    		trayForm.setTray43(toolMachineTray43.getToolCode());
    	}
    	if(toolMachineTray44 != null) {
    		trayForm.setQuantity44(toolMachineTray44.getQuantity());
    		trayForm.setTray44(toolMachineTray44.getToolCode());
    	}
    	if(toolMachineTray45 != null) {
    		trayForm.setQuantity45(toolMachineTray45.getQuantity());
    		trayForm.setTray45(toolMachineTray45.getToolCode());
    	}
    	if(toolMachineTray46 != null) {
    		trayForm.setQuantity46(toolMachineTray46.getQuantity());
    		trayForm.setTray46(toolMachineTray46.getToolCode());
    	}
    	if(toolMachineTray47 != null) {
    		trayForm.setQuantity47(toolMachineTray47.getQuantity());
    		trayForm.setTray47(toolMachineTray47.getToolCode());
    	}
    	if(toolMachineTray48 != null) {
    		trayForm.setQuantity48(toolMachineTray48.getQuantity());
    		trayForm.setTray48(toolMachineTray48.getToolCode());
    	}
    	if(toolMachineTray49 != null) {
    		trayForm.setQuantity49(toolMachineTray49.getQuantity());
    		trayForm.setTray49(toolMachineTray49.getToolCode());
    	}
    	if(toolMachineTray50 != null) {
    		trayForm.setQuantity50(toolMachineTray50.getQuantity());
    		trayForm.setTray50(toolMachineTray50.getToolCode());
    	}
    	
    	if(toolMachineTray51 != null) {
    		trayForm.setQuantity51(toolMachineTray51.getQuantity());
    		trayForm.setTray51(toolMachineTray51.getToolCode());
    	}
    	if(toolMachineTray52 != null) {
    		trayForm.setQuantity52(toolMachineTray52.getQuantity());
    		trayForm.setTray52(toolMachineTray52.getToolCode());
    	}
    	if(toolMachineTray53 != null) {
    		trayForm.setQuantity53(toolMachineTray53.getQuantity());
    		trayForm.setTray53(toolMachineTray53.getToolCode());
    	}
    	if(toolMachineTray54 != null) {
    		trayForm.setQuantity54(toolMachineTray54.getQuantity());
    		trayForm.setTray54(toolMachineTray54.getToolCode());
    	}
    	if(toolMachineTray55 != null) {
    		trayForm.setQuantity55(toolMachineTray55.getQuantity());
    		trayForm.setTray55(toolMachineTray55.getToolCode());
    	}
    	if(toolMachineTray56 != null) {
    		trayForm.setQuantity56(toolMachineTray56.getQuantity());
    		trayForm.setTray56(toolMachineTray56.getToolCode());
    	}
    	if(toolMachineTray57 != null) {
    		trayForm.setQuantity57(toolMachineTray57.getQuantity());
    		trayForm.setTray57(toolMachineTray57.getToolCode());
    	}
    	if(toolMachineTray58 != null) {
    		trayForm.setQuantity58(toolMachineTray58.getQuantity());
    		trayForm.setTray58(toolMachineTray58.getToolCode());
    	}
    	if(toolMachineTray59 != null) {
    		trayForm.setQuantity59(toolMachineTray59.getQuantity());
    		trayForm.setTray59(toolMachineTray59.getToolCode());
    	}
    	if(toolMachineTray60 != null) {
    		trayForm.setQuantity60(toolMachineTray60.getQuantity());
    		trayForm.setTray60(toolMachineTray60.getToolCode());
    	}
    	
        model.addAttribute("machineForm", form);
    	model.addAttribute("companies", companies);
    	model.addAttribute("tools", tools);
    	model.addAttribute("trayForm", trayForm);
    	model.addAttribute("thisCom", thisCom);
        return "machineDetail";
    }  
    
    @RequestMapping(value = "/admin/machineDetail", method = RequestMethod.POST)
    public String saveMachine(Model model, //
            @ModelAttribute("machineForm") @Validated MachineForm machineForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
    	
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Assessor loginAssessor = null;
        Collection<? extends GrantedAuthority> roleList= userDetails.getAuthorities();
        for (GrantedAuthority role : roleList) {
        	if(role.getAuthority().equalsIgnoreCase("ROLE_SubAdmin") || role.getAuthority().equalsIgnoreCase("ROLE_Accounting")) {
        		loginAssessor = assessorDAO.findAccount(userDetails.getUsername().toLowerCase());                
        	}
        }
        
    	MachineForm oldform = machineDAO.findMachineFormByID(machineForm.getMachineID());
    	if(loginAssessor != null && oldform != null && !(loginAssessor.getCompanyCode().equalsIgnoreCase("Master Company")) && !loginAssessor.getCompanyCode().equalsIgnoreCase(oldform.getCompanyCode())) {
    		model.addAttribute("errorMessage", "Error: Fail authenticate!!!");
    		return "/error";
    	}
    	if(loginAssessor != null && oldform != null) {
    		machineForm.setActive(oldform.isActive());
    		machineForm.setCompanyCode(oldform.getCompanyCode());
    	}
        
        // Validate result
        if (result.hasErrors()) {
            return "machineDetail";
        }
        Machine newMachine= null;
        try {
        	newMachine = machineDAO.saveMachine(machineForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "machineDetail";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newMachine);
         
        return "redirect:/machineList";
    }
    
    @RequestMapping(value = "/admin/registerMachine", method = RequestMethod.GET)
    public String viewRegisterMachine(Model model) {
 
    	MachineForm form = new MachineForm();
    	form.setActive(true);
        model.addAttribute("machineForm", form);

        return "registerMachine";
    }
    
    @RequestMapping(value = "/admin/registerMachine", method = RequestMethod.POST)
    public String saveRegisterMachine(Model model, //
            @ModelAttribute("machineForm") @Validated MachineForm machineForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
            return "registerMachine";
        }
        Machine newMachine= null;
        try {
        	newMachine = machineDAO.createMachine(machineForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "registerMachine";
        }
 
        redirectAttributes.addFlashAttribute("flashUser", newMachine);
         
        return "redirect:/admin/registerMachineSuccessfull";
    }
    
    @RequestMapping("/admin/registerMachineSuccessfull")
    public String viewRegisterMachineSuccessfull(Model model) {
        return "registerMachineSuccessfull";
    }
}