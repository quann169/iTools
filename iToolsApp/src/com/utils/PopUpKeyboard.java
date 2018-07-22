package com.utils;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import static java.awt.event.KeyEvent.*;

public class PopUpKeyboard extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5491828705390945154L;
	private Object editField;
	private JTextField textField;
	private JTextComponent textComponent;
	private boolean isTextFieldFocus;
	private boolean isComboBoxFocus;

	public PopUpKeyboard() {
		// this.textField = textField;
		// this.setResizable(false);

		this.setPreferredSize(new Dimension(800, 250));
		keyboardVariables();
	}

	/**
	 * 
	 */

	private void keyboardVariables() {

		String firstRow[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		String secondRow[] = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
		String thirdRow[] = { "A", "S", "D", "F", "G", "H", "J", "K", "L", "Enter" };
		String fourthRow[] = { "Z", "X", "C", "V", "B", "N", "M", "Tab", "Space", "<<" };
		// String fifthRow[] = { "Space" };

		JButton first[];
		JButton second[];
		JButton third[];
		JButton fourth[];

		setLayout(new BorderLayout());
		Font font = new Font("SansSerif", Font.BOLD, 25);
		JPanel jpNorth = new JPanel();
		JPanel jpCenter = new JPanel();
		JPanel jpKeyboard = new JPanel();
		add(jpNorth, BorderLayout.NORTH);
		add(jpCenter, BorderLayout.CENTER);
		add(jpKeyboard, BorderLayout.SOUTH);
		jpNorth.setLayout(new BorderLayout());
		jpCenter.setLayout(new BorderLayout());
		jpKeyboard.setLayout(new GridLayout(4, 1));
		// pack();

		first = new JButton[firstRow.length];
		JPanel p = new JPanel(new GridLayout(1, firstRow.length));
		for (int i = 0; i < firstRow.length; i++) {
			JButton b = new JButton(firstRow[i]);
			b.setFont(font);
			b.setPreferredSize(new Dimension(100, 50));
			b.addActionListener(this);
			first[i] = b;
			p.add(first[i]);
		}
		jpKeyboard.add(p);

		second = new JButton[secondRow.length];
		p = new JPanel(new GridLayout(1, secondRow.length));
		for (int i = 0; i < secondRow.length; ++i) {
			second[i] = new JButton(secondRow[i]);
			second[i].setFont(font);
			second[i].addActionListener(this);
			p.add(second[i]);

		}
		jpKeyboard.add(p);

		third = new JButton[thirdRow.length];
		p = new JPanel(new GridLayout(1, thirdRow.length));
		for (int i = 0; i < thirdRow.length; ++i) {
			third[i] = new JButton(thirdRow[i]);
			third[i].setFont(font);
			if (i == thirdRow.length - 1) {
				third[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
			} else {
				third[i].setFont(font);
			}
			third[i].addActionListener(this);
			p.add(third[i]);
		}
		jpKeyboard.add(p);

		fourth = new JButton[fourthRow.length];
		p = new JPanel(new GridLayout(1, fourthRow.length + 1));
		for (int i = 0; i < fourthRow.length; ++i) {
			fourth[i] = new JButton(fourthRow[i]);
			fourth[i].addActionListener(this);
			if (i == fourthRow.length - 2) {
				fourth[i].setFont(new Font("SansSerif", Font.PLAIN, 15));
			} else if (i == fourthRow.length - 3) {
				fourth[i].setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 15));
			} else {
				fourth[i].setFont(font);
			}

			p.add(fourth[i]);
		}
		jpKeyboard.add(p);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		// boolean capslock = false;
		// boolean shift = true;
		System.out.println(e);
		System.out.println("RRRRRRRRRRRRRRRRR");

		System.out.println("isTextFieldFocus: " + this.isTextFieldFocus);
		System.out.println("isComboBoxFocus: " + this.isComboBoxFocus);
		if (this.isTextFieldFocus) {
			if (textField == null) {
				return;
			}
			if (action == "<<") {
				if (!"".equals(textField.getText())) {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
				}

			} else if (action == "Tab") {
				System.out.println("AAAAAAAAAAAAA");
			} else if (action == "Space") {
				textField.setText(textField.getText() + " ");
			} else if (action == "Enter") {
				textField.setText(textField.getText() + "\n");
			} else {
				textField.setText(textField.getText() + action);
			}
//			Robot robot;
//			try {
//				robot = new Robot();
//				System.out.println("Press : " + action);
//				type(robot, action.toCharArray()[0]);
//			} catch (AWTException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

		}

		// if (this.isComboBoxFocus) {
		// System.out.println("EEEEEEEEEE");
		// if (textComponent == null) {
		// return;
		// }
		// // textComponent.getEditorComponent();
		//
		// // if (action == "<<") {
		// // textComponent.setText(textComponent.substring(0,
		// // textComponent.getEditorComponent().length() - 1));
		// // } else if (action == "Tab") {
		// // System.out.println("AAAAAAAAAAAAA");
		// // } else if (action == "Space") {
		// // textComponent.setText(textComponent.getEditorComponent() + " ");
		// // } else if (action == "Enter") {
		// // textComponent.setText(textComponent.getEditorComponent() + "\n");
		// // } else {
		// // textComponent.setText(textComponent.getEditorComponent() +
		// // action);
		// // }
		// }

	}

	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}

	public void type(Robot robot, char c) {
		String altCode = Integer.toString(c);
//		for (int i = 0; i < altCode.length(); i++) {
//			c = (char) (altCode.charAt(i) + '0');
//			// delay(20);//may be needed for certain applications
		robot.keyPress(c);
//		robot.notifyAll();
			System.out.println("keyPress: " + c);
			// delay(20);//uncomment if necessary
			robot.keyRelease(c);
//		}
	}

	/**
	 * @param textField
	 *            the textField to set
	 */
	public void setTextField(JTextField textField) {
		System.out.println("XXXXXXXX");
		this.textField = textField;
		this.isComboBoxFocus = false;
		this.isTextFieldFocus = true;
	}

	/**
	 * @param editor
	 *            the textComponent to set
	 */
	// public void setCombobox(JTextComponent editor) {
	// System.out.println("AAAAAAAAAAAAAAAa");
	// this.textComponent = editor;
	// this.isComboBoxFocus = true;
	// this.isTextFieldFocus = false;
	// }
}