package vn.com.fwd.app.payment;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Security {
	private static final String HMAC_SHA256 = "HmacSHA256";

	public static String sign(Map<String, String> params)
			throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		return sign(buildDataToSign(params), PaymentConstant.SECRET_KEY);
	}

	public static String sign(String data, String secretKey)
			throws InvalidKeyException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(),
				HMAC_SHA256);
		Mac mac = Mac.getInstance(HMAC_SHA256);
		mac.init(secretKeySpec);
		byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
		return DatatypeConverter.printBase64Binary(rawHmac).replace("\n", "");
	}

	private static String buildDataToSign(Map<String, String> params) {
		String[] signedFieldNames = String.valueOf(
				params.get("signed_field_names")).split(",");
		ArrayList<String> dataToSign = new ArrayList<String>();
		for (String signedFieldName : signedFieldNames) {
			dataToSign.add(signedFieldName + "="
					+ String.valueOf(params.get(signedFieldName)));
		}
		return commaSeparate(dataToSign);
	}

	private static String commaSeparate(ArrayList<String> dataToSign) {
		StringBuilder csv = new StringBuilder();
		for (Iterator<String> it = dataToSign.iterator(); it.hasNext();) {
			csv.append(it.next());
			if (it.hasNext()) {
				csv.append(",");
			}
		}
		return csv.toString();
	}
}
