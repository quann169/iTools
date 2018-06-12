package vn.com.fwd.app.payment;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.fwd.app.payment.PaymentConstant;
import vn.com.fwd.app.payment.PaymentForm;
import vn.com.fwd.domain.service.user.UserService;

@Controller
@RequestMapping("payment")
public class PaymentController {

	@Inject
	protected UserService userService;

	@ModelAttribute
	public PaymentForm setUpPaymentForm() {
		return new PaymentForm();
	}

	@RequestMapping("home")
	public String list(@PageableDefaults Pageable pageable, Model model) {
		return "payment/home";
	}

	@RequestMapping(value = "payment", method = RequestMethod.GET)
	public String openPayment() {
		return "payment/payment";
	}

	@RequestMapping(value = "paymentfail", method = RequestMethod.POST)
	public String handlePaymentFail(ReturnForm returnForm, ModelMap model) {
		model.addAttribute("returnForm", returnForm);
		return "payment/paymentfail";
	}

	@RequestMapping(value = "paymentcancel", method = RequestMethod.POST)
	public String handlePaymentCancel() {
		return "payment/paymentcancel";
	}

	@RequestMapping(value = "paymentsuccess", method = RequestMethod.POST)
	public String handlePaymentSuccess(ReturnForm returnForm, ModelMap model) {
		model.addAttribute("returnForm", returnForm);
		return "payment/paymentsuccess";
	}

	@RequestMapping(value = "payment", method = RequestMethod.POST)
	public String handlePaymentformEapp(PaymentForm paymentForm, ModelMap model)
			throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {

		paymentForm.setAccess_key(PaymentConstant.ACCESS_KEY);
		paymentForm.setProfile_id(PaymentConstant.PROFILE_ID);
		paymentForm.setLocale(PaymentConstant.LOCALE);
		paymentForm.setCurrency(PaymentConstant.CURRENCY);
		paymentForm.setTransaction_type(PaymentConstant.TRANSACTION_TYPE);
		paymentForm.setReference_number("nasdfjalskfasldkfjalf");
		paymentForm.setTransaction_uuid(UUID.randomUUID().toString());
		paymentForm.setSigned_field_names(PaymentConstant.SIGNED_FIELD);
		paymentForm.setUnsigned_field_names(PaymentConstant.UNSIGNED_FIELD);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String date = sdf.format(new Date());		
		paymentForm.setSigned_date_time(date);

		Map<String, String> d = new HashMap<String, String>();
		d.put("access_key", paymentForm.getAccess_key());
		d.put("profile_id", paymentForm.getProfile_id());
		d.put("transaction_uuid", paymentForm.getTransaction_uuid());
		d.put("signed_field_names", paymentForm.getSigned_field_names());
		d.put("unsigned_field_names", paymentForm.getUnsigned_field_names());
		d.put("signed_date_time", paymentForm.getSigned_date_time());
		d.put("locale", paymentForm.getLocale());
		d.put("currency", paymentForm.getCurrency());
		d.put("transaction_type", paymentForm.getTransaction_type());
		d.put("reference_number", paymentForm.getReference_number());
		d.put("amount", paymentForm.getAmount());

		paymentForm.setSignature(Security.sign(d));
		model.addAttribute("paymentForm", paymentForm);
		return "payment/paymentPost";
	}


}
