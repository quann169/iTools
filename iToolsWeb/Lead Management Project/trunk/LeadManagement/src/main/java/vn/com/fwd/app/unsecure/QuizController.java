package vn.com.fwd.app.unsecure;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.apache.commons.io.IOUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.fwd.domain.model.Register;
import vn.com.fwd.domain.service.user.RegisterService;
import vn.com.fwd.service.impl.MailServiceImpl;
import vn.com.fwd.service.impl.SMSServiceImpl;
import vn.com.fwd.sms.webservice.NotificationService;
import vn.com.fwd.sms.webservice.NotificationServiceService;

@Controller
@RequestMapping("quiz")
public class QuizController {
	
	@Inject
    protected RegisterService registerService;

    @Inject
    protected Mapper beanMapper;
    
    @Autowired
    private MailServiceImpl mailService;
    
    @Autowired
    private SMSServiceImpl smsService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String showRegisterForm(ModelMap model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "register/registerQuiz";
	}
	
	 @RequestMapping(value = "registerFormSuccess", method = RequestMethod.GET)
	 public String showFormSuccess(@RequestParam("id") Integer id, ModelMap model) {  
		 System.out.println(id);
		 Register register = registerService.findOne(id);
		 RegisterForm registerForm = new RegisterForm();
		 beanMapper.map(register, registerForm, "register");
		 model.addAttribute("registerForm", registerForm);
	  return "register/registerFormSuccess";
	 }
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String handleRegisterForm(@Validated RegisterForm registerForm,
			BindingResult bindingResult, ModelMap model) {

		System.out.println(registerForm.getFullName());
		System.out.println(registerForm.getSelection());
		
		Register register = beanMapper.map(registerForm, Register.class);
		int resultRegist = registerService.register(register);

		if (bindingResult.hasErrors()) {
			return "register/registerQuiz";
		} else {
			/*String emailbody = null;
			try {
				emailbody = buildEmail2Send("E", register);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// Send email to customer
			try {
				mailService.sendEmail2Client(register.getEmail(), emailbody, "Registration Information");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			// send SMS to customer
			if(register.getMobileNumber() != null && !register.getMobileNumber().trim().isEmpty()) {
				this.sendSMS2Client(register.getMobileNumber());
			}*/
			
			//model.clear();
			if (resultRegist == 0) {
				model.clear();
				return "register/registerQuiz";
			} else {
				return "redirect:/public/registerFormSuccess?id=" + resultRegist;
			}
			//return "register/registerFormSuccess";
			//return "redirect:/public/register?registSuccess=true";
		}

	}

	@RequestMapping("login")
	public String showLogin(@PageableDefaults Pageable pageable, Model model) {
		return "public/login";
	}
	

	private String buildEmail2Send(String lang, Register register) throws IOException {		
		InputStream is = null;
		String html = "/template/registrationActivation.html";

		final ClassLoader classLoader = getClass().getClassLoader();
		is = classLoader.getResourceAsStream(html);
		if(is == null){
			html = "/template/registrationActivation.html";
			is = classLoader.getResourceAsStream(html);
		}
		String fileStr = IOUtils.toString(is, "UTF-8");	
		List<String> replacementsEN = new ArrayList<>();

		replacementsEN.add(register.getFullName());
		
		String body = null;
		if("E".equals(lang)){
			body = this.formatTemplate(fileStr, replacementsEN);
		} else {
			body = this.formatTemplate(fileStr, replacementsEN);
		}
		
		return body;
	}
	
	private String formatTemplate(String template, List<String> replacements) {
		String rtn;
		
		int cnt = 0;
		if(replacements == null){
			rtn = template;
		} else {
			rtn = template;
			for(String string: replacements){
				String pattern = "{"+cnt+"}";
				rtn = rtn.replace(pattern, string);
				cnt = cnt + 1;
			}
		}
		
		return rtn;
	}
	
	private void sendSMS2Client(String phonenumber) {
		String endpoint = smsService.getSmsEndpoint();	// context.getString("sms.endpoint");
		URL url = null;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		QName qname = new QName("http://service.sms.fwd.com/", "NotificationServiceService");
		NotificationServiceService service = new NotificationServiceService(url, qname);
		try {
			NotificationService sms = service.getNotificationServicePort();
			Map<String, Object> ctx = ((BindingProvider) sms).getRequestContext();
			ctx.put("ws-security.username", smsService.getSmsUsername());		
			ctx.put("ws-security.password", smsService.getSmsPassword());			
			
			String content = smsService.getSmsMessages(); 
			sms.createSmsRequest(null, smsService.getSmsType(), phonenumber, content);	
		} catch(Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

}
