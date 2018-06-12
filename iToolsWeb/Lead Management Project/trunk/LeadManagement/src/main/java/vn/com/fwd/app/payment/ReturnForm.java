package vn.com.fwd.app.payment;

import java.io.Serializable;

public class ReturnForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String decision;
	private String message;
	private String reason_code;
	private String req_card_type;
	private String req_card_number;
	private String req_card_expiry_date;

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getReq_card_type() {
		return req_card_type;
	}

	public void setReq_card_type(String req_card_type) {
		this.req_card_type = req_card_type;
	}

	public String getReq_card_number() {
		return req_card_number;
	}

	public void setReq_card_number(String req_card_number) {
		this.req_card_number = req_card_number;
	}

	public String getReq_card_expiry_date() {
		return req_card_expiry_date;
	}

	public void setReq_card_expiry_date(String req_card_expiry_date) {
		this.req_card_expiry_date = req_card_expiry_date;
	}

}
