package com.message;

public enum Enum {
	
	Login_Page_Title("Login_Page_Title"),
	Login_Successful("Login_Successful"),
	Login_Fail("Login_Fail")
	;
	
	private String text;

	Enum(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
