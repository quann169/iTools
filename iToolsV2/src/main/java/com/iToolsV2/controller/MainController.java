package com.iToolsV2.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.dao.CompanyDAO;
import com.iToolsV2.dao.MachineDAO;
import com.iToolsV2.dao.ToolDAO;
import com.iToolsV2.model.AssessorInfo;
import com.iToolsV2.model.CompanyInfo;
import com.iToolsV2.model.MachineInfo;
import com.iToolsV2.model.ToolInfo;
import com.iToolsV2.pagination.PaginationResult;
 
@Controller
@Transactional
public class MainController {
    
    @Autowired
    private MachineDAO machineDAO;
    
    @Autowired
    private AssessorDAO assessorDAO;
    
    @Autowired
    private CompanyDAO companyDAO;
    
    @Autowired
    private ToolDAO toolDAO;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
    }
 
    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }
 
    @RequestMapping("/")
    public String home() {
        //return "index";
    	return "login";
    }
    
    @RequestMapping({ "/machineList" })
    public String listMachineHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 3;
        final int maxNavigationPage = 10;
 
        PaginationResult<MachineInfo> result = machineDAO.queryMachine(page, //
                maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationMachine", result);
        return "machineList";
    }
    
    @RequestMapping({ "/userList" })
    public String listUserHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 10;
        final int maxNavigationPage = 10;
 
        PaginationResult<AssessorInfo> result = assessorDAO.queryAssessor(page, //
                maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationAssessor", result);
        return "assessorList";
    }
    
    @RequestMapping({ "/companyList" })
    public String listCompanyHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 10;
        final int maxNavigationPage = 10;
 
        PaginationResult<CompanyInfo> result = companyDAO.queryCompany(page, //
                maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationCompany", result);
        return "companyList";
    }
    
    @RequestMapping({ "/ctidList" })
    public String listToolHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 10;
        final int maxNavigationPage = 10;
 
        PaginationResult<ToolInfo> result = toolDAO.queryTool(page, //
                maxResult, maxNavigationPage, likeName);
 
        model.addAttribute("paginationTool", result);
        return "toolList";
    }
 
     
    /*@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }*/
 
}