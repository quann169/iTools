package com.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.LoginController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.StringUtils;

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
	
	
	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	final static Logger logger = Logger.getLogger(LoginPage.class);

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

		logoLabel.setBounds(220, 60, 350, 60);
		logoLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 50));

		userLabel.setBounds(70, 130, 150, 60);
		userLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		userTextField.setBounds(220, 150, 300, 30);

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

		passwordLabel.setBounds(70, 180, 150, 60);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		passwordField.setBounds(220, 200, 300, 30);
		
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

		showPassword.setBounds(216, 235, 350, 30);
		showPassword.setFont(new Font(labelFont.getName(), Font.BOLD + Font.ITALIC, 13));

		loginButton.setEnabled(false);
		loginButton.setBounds(220, 280, 140, 30);
		loginButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		forgotPwdButton.setBounds(365, 280, 155, 30);
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
			
			userText = "com1user2";
			pwdText = "123456";
			
			logger.info("Login with username: " + userText);
			LoginController ctlObj = new LoginController();
			Assessor result = ctlObj.validateUser(userText, pwdText);
			
			if (result != null) {
				logger.info("Login OK");
				String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
				List<Role> listRoles = ctlObj.getUserRoles(userText, companyCode);
				logger.info("listRoles: " + listRoles);
				
				System.out.println(listRoles.get(0).getRoleName() + "   " + Enum.EMP.text());
				if (listRoles.size() == 0) {
					JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Have_Not_Role"));
					logger.info("User does not have role");
				} else if (listRoles.size() == 1 && Enum.EMP.text().equals(listRoles.get(0).getRoleName())) {
					EmployeePage empPage = new EmployeePage();
					StringUtils.frameInit(empPage, bundleMessage);
					empPage.setTitle(userText + " - " + result.getFirstName() + " " + result.getLastName());
					empPage.show();
				} else {
					DashboardPage dashboardPage = new DashboardPage();
					StringUtils.frameInit(dashboardPage, bundleMessage);
					dashboardPage.show();
				}
				
//				JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Login_Successful"));
			} else {
				JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Login_Fail"));
				logger.info("Login Fail");
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

	
	
	

	public static void main(String[] a) {
		LoginPage frame = new LoginPage();
		StringUtils.frameInit(frame, bundleMessage);
		
		
		frame.setTitle(bundleMessage.getString("Login_Page_Title"));
		frame.getRootPane().setDefaultButton(frame.loginButton);
		
		
	}

}
