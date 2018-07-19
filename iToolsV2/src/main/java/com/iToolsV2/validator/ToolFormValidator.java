package com.iToolsV2.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.iToolsV2.dao.ToolDAO;
import com.iToolsV2.entity.Tools;
import com.iToolsV2.form.ToolForm;
 
@Component
public class ToolFormValidator implements Validator {

    @Autowired
    private ToolDAO toolDAO;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ToolForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	ToolForm toolForm = (ToolForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toolCode", "NotEmpty.toolForm.toolCode");        
        
        if (!errors.hasFieldErrors("toolCode")) {
        	if (toolForm.getToolID() == 0) {
	        	Tools tool = toolDAO.findTool(toolForm.getToolCode());
	            if (tool != null) {
	                errors.rejectValue("toolCode", "Duplicate.toolForm.toolCode");
	            }
        	}
        }         
    }
 
}