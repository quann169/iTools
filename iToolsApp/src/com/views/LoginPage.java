package com.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.controllers.LoginController;
import com.message.Enum;
import com.models.Assessor;

public class LoginPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages", new Locale("vn", "VN"));
	Container container = getContentPane();
	JLabel logoLabel = new JLabel(bundleMessage.getString("Login_Page_iTools_Logo"));
	JLabel userLabel = new JLabel(bundleMessage.getString("Login_Page_Username"));
	JLabel passwordLabel = new JLabel(bundleMessage.getString("Login_Page_Password"));
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton(bundleMessage.getString("Login_Page_Login"));
	JButton forgotPwdButton = new JButton(bundleMessage.getString("Login_Page_Forget_Password"));
	JCheckBox showPassword = new JCheckBox(bundleMessage.getString("Login_Page_Show_Password"));
	
	

	LoginPage() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = logoLabel.getFont();

		logoLabel.setBounds(250, 60, 350, 60);
		logoLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 50));

		userLabel.setBounds(100, 150, 150, 60);
		userLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		userTextField.setBounds(250, 170, 300, 30);

		userTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (userTextField.getText().length() > 0 && passwordField.getText().length() > 0) {
					loginButton.setEnabled(true);
				}
			}
		});

		passwordLabel.setBounds(100, 200, 150, 60);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		passwordField.setBounds(250, 220, 300, 30);
		
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (userTextField.getText().length() > 0 && passwordField.getText().length() > 0) {
					loginButton.setEnabled(true);
				}
			}
		});

		showPassword.setBounds(246, 255, 350, 30);
		showPassword.setFont(new Font(labelFont.getName(), Font.ITALIC, 20));

		loginButton.setEnabled(false);
		loginButton.setBounds(250, 300, 100, 30);
		loginButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		forgotPwdButton.setBounds(370, 300, 180, 30);
		forgotPwdButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

	}

	public void addComponentsToContainer() {
		container.add(logoLabel);
		container.add(userLabel);
		container.add(passwordLabel);
		container.add(userTextField);
		container.add(passwordField);
		container.add(showPassword);
		container.add(loginButton);
		container.add(forgotPwdButton);
	}

	public void addActionEvent() {
		loginButton.addActionListener(this);
		forgotPwdButton.addActionListener(this);
		showPassword.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String userText = userTextField.getText();
			String pwdText = passwordField.getText();

			LoginController ctlObj = new LoginController();
			boolean result = ctlObj.validateUser(userText, pwdText);

			if (result) {
				List<Assessor> listUsers = ctlObj.getAllUsers();

				for (Assessor assessor : listUsers) {
					System.out.println(assessor);
				}

				JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Login_Successful"));
			} else {
				JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Login_Fail"));
			}

		}
		if (e.getSource() == forgotPwdButton) {
			userTextField.setText("");
			passwordField.setText("");
		}
		if (e.getSource() == showPassword) {
			if (showPassword.isSelected()) {
				passwordField.setEchoChar((char) 0);
			} else {
				passwordField.setEchoChar('*');
			}

		}
	}

	private static void titleAlign(JFrame frame) {

		Font font = frame.getFont();

		String currentTitle = frame.getTitle().trim();
		FontMetrics fm = frame.getFontMetrics(font);
		int frameWidth = frame.getWidth();
		int titleWidth = fm.stringWidth(currentTitle);
		int spaceWidth = fm.stringWidth(" ");
		int centerPos = (frameWidth / 2) - (titleWidth / 2);
		int spaceCount = centerPos / spaceWidth;
		String pad = "";
		pad = String.format("%" + (spaceCount - 14) + "s", pad);
		frame.setTitle(pad + currentTitle);

	}

	public static void main(String[] a) {
		LoginPage frame = new LoginPage();
		frame.setTitle(bundleMessage.getString("Login_Page_Title"));
		frame.setVisible(true);
		frame.setBounds(0, 0, 700, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				titleAlign(frame);
			}

		});
		frame.setResizable(false);
		frame.getRootPane().setDefaultButton(frame.loginButton);
	}

}
