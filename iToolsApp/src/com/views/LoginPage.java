package com.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.LogController;
import com.controllers.LoginController;
import com.controllers.SyncController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.RandomString;
import com.utils.StringUtils;

public class LoginPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	JLabel logoLabel = new JLabel(bundleMessage.getString("Login_Page_iTools_Logo"));
	JLabel userLabel = new JLabel(bundleMessage.getString("Login_Page_Username"));
	JLabel passwordLabel = new JLabel(bundleMessage.getString("Login_Page_Password"));
	JTextField userTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton(bundleMessage.getString("Login_Page_Login"));
	JButton forgotPwdButton = new JButton(bundleMessage.getString("Login_Page_Forget_Password"));
	JCheckBox showPassword = new JCheckBox(bundleMessage.getString("Login_Page_Show_Password"));

	LogController masterLogObj = new LogController();
	static LoginController ctlObj = new LoginController();

	private static final Config cfg = new Config();
	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String companyCodeUH = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE_UH"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));

	static long timeInterval = Long.valueOf(cfg.getProperty("INTERVAL_SYNC"));
	static String userName = "";
	static SyncController syncCtl = new SyncController();
	static EmailUtils emailUtils = new EmailUtils(Enum.LOGIN, userName, companyCode, machineCode);
	final static Logger logger = Logger.getLogger(LoginPage.class);

	static JFrame root;

	LoginPage() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setUndecorated(true);
		setVisible(true);

	}

	public void setLayoutManager() {
		container.setLayout(null);

	}

	public void setLocationAndSize() {
		Font labelFont = logoLabel.getFont();

		logoLabel.setBounds(240, 70, 350, 70);
		logoLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 60));

		userLabel.setBounds(80, 170, 250, 60);
		userLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 40));

		userTextField.setBounds(300, 180, 400, 50);
		userTextField.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
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

			@SuppressWarnings("deprecation")
			public void warn() {
				if (userTextField.getText().length() > 0 && passwordField.getText().length() > 0) {
					loginButton.setEnabled(true);
				}
			}
		});

		passwordLabel.setBounds(80, 260, 250, 60);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 40));

		passwordField.setBounds(300, 260, 400, 50);
		passwordField.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
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

			@SuppressWarnings("deprecation")
			public void warn() {
				if (userTextField.getText().length() > 0 && passwordField.getText().length() > 0) {
					loginButton.setEnabled(true);
				}
			}
		});

		showPassword.setBounds(295, 315, 350, 50);
		showPassword.setFont(new Font(labelFont.getName(), Font.BOLD + Font.ITALIC, 20));

		loginButton.setEnabled(false);
		loginButton.setBounds(200, 380, 210, 50);
		loginButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		forgotPwdButton.setBounds(435, 380, 270, 50);
		forgotPwdButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

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

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		String userText = userTextField.getText();
		String pwdText = passwordField.getText();

		userName = userTextField.getText();
		if (e.getSource() == loginButton) {

			userText = "com1admin";
			// userText = "uhacc1";
			// pwdText = "123456";

			logger.info("Login with username: " + userText);

			Assessor result = ctlObj.validateUser(userText, pwdText);
			if (result != null) {
				logger.info("Login OK");

				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOGIN, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());

				if (result.isFirstTimeLogin()) {
					logger.info("isFirstTimeLogin: " + result.isFirstTimeLogin());
					JFrame old = root;
					root = new ResetPasswordPage(result, false, result.isFirstTimeLogin());
					StringUtils.frameInit(root, bundleMessage);
					// empPage.setJMenuBar(StringUtils.addMenu());
					root.setTitle(result.getUsername() + " - " + result.getFirstName() + " " + result.getLastName());
					root.show();
					old.dispose();
				} else {

					List<Role> listRoles = ctlObj.getUserRoles(userText, companyCode);
					logger.info("listRoles: " + listRoles);

					if (listRoles.size() == 0) {
						listRoles = ctlObj.getUserRoles(userText, companyCodeUH);
						logger.info("listRolesUH: " + listRoles);
					}

					if (listRoles.size() == 0) {
						JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Have_Not_Role"));
						logger.info("User does not have role");
					} else if (listRoles.size() == 1 && Enum.EMP.text().equals(listRoles.get(0).getRoleName())) {
						logger.info("Show EmployeePage");
						JFrame old = root;
						root = new EmployeePage(userName, false);
						StringUtils.frameInit(root, bundleMessage);
						// empPage.setJMenuBar(StringUtils.addMenu());
						root.setTitle(userText + " - " + result.getFirstName() + " " + result.getLastName());
						root.show();
						old.dispose();
					} else {
						logger.info("Show DashboardPage");
						JFrame old = root;
						root = new DashboardPage(listRoles, result);
						old.dispose();
						StringUtils.frameInit(root, bundleMessage);
						root.setTitle(userText + " - " + result.getFirstName() + " " + result.getLastName());
						// dashboardPage.setJMenuBar(StringUtils.addMenu());
						root.show();

					}
				}
			} else {
				logger.info("Login Fail");
				JOptionPane.showMessageDialog(this, bundleMessage.getString("Login_Page_Login_Fail"));
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOGIN_FAIL, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());

			}

		}
		if (e.getSource() == forgotPwdButton) {
			userTextField.setText("");
			passwordField.setText("");
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.FORGOT_PASS, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			logger.info("Forgot Pass");
			JFrame old = root;
			root = new ForgotPasswordPage();
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle("Forgot Password");
			root.show();
			old.dispose();

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

		printConfigInfo();

		Thread one = new Thread() {
			public void run() {

				while (true) {
					RandomString generate = new RandomString(8, ThreadLocalRandom.current());
					String passwordTmp = generate.nextString();
					logger.info("========================================");
					logger.info("Start syncing thread - " + passwordTmp);
					logger.info("========================================");
					syncCtl.syncDataAutomatically(companyCode, machineCode);
					
					logger.info("========================================");
					logger.info("End syncing thread - " + passwordTmp);
					logger.info("========================================");
					try {
						Thread.sleep(timeInterval * 1000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}

			}
		};

		one.start();

		root = new LoginPage();
		StringUtils.frameInit(root, bundleMessage);

		root.setTitle(bundleMessage.getString("Login_Page_Title"));
		root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);

	}

	private static void printConfigInfo() {
		logger.info("************************************************************");
		logger.info("************************************************************");
		logger.info("************************************************************");
		logger.info("************************************************************");
		logger.info("Starting iTool app.");
		logger.info("companyCode: " + companyCode);
		logger.info("companyCodeUH: " + companyCodeUH);
		logger.info("machineCode: " + machineCode);

		List<String> databaseInfo = ctlObj.getDatabaseVersion();
		logger.info("mDbHost: " + databaseInfo.get(0));
		logger.info("mDbUser: " + databaseInfo.get(1));
		logger.info("mDbPwds: " + databaseInfo.get(2));
		logger.info("mDbName: " + databaseInfo.get(3));
		logger.info("mDbPort: " + databaseInfo.get(4));
		logger.info("Database version: " + databaseInfo.get(5));

		logger.info("Last Sync: " + databaseInfo.get(6));

		boolean checkEmailConfig = emailUtils.checkEmailConnection();
		logger.info("checkEmailConfig: " + checkEmailConfig);

		File versionFile = new File("");
		try {
			versionFile = new File(LoginPage.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
					+ "/com/message/version");

			if (versionFile.isFile()) {
				BufferedReader cfgBuffer = new BufferedReader(new FileReader(versionFile));
				String text = cfgBuffer.readLine();
				logger.info("App Version: " + text);
				cfgBuffer.close();
			}
		} catch (Exception e1) {
			logger.info("Verison err: " + e1.getMessage());
		}

	}

}
