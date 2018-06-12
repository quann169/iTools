package vn.com.fwd.app.secure;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LeadRegisterForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1125854199046231935L;

	@Size(min = 1, max = 200)
	private String fullName;

	@NotNull
	private String mobileNumber;

	@NotNull
	private String email;

	@NotNull
	private String age;

	private String referral;

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}

	private Boolean agree;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Boolean getAgree() {
		return agree;
	}

	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
