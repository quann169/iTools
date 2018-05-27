package com.message;

public enum Enum {
	
	CFG_FILE("messageFile");
	
	private String text;

	Enum(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
