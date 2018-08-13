package com.iToolsV2.validator;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.iToolsV2.dao.CompanyDAO;
import com.iToolsV2.dao.MachineDAO;
import com.iToolsV2.entity.Company;
import com.iToolsV2.entity.Machine;
import com.iToolsV2.form.CompanyForm;
import com.iToolsV2.form.MachineForm;
 
@Component
public class MachineFormValidator implements Validator {

    @Autowired
    private MachineDAO machineDAO;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == MachineForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	MachineForm machineForm = (MachineForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "machineCode", "NotEmpty.machineForm.machineCode");        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "machineName", "NotEmpty.machineForm.machineName");
        
        if (!errors.hasFieldErrors("machineCode")) {
        	if (machineForm.getMachineID() == null) {
	        	Machine machine = machineDAO.findMachine(machineForm.getMachineCode());
	            if (machine != null) {
	                errors.rejectValue("machineCode", "Duplicate.machineForm.machineCode");
	            }
        	}
        } 
        if (!errors.hasFieldErrors("machineName")) {
        	if (machineForm.getMachineID() == null) {
        		Machine machine = machineDAO.findMachineByName(machineForm.getMachineName());
	            if (machine != null) {
	                errors.rejectValue("machineName", "Duplicate.machineForm.machineName");
	            }
        	}
        }
        
    }
 
}