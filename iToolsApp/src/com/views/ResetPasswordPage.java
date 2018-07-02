package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.MessageFormat;
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

import com.controllers.LogController;
import com.controllers.UserController;
import com.message.Enum;
import com.models.Assessor;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
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
	JTextField passwordTextField = new JPasswordField();
	JTextField rePasswordTextField = new JPasswordField();

	JButton resetPassButton = new JButton();

	JCheckBox isFirstChange = new JCheckBox(bundleMessage.getString("ResetPassword_Page_FirstChange"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	final static Logger logger = Logger.getLogger(ResetPasswordPage.class);
	UserController empCtlObj = new UserController();

	LogController masterLogObj = new LogController();

	String userName = "";

	Assessor user;
	Map<String, Assessor> mapDisplayName = new HashMap<>();
	int resultValue;
	boolean isDashboard;
	boolean isFirstTimeLogin;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	ResetPasswordPage(Assessor user, boolean isDashboard, boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
		this.isDashboard = isDashboard;
		this.user = user;
		this.userName = user.getUsername();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.RESET_PASS_PAGE, "", "", companyCode, machineCode,
				StringUtils.getCurrentClassAndMethodNames());
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = usernameLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		backToDashboardLabel.setText("<html><html><font size=\"6\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(15, 10, 270, 60);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("backToDashboardLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.SHOW_DASHBOARD, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
			}
		});

		if (this.isDashboard) {
			backToDashboardLabel.setEnabled(true);
		} else {
			backToDashboardLabel.setEnabled(false);
		}

		changePassLabel.setText("<html><html><font size=\"6\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(450, 10, 250, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("changePassLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.CHANGE_PASS, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				updateTimer.restart();
			}
		});

		splitLabel.setBounds(665, 10, 20, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		logOutLabel.setText("<html><font size=\"6\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(685, 10, 150, 60);
		logOutLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateTimer.restart();
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOGOUT, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				logger.info(userName + " logout.");
				root.dispose();
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
			}
		});

		usernameLabel.setBounds(100, 100, 150, 60);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		usernameComboBox.setBounds(270, 110, 250, 30);
		usernameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateAllFields();
			}
		});
		usernameComboBox.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				usernameComboBox.showPopup();
			}
		});
		String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
		this.companyCode = companyCode;
		List<Assessor> listUsers = empCtlObj.getUsersOfCompany(companyCode);
		Collections.sort(listUsers, new Comparator<Assessor>() {
			public int compare(Assessor o1, Assessor o2) {
				String fullName1 = o1.getFirstName() + " " + o1.getLastName();
				String fullName2 = o2.getFirstName() + " " + o2.getLastName();
				return fullName1.compareToIgnoreCase(fullName2);
			}
		});

		if (isFirstTimeLogin) {

			String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
			mapDisplayName.put(displayName, user);
			usernameComboBox.addItem(displayName);
			usernameComboBox.setEnabled(false);
		} else {
			usernameComboBox.addItem("");
			for (Assessor user : listUsers) {
				String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
				mapDisplayName.put(displayName, user);
				if (!user.getUsername().equals(this.user.getUsername())) {
					usernameComboBox.addItem(displayName);
				}

			}
		}
		AutoCompletion.enable(usernameComboBox);

		passwordLabel.setBounds(100, 160, 170, 60);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		passwordTextField.setBounds(270, 170, 250, 30);
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

		rePasswordLabel.setBounds(100, 220, 170, 60);
		rePasswordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 18));

		matchPasswordLabel.setBounds(270, 200, 170, 30);

		rePasswordTextField.setBounds(270, 230, 250, 30);
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

		isFirstChange.setBounds(270, 270, 300, 30);
		isFirstChange.setFont(new Font(labelFont.getName(), Font.ITALIC, 15));

		if (isFirstTimeLogin) {

			firstTimeLoginLabel.setText(bundleMessage.getString("ResetPassword_Page_FirstTimeLoginLabel"));
			firstTimeLoginLabel.setBounds(200, 60, 400, 30);
			firstTimeLoginLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

			resetPassButton.setText(bundleMessage.getString("ResetPassword_Page_FirstTimeLogin"));
			resetPassButton.setBounds(270, 290, 200, 30);
			resetPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
		} else {
			resetPassButton.setText(bundleMessage.getString("ResetPassword_Page_ResetPassword"));
			resetPassButton.setBounds(270, 310, 200, 30);
			resetPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
		}

		resetPassButton.setEnabled(false);

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog(userName, Enum.LOCK_UNLOCK_PAGE, "", Enum.TIME_OUT, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Reset Pass",
						JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.LOCK_UNLOCK_PAGE + " time out.");
				root.dispose();
				root = new LoginPage();
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(bundleMessage.getString("Login_Page_Title"));
				root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
			}
		});
		updateTimer.setRepeats(false);
		updateTimer.restart();
	}

	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			System.err.println("validateAllFields of resetpasspage");
		}

		String password = passwordTextField.getText();
		String repassword = rePasswordTextField.getText();
		boolean userExisted = mapDisplayName.containsKey(usernameComboBox.getSelectedItem().toString());

		if (password.length() > 0 && repassword.length() > 0 && !password.equals(repassword)) {
			matchPasswordLabel.setText("<html><font size=\"3\" face=\"arial\" color=\"red\"><i>"
					+ bundleMessage.getString("ResetPassword_Page_Password_Not_Match") + "</i></font></html>");
			resetPassButton.setEnabled(false);
			return false;
		} else if (password != "" && password.equals(repassword) && userExisted) {
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
		container.add(usernameComboBox);

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
		} else {
			container.add(firstTimeLoginLabel);

		}
		container.add(resetPassButton);

	}

	public void addActionEvent() {
		resetPassButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == resetPassButton) {

			JPanel panel = new JPanel();

			panel.setLayout(null);

			String password = rePasswordTextField.getText();
			String usernameResetPass = mapDisplayName.get(usernameComboBox.getSelectedItem().toString()).getUsername();
			int dialogResult;
			if (isFirstTimeLogin) {
				dialogResult = 0;
			} else {
				dialogResult = JOptionPane.showConfirmDialog(container,
						bundleMessage.getString("ResetPassword_Page_Reset_Confirmation") + " " + usernameResetPass
								+ "?",
						"Confirmation", JOptionPane.YES_NO_OPTION);
			}
			if (dialogResult == 0) {
				System.out.println("Yes option");
				if (isFirstTimeLogin) {
					empCtlObj.updatePassword(usernameResetPass, this.companyCode, password, false);
					masterLogObj.insertLog(userName, Enum.ASSESSOR, "Password", Enum.UPDATE_PASS_FIRST_TIME_LOGIN,
							usernameResetPass + " - " + user.getPassword(), "XXX", companyCode, machineCode,
							StringUtils.getCurrentClassAndMethodNames());
				} else {
					empCtlObj.updatePassword(usernameResetPass, this.companyCode, password, true);
					masterLogObj.insertLog(userName, Enum.ASSESSOR, "Password", Enum.RESET_PASS,
							usernameResetPass + " - " + user.getPassword(), "XXX", companyCode, machineCode,
							StringUtils.getCurrentClassAndMethodNames());
				}

				JOptionPane.showMessageDialog(container, "Completed Reset Password!", "Notify result",
						JOptionPane.INFORMATION_MESSAGE);

				passwordTextField.setText("");
				rePasswordTextField.setText("");
				isFirstChange.setSelected(false);
				usernameComboBox.setSelectedIndex(0);

			} else {
				System.out.println("No Option");
			}

		}
	}

}
