package com.iToolsV2.validator;
 
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.form.AssessorForm;
 
@Component
public class AssessorFormValidator implements Validator {
 
    private EmailValidator emailValidator = EmailValidator.getInstance(); 

    @Autowired
    private AssessorDAO assessorDAO;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AssessorForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	AssessorForm assessorForm = (AssessorForm) target;
    	 
        // Kiểm tra các field của AssessorForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.assessorForm.userName");        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.assessorForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.assessorForm.lastName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty.assessorForm.email");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.assessorForm.address");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.assessorForm.phone");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "active", "NotEmpty.assessorForm.active");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "locked", "NotEmpty.assessorForm.locked");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyCode", "NotEmpty.assessorForm.companyCode");
 
        if (!this.emailValidator.isValid(assessorForm.getEmailAddress())) {
            errors.rejectValue("emailAddress", "Pattern.assessorForm.email");
        } else {
        	//if (assessorForm.getAssessorId() == 0) {
        	Assessor assessor = assessorDAO.findAccountByEmail(assessorForm.getEmailAddress());
            if (assessor != null) {
                errors.rejectValue("emailAddress", "Duplicate.assessorForm.email");
            }
        }
 
        if (!errors.hasFieldErrors("name")) {
        	//if (assessorForm.getAssessorId() == 0) {
	        	Assessor assessor = assessorDAO.findAccount(assessorForm.getName());
	            if (assessor != null) {
	                errors.rejectValue("name", "Duplicate.assessorForm.userName");
	            }
        	//}
        }
        
        if (assessorForm.getAssessorId() == 0) {
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.assessorForm.password");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.assessorForm.confirmPassword");
	        if (!errors.hasErrors()) {
	            if (!assessorForm.getConfirmPassword().equals(assessorForm.getPassword())) {
	                errors.rejectValue("confirmPassword", "Match.assessorForm.confirmPassword");
	            }
	        }
        }
        
    }
 
}