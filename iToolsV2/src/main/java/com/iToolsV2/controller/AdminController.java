package com.iToolsV2.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    	form.setActive(true);
        model.addAttribute("assessorForm", form);
        model.addAttribute("companies", companies);
 
        return "registerAssessor";
    }
    
    @RequestMapping(value = "/admin/assessorDetail", method = RequestMethod.GET)
    public String editAssessor(Model model, @RequestParam("assessorID") int assessorID) {
    	AssessorForm form = assessorDAO.findAssessorFormByID(assessorID);
 
    	List<Company> companies = companyDAO.findAllCompany();
 
        model.addAttribute("assessorForm", form);
        model.addAttribute("companies", companies);
 
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
 
        // Validate result
        if (result.hasErrors()) {
        	List<Company> companies = companyDAO.findAllCompany();
        	model.addAttribute("companies", companies);
            return "assessorDetail";
        }
        Assessor newAssessor= null;
        try {
        	newAssessor = assessorDAO.saveAssessor(assessorForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            List<Company> companies = companyDAO.findAllCompany();
        	model.addAttribute("companies", companies);
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
    	List<Roles> roles = rolesDAO.findAllRoles();
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
        	for (String resultS : returnResult){
        		System.out.println("Success assign roles " + resultS + " for user " + raForm.getUserName());
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
    	List<Machine> machines = machineDAO.findAll();
    	form.setActive(true);
        model.addAttribute("toolForm", form);
        model.addAttribute("machines", machines);
        
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
    
    @RequestMapping(value = "/admin/toolDetail", method = RequestMethod.GET)
    public String editTool(Model model, @RequestParam("toolID") int toolID) {
    	ToolForm form = toolDAO.findToolFormByID(toolID);
 
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
        	for (String resultS : returnResult){
        		System.out.println("Success assign tool " +  toolForm.getToolCode() + " for machine " + resultS);
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
    	MachineForm form = machineDAO.findMachineFormByID(machineID);
    	List<Company> companies = companyDAO.findAllCompany();
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
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray01.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray01(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray02 != null) {
    		trayForm.setQuantity02(toolMachineTray02.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray02.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray02(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray03 != null) {
    		trayForm.setQuantity03(toolMachineTray03.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray03.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray03(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray04 != null) {
    		trayForm.setQuantity04(toolMachineTray04.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray04.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray04(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray05 != null) {
    		trayForm.setQuantity05(toolMachineTray05.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray05.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray05(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray06 != null) {
    		trayForm.setQuantity06(toolMachineTray06.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray06.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray06(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray07 != null) {
    		trayForm.setQuantity07(toolMachineTray07.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray07.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray07(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray08 != null) {
    		trayForm.setQuantity08(toolMachineTray08.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray08.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray08(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray09 != null) {
    		trayForm.setQuantity09(toolMachineTray09.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray09.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray09(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray10 != null) {
    		trayForm.setQuantity10(toolMachineTray10.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray10.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray10(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray11 != null) {
    		trayForm.setQuantity11(toolMachineTray11.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray11.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray11(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray12 != null) {
    		trayForm.setQuantity12(toolMachineTray12.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray12.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray12(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray13 != null) {
    		trayForm.setQuantity13(toolMachineTray13.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray13.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray13(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray14 != null) {
    		trayForm.setQuantity14(toolMachineTray14.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray14.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray14(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray15 != null) {
    		trayForm.setQuantity15(toolMachineTray15.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray15.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray15(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray16 != null) {
    		trayForm.setQuantity16(toolMachineTray16.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray16.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray16(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray17 != null) {
    		trayForm.setQuantity17(toolMachineTray17.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray17.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray17(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray18 != null) {
    		trayForm.setQuantity08(toolMachineTray18.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray18.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray18(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray19 != null) {
    		trayForm.setQuantity19(toolMachineTray19.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray19.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray19(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray20 != null) {
    		trayForm.setQuantity20(toolMachineTray20.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray20.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray20(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray21 != null) {
    		trayForm.setQuantity21(toolMachineTray21.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray21.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray21(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray22 != null) {
    		trayForm.setQuantity22(toolMachineTray22.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray22.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray22(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray23 != null) {
    		trayForm.setQuantity23(toolMachineTray23.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray23.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray23(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray24 != null) {
    		trayForm.setQuantity04(toolMachineTray24.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray24.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray24(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray25 != null) {
    		trayForm.setQuantity25(toolMachineTray25.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray25.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray25(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray26 != null) {
    		trayForm.setQuantity26(toolMachineTray26.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray26.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray26(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray27 != null) {
    		trayForm.setQuantity27(toolMachineTray27.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray27.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray27(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray28 != null) {
    		trayForm.setQuantity28(toolMachineTray28.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray28.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray28(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray29 != null) {
    		trayForm.setQuantity29(toolMachineTray29.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray29.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray29(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray30 != null) {
    		trayForm.setQuantity30(toolMachineTray30.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray30.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray30(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray31 != null) {
    		trayForm.setQuantity31(toolMachineTray31.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray31.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray31(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray32 != null) {
    		trayForm.setQuantity32(toolMachineTray32.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray32.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray32(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray33 != null) {
    		trayForm.setQuantity33(toolMachineTray33.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray33.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray33(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray34 != null) {
    		trayForm.setQuantity34(toolMachineTray34.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray34.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray34(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray35 != null) {
    		trayForm.setQuantity35(toolMachineTray35.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray35.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray35(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray36 != null) {
    		trayForm.setQuantity36(toolMachineTray36.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray36.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray36(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray37 != null) {
    		trayForm.setQuantity37(toolMachineTray37.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray37.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray37(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray38 != null) {
    		trayForm.setQuantity38(toolMachineTray38.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray38.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray38(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray39 != null) {
    		trayForm.setQuantity39(toolMachineTray39.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray39.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray39(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray40 != null) {
    		trayForm.setQuantity40(toolMachineTray40.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray40.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray40(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray41 != null) {
    		trayForm.setQuantity41(toolMachineTray41.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray41.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray41(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray42 != null) {
    		trayForm.setQuantity42(toolMachineTray42.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray42.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray42(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray43 != null) {
    		trayForm.setQuantity43(toolMachineTray43.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray43.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray43(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray44 != null) {
    		trayForm.setQuantity44(toolMachineTray44.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray44.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray44(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray45 != null) {
    		trayForm.setQuantity45(toolMachineTray45.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray45.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray45(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray46 != null) {
    		trayForm.setQuantity46(toolMachineTray46.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray46.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray46(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray47 != null) {
    		trayForm.setQuantity47(toolMachineTray47.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray47.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray47(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray48 != null) {
    		trayForm.setQuantity48(toolMachineTray48.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray48.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray48(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray49 != null) {
    		trayForm.setQuantity49(toolMachineTray49.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray49.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray49(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray50 != null) {
    		trayForm.setQuantity50(toolMachineTray50.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray50.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray50(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray51 != null) {
    		trayForm.setQuantity51(toolMachineTray51.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray51.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray51(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray52 != null) {
    		trayForm.setQuantity52(toolMachineTray52.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray52.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray52(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray53 != null) {
    		trayForm.setQuantity53(toolMachineTray53.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray53.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray53(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray54 != null) {
    		trayForm.setQuantity04(toolMachineTray54.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray54.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray54(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray55 != null) {
    		trayForm.setQuantity55(toolMachineTray55.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray55.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray55(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray56 != null) {
    		trayForm.setQuantity56(toolMachineTray56.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray56.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray56(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray57 != null) {
    		trayForm.setQuantity57(toolMachineTray57.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray57.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray57(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray58 != null) {
    		trayForm.setQuantity58(toolMachineTray58.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray58.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray58(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray59 != null) {
    		trayForm.setQuantity59(toolMachineTray59.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray59.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray59(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray60 != null) {
    		trayForm.setQuantity60(toolMachineTray60.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray60.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray60(tm.getToolCode());
    		}
    	}
    	
        model.addAttribute("machineForm", form);
    	model.addAttribute("companies", companies);
    	model.addAttribute("tools", tools);
    	model.addAttribute("trayForm", trayForm);
        return "machineDetail";
    }
    
    @RequestMapping(value = "/admin/assignToolTray", method = RequestMethod.POST)
    public String assignToolTray(Model model, //
            @ModelAttribute("trayForm") @Validated TrayForm trayForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        // Validate result
        if (result.hasErrors()) {
        	MachineForm form = machineDAO.findMachineFormByCode(trayForm.getMachineCode());
        	List<Company> companies = companyDAO.findAllCompany();
        	List<Tools> tools = toolDAO.findToolsByMachineCode(form.getMachineCode());
        	trayForm.setMachineCode(form.getMachineCode());
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
        	
        	if(toolMachineTray01 != null) {
        		trayForm.setQuantity01(toolMachineTray01.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray01.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray01(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray02 != null) {
        		trayForm.setQuantity02(toolMachineTray02.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray02.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray02(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray03 != null) {
        		trayForm.setQuantity03(toolMachineTray03.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray03.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray03(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray04 != null) {
        		trayForm.setQuantity04(toolMachineTray04.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray04.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray04(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray05 != null) {
        		trayForm.setQuantity05(toolMachineTray05.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray05.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray05(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray06 != null) {
        		trayForm.setQuantity06(toolMachineTray06.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray06.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray06(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray07 != null) {
        		trayForm.setQuantity07(toolMachineTray07.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray07.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray07(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray08 != null) {
        		trayForm.setQuantity08(toolMachineTray08.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray08.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray08(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray09 != null) {
        		trayForm.setQuantity09(toolMachineTray09.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray09.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray09(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray10 != null) {
        		trayForm.setQuantity10(toolMachineTray10.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray10.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray10(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray11 != null) {
        		trayForm.setQuantity11(toolMachineTray11.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray11.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray11(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray12 != null) {
        		trayForm.setQuantity12(toolMachineTray12.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray12.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray12(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray13 != null) {
        		trayForm.setQuantity13(toolMachineTray13.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray13.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray13(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray14 != null) {
        		trayForm.setQuantity14(toolMachineTray14.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray14.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray14(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray15 != null) {
        		trayForm.setQuantity15(toolMachineTray15.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray15.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray15(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray16 != null) {
        		trayForm.setQuantity16(toolMachineTray16.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray16.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray16(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray17 != null) {
        		trayForm.setQuantity17(toolMachineTray17.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray17.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray17(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray18 != null) {
        		trayForm.setQuantity08(toolMachineTray18.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray18.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray18(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray19 != null) {
        		trayForm.setQuantity19(toolMachineTray19.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray19.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray19(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray20 != null) {
        		trayForm.setQuantity20(toolMachineTray20.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray20.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray20(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray21 != null) {
        		trayForm.setQuantity21(toolMachineTray21.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray21.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray21(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray22 != null) {
        		trayForm.setQuantity22(toolMachineTray22.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray22.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray22(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray23 != null) {
        		trayForm.setQuantity23(toolMachineTray23.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray23.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray23(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray24 != null) {
        		trayForm.setQuantity04(toolMachineTray24.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray24.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray24(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray25 != null) {
        		trayForm.setQuantity25(toolMachineTray25.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray25.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray25(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray26 != null) {
        		trayForm.setQuantity26(toolMachineTray26.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray26.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray26(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray27 != null) {
        		trayForm.setQuantity27(toolMachineTray27.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray27.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray27(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray28 != null) {
        		trayForm.setQuantity28(toolMachineTray28.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray28.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray28(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray29 != null) {
        		trayForm.setQuantity29(toolMachineTray29.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray29.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray29(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray30 != null) {
        		trayForm.setQuantity30(toolMachineTray30.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray30.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray30(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray31 != null) {
        		trayForm.setQuantity31(toolMachineTray31.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray31.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray31(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray32 != null) {
        		trayForm.setQuantity32(toolMachineTray32.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray32.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray32(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray33 != null) {
        		trayForm.setQuantity33(toolMachineTray33.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray33.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray33(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray34 != null) {
        		trayForm.setQuantity34(toolMachineTray34.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray34.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray34(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray35 != null) {
        		trayForm.setQuantity35(toolMachineTray35.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray35.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray35(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray36 != null) {
        		trayForm.setQuantity36(toolMachineTray36.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray36.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray36(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray37 != null) {
        		trayForm.setQuantity37(toolMachineTray37.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray37.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray37(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray38 != null) {
        		trayForm.setQuantity38(toolMachineTray38.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray38.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray38(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray39 != null) {
        		trayForm.setQuantity39(toolMachineTray39.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray39.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray39(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray40 != null) {
        		trayForm.setQuantity40(toolMachineTray40.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray40.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray40(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray41 != null) {
        		trayForm.setQuantity41(toolMachineTray41.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray41.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray41(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray42 != null) {
        		trayForm.setQuantity42(toolMachineTray42.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray42.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray42(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray43 != null) {
        		trayForm.setQuantity43(toolMachineTray43.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray43.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray43(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray44 != null) {
        		trayForm.setQuantity44(toolMachineTray44.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray44.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray44(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray45 != null) {
        		trayForm.setQuantity45(toolMachineTray45.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray45.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray45(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray46 != null) {
        		trayForm.setQuantity46(toolMachineTray46.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray46.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray46(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray47 != null) {
        		trayForm.setQuantity47(toolMachineTray47.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray47.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray47(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray48 != null) {
        		trayForm.setQuantity48(toolMachineTray48.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray48.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray48(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray49 != null) {
        		trayForm.setQuantity49(toolMachineTray49.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray49.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray49(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray50 != null) {
        		trayForm.setQuantity50(toolMachineTray50.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray50.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray50(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray51 != null) {
        		trayForm.setQuantity51(toolMachineTray51.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray51.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray51(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray52 != null) {
        		trayForm.setQuantity52(toolMachineTray52.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray52.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray52(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray53 != null) {
        		trayForm.setQuantity53(toolMachineTray53.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray53.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray53(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray54 != null) {
        		trayForm.setQuantity04(toolMachineTray54.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray54.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray54(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray55 != null) {
        		trayForm.setQuantity55(toolMachineTray55.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray55.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray55(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray56 != null) {
        		trayForm.setQuantity56(toolMachineTray56.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray56.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray56(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray57 != null) {
        		trayForm.setQuantity57(toolMachineTray57.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray57.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray57(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray58 != null) {
        		trayForm.setQuantity58(toolMachineTray58.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray58.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray58(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray59 != null) {
        		trayForm.setQuantity59(toolMachineTray59.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray59.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray59(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray60 != null) {
        		trayForm.setQuantity60(toolMachineTray60.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray60.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray60(tm.getToolCode());
        		}
        	}
            model.addAttribute("machineForm", form);
        	model.addAttribute("companies", companies);
        	model.addAttribute("tools", tools);
        	model.addAttribute("trayForm", trayForm);
            return "machineDetail";
        }
        
        try {
        	toolMachineTrayDAO.updateTray(trayForm);
        } // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            MachineForm form = machineDAO.findMachineFormByCode(trayForm.getMachineCode());
        	List<Company> companies = companyDAO.findAllCompany();
        	List<Tools> tools = toolDAO.findToolsByMachineCode(form.getMachineCode());
        	trayForm.setMachineCode(form.getMachineCode());
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
        	
        	if(toolMachineTray01 != null) {
        		trayForm.setQuantity01(toolMachineTray01.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray01.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray01(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray02 != null) {
        		trayForm.setQuantity02(toolMachineTray02.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray02.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray02(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray03 != null) {
        		trayForm.setQuantity03(toolMachineTray03.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray03.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray03(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray04 != null) {
        		trayForm.setQuantity04(toolMachineTray04.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray04.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray04(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray05 != null) {
        		trayForm.setQuantity05(toolMachineTray05.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray05.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray05(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray06 != null) {
        		trayForm.setQuantity06(toolMachineTray06.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray06.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray06(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray07 != null) {
        		trayForm.setQuantity07(toolMachineTray07.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray07.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray07(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray08 != null) {
        		trayForm.setQuantity08(toolMachineTray08.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray08.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray08(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray09 != null) {
        		trayForm.setQuantity09(toolMachineTray09.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray09.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray09(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray10 != null) {
        		trayForm.setQuantity10(toolMachineTray10.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray10.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray10(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray11 != null) {
        		trayForm.setQuantity11(toolMachineTray11.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray11.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray11(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray12 != null) {
        		trayForm.setQuantity12(toolMachineTray12.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray12.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray12(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray13 != null) {
        		trayForm.setQuantity13(toolMachineTray13.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray13.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray13(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray14 != null) {
        		trayForm.setQuantity14(toolMachineTray14.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray14.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray14(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray15 != null) {
        		trayForm.setQuantity15(toolMachineTray15.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray15.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray15(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray16 != null) {
        		trayForm.setQuantity16(toolMachineTray16.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray16.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray16(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray17 != null) {
        		trayForm.setQuantity17(toolMachineTray17.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray17.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray17(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray18 != null) {
        		trayForm.setQuantity08(toolMachineTray18.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray18.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray18(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray19 != null) {
        		trayForm.setQuantity19(toolMachineTray19.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray19.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray19(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray20 != null) {
        		trayForm.setQuantity20(toolMachineTray20.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray20.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray20(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray21 != null) {
        		trayForm.setQuantity21(toolMachineTray21.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray21.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray21(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray22 != null) {
        		trayForm.setQuantity22(toolMachineTray22.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray22.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray22(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray23 != null) {
        		trayForm.setQuantity23(toolMachineTray23.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray23.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray23(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray24 != null) {
        		trayForm.setQuantity04(toolMachineTray24.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray24.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray24(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray25 != null) {
        		trayForm.setQuantity25(toolMachineTray25.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray25.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray25(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray26 != null) {
        		trayForm.setQuantity26(toolMachineTray26.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray26.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray26(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray27 != null) {
        		trayForm.setQuantity27(toolMachineTray27.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray27.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray27(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray28 != null) {
        		trayForm.setQuantity28(toolMachineTray28.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray28.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray28(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray29 != null) {
        		trayForm.setQuantity29(toolMachineTray29.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray29.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray29(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray30 != null) {
        		trayForm.setQuantity30(toolMachineTray30.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray30.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray30(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray31 != null) {
        		trayForm.setQuantity31(toolMachineTray31.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray31.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray31(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray32 != null) {
        		trayForm.setQuantity32(toolMachineTray32.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray32.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray32(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray33 != null) {
        		trayForm.setQuantity33(toolMachineTray33.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray33.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray33(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray34 != null) {
        		trayForm.setQuantity34(toolMachineTray34.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray34.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray34(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray35 != null) {
        		trayForm.setQuantity35(toolMachineTray35.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray35.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray35(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray36 != null) {
        		trayForm.setQuantity36(toolMachineTray36.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray36.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray36(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray37 != null) {
        		trayForm.setQuantity37(toolMachineTray37.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray37.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray37(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray38 != null) {
        		trayForm.setQuantity38(toolMachineTray38.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray38.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray38(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray39 != null) {
        		trayForm.setQuantity39(toolMachineTray39.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray39.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray39(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray40 != null) {
        		trayForm.setQuantity40(toolMachineTray40.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray40.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray40(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray41 != null) {
        		trayForm.setQuantity41(toolMachineTray41.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray41.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray41(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray42 != null) {
        		trayForm.setQuantity42(toolMachineTray42.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray42.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray42(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray43 != null) {
        		trayForm.setQuantity43(toolMachineTray43.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray43.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray43(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray44 != null) {
        		trayForm.setQuantity44(toolMachineTray44.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray44.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray44(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray45 != null) {
        		trayForm.setQuantity45(toolMachineTray45.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray45.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray45(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray46 != null) {
        		trayForm.setQuantity46(toolMachineTray46.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray46.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray46(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray47 != null) {
        		trayForm.setQuantity47(toolMachineTray47.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray47.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray47(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray48 != null) {
        		trayForm.setQuantity48(toolMachineTray48.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray48.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray48(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray49 != null) {
        		trayForm.setQuantity49(toolMachineTray49.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray49.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray49(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray50 != null) {
        		trayForm.setQuantity50(toolMachineTray50.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray50.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray50(tm.getToolCode());
        		}
        	}
        	
        	if(toolMachineTray51 != null) {
        		trayForm.setQuantity51(toolMachineTray51.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray51.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray51(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray52 != null) {
        		trayForm.setQuantity52(toolMachineTray52.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray52.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray52(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray53 != null) {
        		trayForm.setQuantity53(toolMachineTray53.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray53.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray53(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray54 != null) {
        		trayForm.setQuantity04(toolMachineTray54.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray54.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray54(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray55 != null) {
        		trayForm.setQuantity55(toolMachineTray55.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray55.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray55(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray56 != null) {
        		trayForm.setQuantity56(toolMachineTray56.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray56.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray56(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray57 != null) {
        		trayForm.setQuantity57(toolMachineTray57.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray57.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray57(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray58 != null) {
        		trayForm.setQuantity58(toolMachineTray58.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray58.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray58(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray59 != null) {
        		trayForm.setQuantity59(toolMachineTray59.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray59.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray59(tm.getToolCode());
        		}
        	}
        	if(toolMachineTray60 != null) {
        		trayForm.setQuantity60(toolMachineTray60.getQuantity());
        		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray60.getToolsMachineID());
        		if(tm != null) {
        			trayForm.setTray60(tm.getToolCode());
        		}
        	}
            model.addAttribute("machineForm", form);
        	model.addAttribute("companies", companies);
        	model.addAttribute("tools", tools);
        	model.addAttribute("trayForm", trayForm);
            return "machineDetail";
        }
        
        MachineForm form = machineDAO.findMachineFormByCode(trayForm.getMachineCode());
    	List<Company> companies = companyDAO.findAllCompany();
    	List<Tools> tools = toolDAO.findToolsByMachineCode(form.getMachineCode());
    	trayForm.setMachineCode(form.getMachineCode());
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
    	
    	if(toolMachineTray01 != null) {
    		trayForm.setQuantity01(toolMachineTray01.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray01.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray01(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray02 != null) {
    		trayForm.setQuantity02(toolMachineTray02.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray02.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray02(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray03 != null) {
    		trayForm.setQuantity03(toolMachineTray03.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray03.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray03(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray04 != null) {
    		trayForm.setQuantity04(toolMachineTray04.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray04.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray04(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray05 != null) {
    		trayForm.setQuantity05(toolMachineTray05.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray05.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray05(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray06 != null) {
    		trayForm.setQuantity06(toolMachineTray06.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray06.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray06(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray07 != null) {
    		trayForm.setQuantity07(toolMachineTray07.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray07.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray07(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray08 != null) {
    		trayForm.setQuantity08(toolMachineTray08.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray08.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray08(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray09 != null) {
    		trayForm.setQuantity09(toolMachineTray09.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray09.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray09(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray10 != null) {
    		trayForm.setQuantity10(toolMachineTray10.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray10.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray10(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray11 != null) {
    		trayForm.setQuantity11(toolMachineTray11.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray11.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray11(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray12 != null) {
    		trayForm.setQuantity12(toolMachineTray12.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray12.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray12(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray13 != null) {
    		trayForm.setQuantity13(toolMachineTray13.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray13.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray13(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray14 != null) {
    		trayForm.setQuantity14(toolMachineTray14.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray14.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray14(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray15 != null) {
    		trayForm.setQuantity15(toolMachineTray15.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray15.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray15(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray16 != null) {
    		trayForm.setQuantity16(toolMachineTray16.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray16.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray16(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray17 != null) {
    		trayForm.setQuantity17(toolMachineTray17.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray17.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray17(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray18 != null) {
    		trayForm.setQuantity08(toolMachineTray18.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray18.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray18(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray19 != null) {
    		trayForm.setQuantity19(toolMachineTray19.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray19.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray19(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray20 != null) {
    		trayForm.setQuantity20(toolMachineTray20.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray20.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray20(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray21 != null) {
    		trayForm.setQuantity21(toolMachineTray21.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray21.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray21(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray22 != null) {
    		trayForm.setQuantity22(toolMachineTray22.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray22.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray22(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray23 != null) {
    		trayForm.setQuantity23(toolMachineTray23.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray23.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray23(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray24 != null) {
    		trayForm.setQuantity04(toolMachineTray24.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray24.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray24(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray25 != null) {
    		trayForm.setQuantity25(toolMachineTray25.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray25.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray25(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray26 != null) {
    		trayForm.setQuantity26(toolMachineTray26.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray26.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray26(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray27 != null) {
    		trayForm.setQuantity27(toolMachineTray27.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray27.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray27(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray28 != null) {
    		trayForm.setQuantity28(toolMachineTray28.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray28.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray28(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray29 != null) {
    		trayForm.setQuantity29(toolMachineTray29.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray29.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray29(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray30 != null) {
    		trayForm.setQuantity30(toolMachineTray30.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray30.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray30(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray31 != null) {
    		trayForm.setQuantity31(toolMachineTray31.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray31.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray31(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray32 != null) {
    		trayForm.setQuantity32(toolMachineTray32.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray32.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray32(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray33 != null) {
    		trayForm.setQuantity33(toolMachineTray33.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray33.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray33(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray34 != null) {
    		trayForm.setQuantity34(toolMachineTray34.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray34.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray34(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray35 != null) {
    		trayForm.setQuantity35(toolMachineTray35.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray35.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray35(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray36 != null) {
    		trayForm.setQuantity36(toolMachineTray36.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray36.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray36(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray37 != null) {
    		trayForm.setQuantity37(toolMachineTray37.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray37.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray37(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray38 != null) {
    		trayForm.setQuantity38(toolMachineTray38.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray38.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray38(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray39 != null) {
    		trayForm.setQuantity39(toolMachineTray39.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray39.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray39(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray40 != null) {
    		trayForm.setQuantity40(toolMachineTray40.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray40.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray40(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray41 != null) {
    		trayForm.setQuantity41(toolMachineTray41.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray41.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray41(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray42 != null) {
    		trayForm.setQuantity42(toolMachineTray42.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray42.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray42(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray43 != null) {
    		trayForm.setQuantity43(toolMachineTray43.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray43.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray43(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray44 != null) {
    		trayForm.setQuantity44(toolMachineTray44.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray44.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray44(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray45 != null) {
    		trayForm.setQuantity45(toolMachineTray45.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray45.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray45(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray46 != null) {
    		trayForm.setQuantity46(toolMachineTray46.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray46.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray46(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray47 != null) {
    		trayForm.setQuantity47(toolMachineTray47.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray47.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray47(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray48 != null) {
    		trayForm.setQuantity48(toolMachineTray48.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray48.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray48(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray49 != null) {
    		trayForm.setQuantity49(toolMachineTray49.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray49.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray49(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray50 != null) {
    		trayForm.setQuantity50(toolMachineTray50.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray50.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray50(tm.getToolCode());
    		}
    	}
    	
    	if(toolMachineTray51 != null) {
    		trayForm.setQuantity51(toolMachineTray51.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray51.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray51(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray52 != null) {
    		trayForm.setQuantity52(toolMachineTray52.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray52.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray52(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray53 != null) {
    		trayForm.setQuantity53(toolMachineTray53.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray53.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray53(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray54 != null) {
    		trayForm.setQuantity04(toolMachineTray54.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray54.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray54(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray55 != null) {
    		trayForm.setQuantity55(toolMachineTray55.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray55.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray55(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray56 != null) {
    		trayForm.setQuantity56(toolMachineTray56.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray56.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray56(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray57 != null) {
    		trayForm.setQuantity57(toolMachineTray57.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray57.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray57(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray58 != null) {
    		trayForm.setQuantity58(toolMachineTray58.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray58.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray58(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray59 != null) {
    		trayForm.setQuantity59(toolMachineTray59.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray59.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray59(tm.getToolCode());
    		}
    	}
    	if(toolMachineTray60 != null) {
    		trayForm.setQuantity60(toolMachineTray60.getQuantity());
    		ToolsMachine tm = toolMachineDAO.findByID(toolMachineTray60.getToolsMachineID());
    		if(tm != null) {
    			trayForm.setTray60(tm.getToolCode());
    		}
    	}
        model.addAttribute("machineForm", form);
    	model.addAttribute("companies", companies);
    	model.addAttribute("tools", tools);
    	model.addAttribute("trayForm", trayForm);
        return "machineDetail";
    }
    
    @RequestMapping(value = "/admin/machineDetail", method = RequestMethod.POST)
    public String saveMachine(Model model, //
            @ModelAttribute("machineForm") @Validated MachineForm machineForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
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