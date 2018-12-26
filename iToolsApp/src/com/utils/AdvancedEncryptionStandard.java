package com.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.views.DashboardPage;

public class AdvancedEncryptionStandard {
	private static byte[] key = "MZygpewJsCdr4a1y".getBytes(StandardCharsets.UTF_8);
	private static byte[] initVector = "MZygpewJsCpRrfOr".getBytes(StandardCharsets.UTF_8);

	private static final String ALGORITHM = "AES";
	final static Logger logger = Logger.getLogger(AdvancedEncryptionStandard.class);

	// public AdvancedEncryptionStandard(byte[] key) {
	// this.key = key;
	// }

	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
//			System.out.println("encrypted " + value + " ==> " + Base64.encodeBase64String(encrypted));

			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			logger.error(value);
			logger.error(ex.getMessage());
//			ex.printStackTrace();
		}

		return null;
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			// System.out.println("decrypt " + encrypted + " ==> " + new
			// String(original));
			return new String(original);
		} catch (Exception ex) {
//			ex.printStackTrace();
			logger.error(encrypted);
			logger.error(ex.getMessage());
		}

		return null;
	}

	public static void main(String[] a) throws Exception {

//		System.out.println(AdvancedEncryptionStandard.decrypt("UcyZIDfhWhm4TEAuSwLV7w=="));
//		System.out.println(AdvancedEncryptionStandard.decrypt("KG68kUlf8UKiI+qZYZFt5A=="));
//		System.out.println(AdvancedEncryptionStandard.decrypt("iNsabt5tGQ4+JPjalpYuvQ=="));
//		System.out.println(AdvancedEncryptionStandard.decrypt("evNaJwjjy9S1qudJ3/VSUA=="));
		
//		System.out.println("Optiwell: " + AdvancedEncryptionStandard.encrypt("Optiwell"));
		System.out.println("VINAMOILTOOLS: " + AdvancedEncryptionStandard.encrypt("VINAMOILTOOLS"));
		System.out.println("MFC: " + AdvancedEncryptionStandard.encrypt("MFC"));
//		System.out.println("VM-1: " + AdvancedEncryptionStandard.encrypt("VM-1"));
//		System.out.println("VM-2: " + AdvancedEncryptionStandard.encrypt("VM-2"));
//		System.out.println("VM-3: " + AdvancedEncryptionStandard.encrypt("VM-3"));
//		System.out.println("VM-4: " + AdvancedEncryptionStandard.encrypt("VM-4"));
		
//
//		plainText = "1qazxsw2!A";
//		cipherText = AdvancedEncryptionStandard.encrypt(plainText);
//		AdvancedEncryptionStandard.decrypt(cipherText);
//		System.out.println();
//
//		plainText = "mail.tqteam.net";
//		cipherText = AdvancedEncryptionStandard.encrypt(plainText);
//		AdvancedEncryptionStandard.decrypt(cipherText);
//		System.out.println();
//
//		plainText = "25";
//		cipherText = AdvancedEncryptionStandard.encrypt(plainText);
//		AdvancedEncryptionStandard.decrypt(cipherText);
//		System.out.println();
//
//		plainText = "MAC5";
//		cipherText = AdvancedEncryptionStandard.encrypt(plainText);
//		AdvancedEncryptionStandard.decrypt(cipherText);
//		System.out.println();

	}
}
