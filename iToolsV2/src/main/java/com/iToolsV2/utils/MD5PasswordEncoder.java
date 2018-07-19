package com.iToolsV2.utils;
 
import java.math.BigInteger;
import java.security.MessageDigest;

//import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
 
public class MD5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        String encPass = "";
       try {
            /*MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(charSequence.toString().getBytes());
            byte[] b64 = Base64.encodeBase64(digest);
            encPass = new String(b64);
            encPass = encPass.replaceAll("=", "");*/
    	   MessageDigest m = MessageDigest.getInstance("MD5");
           m.update(charSequence.toString().getBytes(), 0, charSequence.toString().length());
           BigInteger i = new BigInteger(1,m.digest());
           encPass = String.format("%1$032x", i); 
        }catch(Exception ex){
            //logger.error("An exception trying to encode a password", ex);
        }
        return encPass;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encode(charSequence).equals(s);
    }
}