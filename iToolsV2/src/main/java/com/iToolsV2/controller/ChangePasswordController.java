package com.iToolsV2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.form.ChangePasswordForm;

import lombok.extern.slf4j.Slf4j;

@PreAuthorize("isAuthenticated()")
@Slf4j
@Controller
public class ChangePasswordController extends AbstractController {

	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private AssessorDAO assessorDao;

    @GetMapping("/change-password")
    public String getChangePassword(@ModelAttribute("changePassForm") ChangePasswordForm form) {
        form.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return "public/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("changePassForm") ChangePasswordForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) return "public/change-password";
        if (!StringUtils.equals(form.getPassword(), form.getVerifyPassword())) {
            bindingResult.rejectValue("verifyPassword", "Invalid");
        }
        if (bindingResult.hasErrors()) return "public/change-password";
// check old password
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Assessor assessor = assessorDao.findByUserNameAndActiveAndLocked(username, true, false);
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
        String encrytedPasswordOld = this.passwordEncoder.encode(form.getPasswordOld());
        if (!assessor.getEncrytedPassword().equals(encrytedPasswordOld)) {
        	bindingResult.rejectValue("passwordOld", "Invalid");
        	return "public/change-password";
        }
        if (assessor.getEncrytedPassword().equals(encrytedPassword)) {
        	bindingResult.rejectValue("password", "samePassword");
        	return "public/change-password";
        } 
        try {
        	 // save pass
            //Assessor assessor = assessorDao.findByUserNameAndActiveAndLocked(username, true, false);
            //String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
            assessor.setEncrytedPassword(encrytedPassword);
            assessor.setFirstTimeLogin(false);
            assessorDao.update(assessor);
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            request.logout();

            return "public/change-password-success";

        } catch (Exception e) {
            log.error("Change pass not success, username is" + username, e);
            bindingResult.rejectValue("password", "Invalid");
            return "public/change-password";
        }
    }

}

