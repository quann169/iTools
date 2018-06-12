package vn.com.fwd.app.payment;

public class PaymentConstant {

	public static final String PROFILE_ID = "D5CAAB70-2A6D-4921-9D39-6F15BD130E51";
	public static final String ACCESS_KEY = "b1473444750d3258a805de0160b74d01";
	public static final String SECRET_KEY = "95c18d5d383d4c1cab934eaa269521787c51abe27cb341d88df488e238f9197dd30f4762f095404bb6e7102566cfdbf272aba785d3ff45d2a178c5a73a11c8f54f759d873c30499cb2f98d055049aa152d1a7e516aa14fa284716c872a67a1127e847137b90c48879ed5017b4c67127f06493875d3974a79ae9d361222104e18";
	public static final String LOCALE = "vi-VN";
	public static final String TRANSACTION_TYPE = "sale";
	public static final String CURRENCY = "VND";
	public static final String SIGNED_FIELD = "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,currency,transaction_type,reference_number,amount";
	public static final String UNSIGNED_FIELD = "bill_address1,bill_city,bill_country,customer_email,customer_lastname";
}
