package com.iToolsV2.controller;
 
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
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
import com.iToolsV2.dao.OrderDAO;
import com.iToolsV2.dao.ProductDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.Product;
import com.iToolsV2.form.AssessorForm;
import com.iToolsV2.form.ProductForm;
import com.iToolsV2.model.OrderDetailInfo;
import com.iToolsV2.model.OrderInfo;
import com.iToolsV2.pagination.PaginationResult;
import com.iToolsV2.validator.AssessorFormValidator;
import com.iToolsV2.validator.ProductFormValidator;
 
@Controller
@Transactional
public class AdminController {
 
    @Autowired
    private OrderDAO orderDAO;
 
    @Autowired
    private ProductDAO productDAO;
 
    @Autowired
    private ProductFormValidator productFormValidator;
    
    @Autowired
    private AssessorFormValidator assessorFormValidator;
    
    @Autowired
    private AssessorDAO assessorDAO;
    
    @Autowired
    private CompanyDAO companyDAO;
 
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
 
        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator); 
        }
        
        if (target.getClass() == AssessorForm.class) {
            dataBinder.setValidator(assessorFormValidator); 
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
 
    @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;
 
        PaginationResult<OrderInfo> paginationResult //
                = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
 
        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }
 
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;
 
        if (code != null && code.length() > 0) {
            Product product = productDAO.findProduct(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }
 
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
    public String productSave(Model model, //
            @ModelAttribute("productForm") @Validated ProductForm productForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
        if (result.hasErrors()) {
            return "product";
        }
        try {
            productDAO.save(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }
 
        return "redirect:/productList";
    }
 
    @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = this.orderDAO.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);
 
        model.addAttribute("orderInfo", orderInfo);
 
        return "order";
    }
    
    @RequestMapping("/admin/registerSuccessful")
    public String viewRegisterSuccessful(Model model) {
        return "registerAssessorSuccessfull";
    }
 
    @RequestMapping(value = "/admin/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {
 
    	AssessorForm form = new AssessorForm();
    	List<Company> companies = companyDAO.findAllCompany();
 
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

    @RequestMapping(value = "/admin/register", method = RequestMethod.POST)
    public String saveRegister(Model model, //
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
         
        return "redirect:/admin/registerSuccessful";
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
 
}