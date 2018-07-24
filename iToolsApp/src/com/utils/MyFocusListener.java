package com.utils;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxEditor;

public class MyFocusListener implements FocusListener{
	
	PopUpKeyboard keyboard;
	public MyFocusListener(PopUpKeyboard keyboard) {
		this.keyboard = keyboard;
	}

	@Override
	public void focusGained(FocusEvent event) {		
		if (keyboard == null) {
			return;
		}
		Object obj = event.getSource();
		if (obj instanceof JTextField) {
			keyboard.setTextField((JTextField) obj);
		}
		
		
		if (obj instanceof JComboBox) {
//			keyboard.setTextComponent((MetalComboBoxEditor) obj);
		}
		
//		keyboard.setTextField(textField);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
