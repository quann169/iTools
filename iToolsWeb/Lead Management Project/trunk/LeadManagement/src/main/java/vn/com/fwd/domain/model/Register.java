package vn.com.fwd.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "T_NEWUL")
public class Register implements Serializable {
	
	/**
     * serial version id.
     */
    private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue
    @Column(name = "REGISTER_ID", unique = true, nullable = false)
	private Integer id;
    
    @Column(name = "REGISTER_CODE", nullable = false)
	private String fullName;
    
    @Column(name = "REGISTER_SELECTION", nullable = false)
	private String selection;
    
/*	@Column(name = "REGISTER_EMAIL", unique = true, nullable = true, length = 64)
	private String email;
    
    @Column(name = "REGISTER_MOBILE", nullable = true)
	private String mobileNumber;
    
    @Column(name = "REGISTER_ADDRESS", nullable = true)
	private String address;
    
    @Column(name = "REGISTER_NOTE", nullable = true)
	private String referral;*/
    
    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/*public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}*/

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
    public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
