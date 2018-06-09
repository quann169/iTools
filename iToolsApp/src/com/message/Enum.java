package com.message;

public enum Enum {

	// Role
	ADMIN("Admin"), SUBADMIN("SubAdmin"), ACCT("Accounting"), EMP("Emp"), OTHER("Other"), PUTIN("PutIns"), TKOVER(
			"TakeOver"), EDTTRANS("EditTransaction"),

	;

	private String text;

	Enum(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}
}
