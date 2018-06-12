package vn.com.fwd.service.impl;


public class SMSServiceImpl {
	private String smsEndpoint;
	private String smsUsername;
	private String smsPassword;
	private String smsToken;
	private String smsType;
	private String smsMessages;
	
	public SMSServiceImpl() {
		
	}

	public String getSmsEndpoint() {
		return smsEndpoint;
	}

	public void setSmsEndpoint(String smsEndpoint) {
		this.smsEndpoint = smsEndpoint;
	}

	public String getSmsUsername() {
		return smsUsername;
	}

	public void setSmsUsername(String smsUsername) {
		this.smsUsername = smsUsername;
	}

	public String getSmsPassword() {
		return smsPassword;
	}

	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}

	public String getSmsToken() {
		return smsToken;
	}

	public void setSmsToken(String smsToken) {
		this.smsToken = smsToken;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getSmsMessages() {
		return smsMessages;
	}

	public void setSmsMessages(String smsMessages) {
		this.smsMessages = smsMessages;
	}
}
