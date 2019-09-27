package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.LoginController;
import com.controllers.UserController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.FilterComboBox;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class ResetPasswordPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));
	JLabel changePassLabel = new JLabel(bundleMessage.getString("App_ChangePassword"));
	JLabel splitLabel = new JLabel(" | ");

	JLabel backToDashboardLabel = new JLabel(bundleMessage.getString("Employee_Back_To_Dashboard"));

	JLabel usernameLabel = new JLabel(bundleMessage.getString("ResetPassword_Page_Username"));
	JLabel passwordLabel = new JLabel(bundleMessage.getString("ResetPassword_Page_Password"));
	JLabel rePasswordLabel = new JLabel(bundleMessage.getString("ResetPassword_Page_RePassword"));
	JLabel firstTimeLoginLabel = new JLabel();

	JLabel matchPasswordLabel = new JLabel();

	JComboBox<String> usernameComboBox = new JComboBox<String>();
	JPasswordField passwordTextField = new JPasswordField();
	JPasswordField rePasswordTextField = new JPasswordField();

	JButton resetPassButton = new JButton();
	JTextField usernameTextBox = new JTextField();

	JCheckBox isFirstChange = new JCheckBox(bundleMessage.getString("ResetPassword_Page_FirstChange"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	static String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	static String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
	private static final String companyCodeUH = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE_UH"));
	static String userName = "";
	final static Logger logger = Logger.getLogger(ResetPasswordPage.class);
	UserController empCtlObj = new UserController();
	static EmailUtils emailUtils = new EmailUtils(Enum.LOGIN, userName, companyCode, machineCode);
	static LoginController ctlObj = new LoginController();

	Assessor user;
	Map<String, Assessor> mapDisplayName = new HashMap<>();
	int resultValue;
	boolean isDashboard;
	boolean isFirstTimeLogin;
	public PopUpKeyboard keyboard;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	String firstName, lastName = "";

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int windowWidth = (int) screenSize.getWidth();
	static int windowHeight = (int) screenSize.getHeight();
	static int extWidth = (windowWidth > 900) ? 0 : 0;
	static int extHeight = (windowHeight > 700) ? 0 : 0;

	@SuppressWarnings("static-access")
	ResetPasswordPage(Assessor user, boolean isDashboard, boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
		this.isDashboard = isDashboard;
		this.user = user;
		this.userName = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		logger.info("Init " + Enum.RESET_PASS_PAGE);
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = usernameLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(15 + extWidth, 5 + extHeight, 300, 70);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				updateTimer.restart();
				logger.info(userName + " back to dashboard from " + Enum.RESET_PASS_PAGE);
				JFrame old = root;

				List<Role> listRoles = ctlObj.getUserRoles(userName, companyCode, companyCodeUH);
				root = new DashboardPage(listRoles, user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(userName + " - " + user.getFirstName() + " " + user.getLastName());
				old.dispose();
			}
		});

		if (this.isDashboard) {
			backToDashboardLabel.setEnabled(true);
		} else {
			backToDashboardLabel.setEnabled(false);
		}

		changePassLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(530 + extWidth, 5 + extHeight, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();

				logger.info(userName + " click change password from " + Enum.RESET_PASS_PAGE);
				JFrame old = root;
				root = new ChangePasswordPage(user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Change_Password_Page_Title"));
				root.getRootPane().setDefaultButton(((ChangePasswordPage) root).changePassButton);
				old.dispose();
			}
		});

		splitLabel.setBounds(693 + extWidth, 5 + extHeight, 25, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		logOutLabel.setText("<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(715 + extWidth, 5 + extHeight, 150, 60);
		logOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();
				logger.info(userName + " logout.");
				JFrame old = root;
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
				old.dispose();
			}
		});

		usernameLabel.setBounds(70 + extWidth, 75 + extHeight, 250, 40);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		usernameTextBox.setBounds(260 + extWidth, 80 + extHeight, 450, 30);
		usernameTextBox.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		List<Assessor> listUsers = empCtlObj.getUsersOfCompany(companyCode);
		Collections.sort(listUsers, new Comparator<Assessor>() {
			public int compare(Assessor o1, Assessor o2) {
				String fullName1 = o1.getFirstName() + " " + o1.getLastName();
				String fullName2 = o2.getFirstName() + " " + o2.getLastName();
				return fullName1.compareToIgnoreCase(fullName2);
			}
		});

		List<String> listUserNames = new ArrayList<>();

		if (isFirstTimeLogin) {
			String displayName = firstName + " " + lastName + " - " + user.getUsername();
			usernameTextBox.setText(displayName);
			usernameTextBox.setEditable(false);

		} else {
			// usernameComboBox.addItem("");
			listUserNames.add("");
			for (Assessor user : listUsers) {
				String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
				mapDisplayName.put(displayName, user);
				if (!user.getUsername().equals(this.user.getUsername())) {
					listUserNames.add(displayName);
				}

			}

			usernameComboBox = new FilterComboBox(listUserNames, keyboard);
			usernameComboBox.setBounds(260 + extWidth, 80 + extHeight, 450, 30);
			usernameComboBox.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
			usernameComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					validateAllFields();
				}
			});
		}

		//
		//
		//
		// new AutoCompletion(usernameComboBox, keyboard);

		passwordLabel.setBounds(70 + extWidth, 135 + extHeight, 350, 40);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		passwordTextField.setBounds(260 + extWidth, 140 + extHeight, 450, 30);
		passwordTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		passwordTextField.setText("");

		passwordTextField.getDocument().addDocumentListener(new DocumentListener() {
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
				validateAllFields();
			}
		});

		passwordTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (passwordTextField.getPassword().length >= 8)
					e.consume();
			}
		});

		matchPasswordLabel.setBounds(260 + extWidth, 170 + extHeight, 450, 30);

		rePasswordLabel.setBounds(70 + extWidth, 195 + extHeight, 350, 40);
		rePasswordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		rePasswordTextField.setBounds(260 + extWidth, 200 + extHeight, 450, 30);
		rePasswordTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		rePasswordTextField.setText("");

		rePasswordTextField.getDocument().addDocumentListener(new DocumentListener() {
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
				validateAllFields();
			}
		});

		rePasswordTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (rePasswordTextField.getPassword().length >= 8)
					e.consume();
			}
		});

		isFirstChange.setBounds(255 + extWidth, 245 + extHeight, 400, 30);
		isFirstChange.setFont(new Font(labelFont.getName(), Font.ITALIC, 17));
		// isFirstTimeLogin = true;
		if (isFirstTimeLogin) {
			usernameComboBox.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
			firstTimeLoginLabel.setText(bundleMessage.getString("ResetPassword_Page_FirstTimeLoginLabel"));
			firstTimeLoginLabel.setBounds(200 + extWidth, 40 + extHeight, 550, 30);
			firstTimeLoginLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

			resetPassButton.setText(bundleMessage.getString("ResetPassword_Page_FirstTimeLogin"));
			resetPassButton.setBounds(260 + extWidth, 270 + extHeight, 300, 35);
			resetPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
		} else {
			resetPassButton.setText(bundleMessage.getString("ResetPassword_Page_ResetPassword"));
			resetPassButton.setBounds(260 + extWidth, 290 + extHeight, 300, 35);
			resetPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
		}

		resetPassButton.setEnabled(false);

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container,
						"<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>",
						"Time Out Reset Pass", JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.RESET_PASS_PAGE + " time out.");
				JFrame old = root;
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
				old.dispose();
			}
		});
		updateTimer.setRepeats(false);
		updateTimer.restart();
	}

	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			logger.error("validateAllFields of resetpasspage");
		}

		String password = passwordTextField.getText();
		String repassword = rePasswordTextField.getText();
		boolean userExisted = true;
		if (isFirstTimeLogin) {
			userExisted = true;
		} else {
			userExisted = mapDisplayName.containsKey(usernameComboBox.getSelectedItem().toString());
		}
		if (password.length() > 0 && repassword.length() > 0 && !password.equals(repassword)) {
			matchPasswordLabel.setText("<html><font size=\"3\" face=\"arial\" color=\"red\"><i>"
					+ bundleMessage.getString("ResetPassword_Page_Password_Not_Match") + "</i></font></html>");
			resetPassButton.setEnabled(false);
			return false;
		} else if (!"".equals(password) && password.equals(repassword) && userExisted) {
			matchPasswordLabel.setText("");
			resetPassButton.setEnabled(true);
			return true;
		} else {
			matchPasswordLabel.setText("");
			resetPassButton.setEnabled(false);
			return false;
		}
	}

	public void addComponentsToContainer() {

		container.add(logOutLabel);

		container.add(usernameLabel);

		container.add(passwordLabel);
		container.add(passwordTextField);
		container.add(rePasswordTextField);
		container.add(rePasswordLabel);
		container.add(matchPasswordLabel);

		if (!isFirstTimeLogin) {
			container.add(splitLabel);
			container.add(backToDashboardLabel);
			container.add(changePassLabel);
			container.add(isFirstChange);
			container.add(usernameComboBox);
		} else {
			container.add(firstTimeLoginLabel);
			container.add(usernameTextBox);

		}
		container.add(resetPassButton);

	}

	public void addActionEvent() {
		resetPassButton.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root,
						"<html><font size=\"5\" face=\"arial\">Are you sure you want to exit?</font></html>",
						"Confirm Close", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
						ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == resetPassButton) {

			JPanel panel = new JPanel();

			panel.setLayout(null);

			String password = rePasswordTextField.getText();

			final String usernameResetPass;
			if (isFirstTimeLogin) {
				usernameResetPass = userName;
			} else {
				usernameResetPass = mapDisplayName.get(usernameComboBox.getSelectedItem().toString()).getUsername();
			}
			int dialogResult;
			if (isFirstTimeLogin) {
				dialogResult = 0;
			} else {

				String confirm = "<html><font size=\"4\" face=\"arial\"><i>"
						+ bundleMessage.getString("ResetPassword_Page_Reset_Confirmation") + " " + usernameResetPass
						+ "?</i></font></html>";
				dialogResult = JOptionPane.showConfirmDialog(container, confirm, "Confirmation",
						JOptionPane.YES_NO_OPTION);
			}
			if (dialogResult == 0) {
				logger.info("Yes option");
				if (isFirstTimeLogin) {
					String newPass = empCtlObj.updatePassword(usernameResetPass, companyCode, password, 0);
					
					
					
					
					logger.info("Lock User");
					Thread one = new Thread() {
						public void run() {
							List<String> listCCEmail = new ArrayList<>();
							String emailUser;
							if (companyCode.contains("MFC")) {
								emailUser = ctlObj.getEmailSubAdmin(companyCode);
							} else {
								emailUser = ctlObj.getEmailUser(companyCode, usernameResetPass);
								listCCEmail.add(ctlObj.getEmailAdmin());
							}
							String fullName = ctlObj.getFullNameUser(companyCode, userName);
							emailUtils.sendEmail(emailUser, listCCEmail, "First Time Login Change Pass",
									"Hi " + fullName + "(" + usernameResetPass + "),\nYour pass has been change to " + newPass);

						}
					};

					one.start();
					logger.info("Change Pass of " + usernameResetPass);

					String confirm = "<html><font size=\"5\" face=\"arial\"><i>Completed Change Password</i></font></html>";
					JOptionPane.showMessageDialog(container, confirm, "Notify result", JOptionPane.INFORMATION_MESSAGE);
					logger.info("log out");
					logger.info(userName + " logout.");
					JFrame old = root;
					root = new LoginPage();
					StringUtils.frameInit(root, bundleMessage);

					root.setTitle(bundleMessage.getString("Login_Page_Title"));
					root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
					old.dispose();
				} else {
					empCtlObj.updatePassword(usernameResetPass, companyCode, password, 1);
					logger.info("Reset Pass of " + usernameResetPass);
					String confirm = "<html><font size=\"5\" face=\"arial\"><i>Completed Reset Password" + " "
							+ usernameResetPass + "</i></font></html>";
					JOptionPane.showMessageDialog(container, confirm, "Notify result", JOptionPane.INFORMATION_MESSAGE);
				}

				passwordTextField.setText("");
				rePasswordTextField.setText("");
				isFirstChange.setSelected(false);
				if (!isFirstTimeLogin) {
					usernameComboBox.setSelectedIndex(0);
				}

			} else {
				logger.info("No Option");
			}

		}
	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		usernameComboBox.getEditor().getEditorComponent().addFocusListener(focus1);
		passwordTextField.addFocusListener(focus1);
		rePasswordTextField.addFocusListener(focus1);
		usernameTextBox.addFocusListener(focus1);
	}

}
