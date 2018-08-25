package com.views;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
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
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
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

	static String productMode = cfg.getProperty("PRODUCT_MODE");

	static long timeInterval = Long.valueOf(cfg.getProperty("INTERVAL_SYNC"));
	static String userName = "";
	static SyncController syncCtl = new SyncController();
	static EmailUtils emailUtils = new EmailUtils(Enum.LOGIN, userName, companyCode, machineCode);
	final static Logger logger = Logger.getLogger(LoginPage.class);
	public PopUpKeyboard keyboard;
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

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		userTextField.addFocusListener(focus1);
		passwordField.addFocusListener(focus1);
	}

	public void setLocationAndSize() {
		Font labelFont = logoLabel.getFont();

		logoLabel.setBounds(300, 40, 350, 70);
		logoLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 40));

		userLabel.setBounds(80, 120, 250, 60);
		userLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 30));

		userTextField.setBounds(300, 130, 400, 40);
		userTextField.setFont(new Font(labelFont.getName(), Font.PLAIN, 25));
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
				} else {
					loginButton.setEnabled(false);
				}
			}
		});

		userTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (userTextField.getText().length() >= 20)
					e.consume();
			}
		});

		passwordLabel.setBounds(80, 200, 250, 60);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 30));

		passwordField.setBounds(300, 210, 400, 40);
		passwordField.setFont(new Font(labelFont.getName(), Font.PLAIN, 25));
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
				} else {
					loginButton.setEnabled(false);
				}
			}
		});

		passwordField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (passwordField.getPassword().length >= 8)
					e.consume();
			}
		});

		showPassword.setBounds(295, 260, 350, 40);
		showPassword.setFont(new Font(labelFont.getName(), Font.BOLD + Font.ITALIC, 20));

		loginButton.setEnabled(false);
		loginButton.setBounds(300, 310, 140, 40);
		loginButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		forgotPwdButton.setBounds(460, 310, 240, 40);
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

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		String userText = userTextField.getText();
		String pwdText = passwordField.getText();

		userName = userTextField.getText();
		if (e.getSource() == loginButton) {

			if (productMode.equals("Dev")) {
				userText = "com1user1";
				// userText = "com1admin";
				// userText = "uhacc";
				pwdText = "123456";
			}

			logger.info("Login with username: " + userText);

			Assessor result = ctlObj.validateUser(userText, pwdText);
			if (result != null) {
				logger.info("Login OK");
				if (result.isLocked()) {
					logger.info("User blocked");
					JOptionPane.showMessageDialog(this, "<html><font size=\"5\" face=\"arial\">"
							+ bundleMessage.getString("Login_Page_Login_Acc_Locked") + "</font></html>");
				} else if (result.isFirstTimeLogin()) {
					logger.info("isFirstTimeLogin: " + result.isFirstTimeLogin());
					JFrame old = root;
					root = new ResetPasswordPage(result, false, result.isFirstTimeLogin());
					StringUtils.frameInit(root, bundleMessage);
					// empPage.setJMenuBar(StringUtils.addMenu());
					root.setTitle(result.getFirstName() + " " + result.getLastName());
					root.show();
					old.dispose();
				} else {
					List<Role> listRoles = ctlObj.getUserRoles(userText, companyCode, companyCodeUH);
					logger.info("listRoles: " + listRoles);

					// if (listRoles.size() == 0) {
					// listRoles = ctlObj.getUserRoles(userText, companyCodeUH);
					// logger.info("listRolesUH: " + listRoles);
					// }

					if (listRoles.size() == 0) {
						JOptionPane.showMessageDialog(this, "<html><font size=\"5\" face=\"arial\">"
								+ bundleMessage.getString("Login_Page_Have_Not_Role") + "</font></html>");
						logger.info("User does not have role");
					} else if (listRoles.size() == 1 && Enum.EMP.text().equals(listRoles.get(0).getRoleName())) {
						logger.info("Show EmployeePage");
						JFrame old = root;
						root = new EmployeePage(result, false);
						StringUtils.frameInit(root, bundleMessage);
						// empPage.setJMenuBar(StringUtils.addMenu());
						root.setTitle(result.getFirstName() + " " + result.getLastName() + " - GetTool Page");
						root.show();
						old.dispose();
					} else {
						logger.info("Show DashboardPage");
						JFrame old = root;
						root = new DashboardPage(listRoles, result);
						old.dispose();
						StringUtils.frameInit(root, bundleMessage);
						root.setTitle(result.getFirstName() + " " + result.getLastName() + " - Dashboard Page");
						// dashboardPage.setJMenuBar(StringUtils.addMenu());
						root.show();

					}
				}
			} else {
				logger.info("Login Fail");

				logger.info("Update Fail Time");
				ctlObj.updateFailTimes(companyCode, userText);

				boolean lockUsers = ctlObj.lockUser(companyCode, userText);
				if (lockUsers) {
					String email = ctlObj.getEmailUser(companyCode, userText);
					logger.info("Lock User By Fail 3 times");
					Thread one = new Thread() {
						public void run() {
							List<String> listCCEmail = new ArrayList<>();
							listCCEmail.add(ctlObj.getEmailAdmin());
							emailUtils.sendEmail(email, listCCEmail, "Login_Fail_3_Times",
									"Login fail 3 times. Application will lock your account. Please contact your admin to unlock.");

						}
					};

					one.start();
					JOptionPane.showMessageDialog(this, "<html><font size=\"5\" face=\"arial\">"
							+ bundleMessage.getString("Login_Page_Login_Fail_3_Times") + "</font></html>");

				} else {
					JOptionPane.showMessageDialog(this, "<html><font size=\"5\" face=\"arial\">"
							+ bundleMessage.getString("Login_Page_Login_Fail") + "</font></html>");
				}

			}

		}
		if (e.getSource() == forgotPwdButton) {
			userTextField.setText("");
			passwordField.setText("");
			logger.info("Forgot Pass Button Click");
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

		Thread two = new Thread() {
			public void run() {

				try {
					while (true) {

						File file = new File("./log/logging.log");
						if (file.exists() && file.canRead()) {
							// long fileLength = file.length();
							readFile(file, 0L);
							// while (true) {
							//
							// if (fileLength < file.length()) {
							// readFile(file, fileLength);
							// fileLength = file.length();
							// }
							// }
						}

						try {
							Thread.sleep(12 * 60 * 60 * 1000);

							// Thread.sleep(60 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							logger.error(e.getMessage());
						}
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error(e2.getMessage());
				}

			}
		};
		if (!productMode.equals("Dev")) {
			one.start();
			two.start();
		}
		root = new LoginPage();
		StringUtils.frameInit(root, bundleMessage);
		root.setTitle(bundleMessage.getString("Login_Page_Title"));
		root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);

	}

	/**
	 * 
	 * @param file
	 * @param fileLength
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File file, Long fileLength) throws IOException {
		LogController logCtl = new LogController();
		String latestTime = logCtl.getLatestEmailLog();

		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();
		String dateTime = dateFormat.format(date);
		String outFilePath = "./log/logging_" + dateTime + ".log";
		String line = "";
		BufferedReader in = new BufferedReader(new java.io.FileReader(file));
		in.skip(fileLength);

		String dataLog = "";
		int countLine = 0;
		String lastLine = "";
		while ((line = in.readLine()) != null) {
			if (line.compareTo(latestTime) > 0) {
				line = line.trim();
				dataLog += line + "\n";
				countLine++;
				lastLine = line;
				if (countLine % 1000 == 0) {
					logger.info("Get " + countLine + " from logs");
				}
			}

		}
		in.close();

		BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath));
		writer.write(dataLog);

		writer.close();

		emailUtils.sendEmailWithAttachedFile("quann169@gmail.com",
				companyCode + " - " + machineCode + ": ITools App Log", outFilePath);
		// emailUtils.sendEmailWithAttachedFile("ngngoctanthuong@gmail.com",
		// companyCode + " - " + machineCode + ": ITools App Log", outFilePath);
		String[] dataLastLine = lastLine.split(" ");
		String strDate = dataLastLine[0] + " " + dataLastLine[1];
		SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdfrmt.setLenient(false);
		try {
			sdfrmt.parse(strDate);
			boolean addLog = logCtl.updateLatestEmailLog(strDate);
			if (addLog) {
				logger.info("Update " + strDate + " to MasterLog successfully.");
			}

		} catch (ParseException e) {
			logger.error(strDate + ": erorr date format");
		}

		try {
			Files.deleteIfExists(Paths.get(outFilePath));
			logger.info("Delete log file successfully.");
		} catch (Exception e) {
			logger.error(strDate + ": Cannot delete log file");
		}

		return outFilePath;
	}

	/**
	 * 
	 */
	private static void printConfigInfo() {
		final JDialog d = new JDialog();
		JPanel p1 = new JPanel(new GridBagLayout());
		JLabel progress = new JLabel("Checking configuration...");
		p1.add(progress, new GridBagConstraints());
		d.getContentPane().add(p1);
		d.setBounds(100, 100, 500, 200);
		// d.setLocationRelativeTo(f);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setModal(true);

		SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
			protected Void doInBackground() throws InterruptedException {

				publish("Checking database configuration...");
				Thread.sleep(1000);
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
				Thread.sleep(1000);
				File versionFile = new File("");
				try {
					versionFile = new File(
							LoginPage.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()
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

				publish("Checking email...");
				Thread.sleep(1000);

				 boolean checkEmailConfig = emailUtils.checkEmailConnection();
				 logger.info("checkEmailConfig: " + checkEmailConfig);

				publish("Done checking...");
				Thread.sleep(1000);

				return null;
			}

			protected void process(List<String> chunks) {
				String selection = chunks.get(chunks.size() - 1);
				progress.setText(selection);
			}

			protected void done() {
				d.dispose();

			}
		};
		worker.execute();
		d.setVisible(true);
	}

}
