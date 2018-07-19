package com.iToolsV2.controller;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iToolsV2.dao.AssessorDAO;
import com.iToolsV2.entity.Assessor;
import com.iToolsV2.form.ForgetPasswordForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ForgetPasswordController {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private AssessorDAO assessorDao;
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("/reset-password")
	public String getForgetPasswordPage(@ModelAttribute("forgetPassForm") ForgetPasswordForm form) {
        return "public/forgotPassword";
    }
	
	@PostMapping("/reset-password")
    public String postForgetPassword(@ModelAttribute("forgetPassForm") ForgetPasswordForm form, BindingResult bindingResult) {
        String username = form.getUsername();
        if (StringUtils.isEmpty(username)) {
            bindingResult.rejectValue("username", "IsEmpty");
        } else {
        	 Assessor assessor = assessorDao.findByUserNameAndActiveAndLocked(username, true, false);
        	 if (assessor == null) {
        		 bindingResult.rejectValue("username", "Invalid");
        	 } else if (StringUtils.isEmpty(form.getEmail())) {
                 bindingResult.rejectValue("email", "IsEmpty");
             } else if (!StringUtils.equalsIgnoreCase(assessor.getEmailAddress(), form.getEmail())) {
                 bindingResult.rejectValue("email", "Invalid");
             }
        }
        
        if (bindingResult.hasErrors()) {
        	return "public/forgotPassword";
        }
        
        try {
            String newPassword = generatePassword();
            log.info(newPassword);
            // save pass
            Assessor assessor = assessorDao.findByUserNameAndActiveAndLocked(username, true, false);
            String encrytedPassword = this.passwordEncoder.encode(newPassword);
            assessor.setEncrytedPassword(encrytedPassword);
            assessor.setFirstTimeLogin(true);
            assessorDao.update(assessor);
            sendNewPasswordMail(username, getEmail(username), newPassword);
            
        } catch (MessagingException | MailSendException ex) {
            log.error("Cannot send mail, username is " + username, ex);
            return "public/fail";
        } catch (Exception ex) {
            log.error("Cannot change password, username is " + username, ex);
            return "public/fail";
        }
        
        return "public/reset-password-success";
    }
	
	private String generatePassword() {
        return "ITool" + StringUtils.left(RandomUtils.nextInt(0, 9) + UUID.randomUUID().toString().replaceAll("-", ""), 7).toUpperCase();
    }
	
	private String getEmail(String username) {
		Assessor assessor = assessorDao.findByUserNameAndActiveAndLocked(username, true, false);
        if (assessor == null ) return "";
        else return assessor.getEmailAddress();
    }
	
	private void sendNewPasswordMail(String username, String email, String newPassword) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("itool_web@tqteam.net");
        helper.setTo(email);
        helper.setSubject("Thay đổi mật khẩu");

        String newLine = System.lineSeparator();
        helper.setText("Xin chào " + username + newLine + newLine +
                "Bạn đã gửi yêu cầu thay đổi mật khẩu tới hệ thống ITool" + newLine +
                "Mật khẩu mới của bạn là \"" + newPassword + "\"" + newLine +
                "Vui lòng đăng nhập vào hệ thống để thay đổi mật khẩu." + newLine + newLine +
                "Trân trọng cảm ơn," + newLine +
                "Administrator"
        );

        sender.send(message);
        log.info("Send reset password email to " + email);
    }
}
