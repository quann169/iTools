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
		System.out.println("--MMMMMMMMMMMMMMMMMMMMMMMMMM-");
		System.out.println(event);
		System.out.println(keyboard);
		
		if (keyboard == null) {
			return;
		}
		Object obj = event.getSource();
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQ: " + obj);
		if (obj instanceof JTextField) {
			keyboard.setTextField((JTextField) obj);
		}
		
		
		if (obj instanceof JComboBox) {
			System.out.println("---------------------DDDDDDDDDDDD-----------------");
//			keyboard.setTextComponent((MetalComboBoxEditor) obj);
		}
		
		System.out.println(obj instanceof BasicComboBoxEditor);
		System.out.println(obj.getClass());
//		keyboard.setTextField(textField);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
