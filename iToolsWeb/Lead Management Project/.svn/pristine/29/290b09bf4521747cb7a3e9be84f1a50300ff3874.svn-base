package vn.com.fwd.app.secure;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.fwd.domain.service.user.RegisterService;
import vn.com.fwd.service.impl.MailServiceImpl;
import vn.com.fwd.service.impl.SMSServiceImpl;

@Controller
@RequestMapping("fsc")
public class FSCController {
	
	@Inject
    protected RegisterService registerService;

    @Inject
    protected Mapper beanMapper;
    
    @Autowired
    private MailServiceImpl mailService;
    
    @Autowired
    private SMSServiceImpl smsService;

	@RequestMapping(value = "leadSearch", method = RequestMethod.GET)
	public String showRegisterForm(ModelMap model) {
		model.addAttribute("leadSearchForm", new LeadSearchForm());
		return "fsc/leadSearch";
	}
	
	@RequestMapping(value = "leadInfo", method = RequestMethod.GET)
	public String showReportForm(ModelMap model) {
		model.addAttribute("leadInfoForm", new LeadInfoForm());
		return "fsc/leadInfo";
	}

}


