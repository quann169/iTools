package com.iToolsV2.validator;
 
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.iToolsV2.form.RolesAssessorForm;
 
@Component
public class RolesAssessorFormValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == RolesAssessorForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rolesID", "NotEmpty.raForm.rolesID");                         
    }
 
}