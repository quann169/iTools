package com.iToolsV2.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.iToolsV2.dao.CompanyDAO;
import com.iToolsV2.entity.Company;
import com.iToolsV2.form.CompanyForm;
 
@Component
public class CompanyFormValidator implements Validator {

    @Autowired
    private CompanyDAO companyDAO;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == CompanyForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	CompanyForm companyForm = (CompanyForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyCode", "NotEmpty.companyForm.companyCode");        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "NotEmpty.companyForm.companyName");
        
        if (!errors.hasFieldErrors("companyCode")) {
        	if (companyForm.getCompanyId() == 0) {
	        	Company company = companyDAO.findCompanyByCode(companyForm.getCompanyCode());
	            if (company != null) {
	                errors.rejectValue("companyCode", "Duplicate.companyForm.companyCode");
	            }
        	}
        } 
        if (!errors.hasFieldErrors("companyName")) {
        	if (companyForm.getCompanyId() == 0) {
	        	Company company = companyDAO.findCompany(companyForm.getCompanyName());
	            if (company != null) {
	                errors.rejectValue("companyName", "Duplicate.companyForm.companyName");
	            }
        	}
        }
        
    }
 
}