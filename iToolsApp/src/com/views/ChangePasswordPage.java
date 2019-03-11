package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
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
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.EmailUtils;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class ChangePasswordPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));


	JLabel usernameLabel = new JLabel(bundleMessage.getString("ChangePassword_Page_Username"));
	JLabel oldPasswordLabel = new JLabel(bundleMessage.getString("ChangePassword_Page_Old_Password"));
	JLabel passwordLabel = new JLabel(bundleMessage.getString("ChangePassword_Page_Password"));
	JLabel rePasswordLabel = new JLabel(bundleMessage.getString("ChangePassword_Page_RePassword"));


	JTextField usernameTextField = new JTextField();
	JPasswordField oldPasswordTextField = new JPasswordField();
	JPasswordField passwordTextField = new JPasswordField();
	JPasswordField rePasswordTextField = new JPasswordField();

	JButton changePassButton = new JButton();

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	static String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	static String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));
	static String userName = "";
	final static Logger logger = Logger.getLogger(ChangePasswordPage.class);
	UserController empCtlObj = new UserController();
	static EmailUtils emailUtils = new EmailUtils(Enum.LOGIN, userName, companyCode, machineCode);
	static LoginController ctlObj = new LoginController();

	Assessor user;
	int resultValue;
	public PopUpKeyboard keyboard;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	ChangePasswordPage(Assessor user) {
		this.user = user;
		this.userName = user.getUsername();
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		logger.info("Init ChangePasswordPage of " + user.getUsername());
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = usernameLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		logOutLabel.setText("<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(715, 5, 150, 60);
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

		usernameLabel.setBounds(70, 80, 250, 40);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();

		usernameTextField.setText(displayName);
		usernameTextField.setBounds(260, 85, 450, 30);
		usernameTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
		usernameTextField.setEditable(false);

		oldPasswordLabel.setBounds(70, 135, 350, 40);
		oldPasswordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		oldPasswordTextField.setBounds(260, 140, 450, 30);
		oldPasswordTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		oldPasswordTextField.setText("");

		oldPasswordTextField.getDocument().addDocumentListener(new DocumentListener() {
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

		passwordLabel.setBounds(70, 185, 350, 40);
		passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		passwordTextField.setBounds(260, 190, 450, 30);
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


		rePasswordLabel.setBounds(70, 235, 350, 40);
		rePasswordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		rePasswordTextField.setBounds(260, 240, 450, 30);
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

		changePassButton.setText(bundleMessage.getString("ChangePassword_Page_ChangePassword"));
		changePassButton.setBounds(260, 290, 300, 35);
		changePassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 22));

		changePassButton.setEnabled(false);

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>" , "Time Out Reset Pass",
						JOptionPane.WARNING_MESSAGE);

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
			System.err.println("validateAllFields of resetpasspage");
		}

		String password = passwordTextField.getText();
		String repassword = rePasswordTextField.getText();
		String oldPassword = rePasswordTextField.getText();

		if (!"".equals(password) && password.equals(repassword) && !"".equals(oldPassword)) {
			changePassButton.setEnabled(true);
			return true;
		} else {
			changePassButton.setEnabled(false);
			return false;
		}
	}

	public void addComponentsToContainer() {

		container.add(logOutLabel);

		container.add(usernameLabel);
		container.add(usernameTextField);

		container.add(passwordLabel);
		container.add(passwordTextField);
		container.add(rePasswordTextField);
		container.add(rePasswordLabel);

		container.add(oldPasswordTextField);
		container.add(oldPasswordLabel);

		container.add(changePassButton);

	}

	public void addActionEvent() {
		changePassButton.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root, "<html><font size=\"5\" face=\"arial\">Are you sure you want to exit?</font></html>", "Confirm Close",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == changePassButton) {

			JPanel panel = new JPanel();

			panel.setLayout(null);

			String password = rePasswordTextField.getText();
			String oldPassword = oldPasswordTextField.getText();
			int dialogResult;
			String confirm = "<html><font size=\"4\" face=\"arial\"><i>"
					+ bundleMessage.getString("ChangePassword_Page_Reset_Confirmation") + "?</i></font></html>";
			dialogResult = JOptionPane.showConfirmDialog(container, confirm, "Confirmation", JOptionPane.YES_NO_OPTION);
			if (dialogResult == 0) {
				logger.info("Yes option");

				Assessor validateOldPass = ctlObj.validateUser(userName, oldPassword, machineCode);

				if (validateOldPass == null) {
					JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + "Old password does not match." + "</font></html>" , "Check old password",
							JOptionPane.WARNING_MESSAGE);
				} else {

					String email = ctlObj.getEmailUser(companyCode, userName);
					logger.info("Lock User");

					empCtlObj.updatePassword(userName, companyCode, password, 0);
					logger.info("Change password ok - " + userName + " - " + password);
					
					Thread one = new Thread() {
						public void run() {
							List<String> listCCEmail = new ArrayList<>();
							listCCEmail.add(ctlObj.getEmailAdmin());
							emailUtils.sendEmail(email, listCCEmail, "Change Pass Successfully",
									"Hi " + userName + ",\nYour pass has been change to " + password);

						}
					};
					one.start();
					
					confirm = "<html><font size=\"4\" face=\"arial\"><i>Completed change password" + " " + userName
							+ "</i></font></html>";
					
					JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + confirm + "</font></html>", "Notify result", JOptionPane.INFORMATION_MESSAGE);
					
					
					logger.info(userName + " logout.");
					JFrame old = root;
					root = new LoginPage();
					StringUtils.frameInit(root, bundleMessage);

					root.setTitle(bundleMessage.getString("Login_Page_Title"));
					root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
					old.dispose();
				}
			} else {
				logger.info("No Option");
			}

		}
	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		passwordTextField.addFocusListener(focus1);
		rePasswordTextField.addFocusListener(focus1);
		oldPasswordTextField.addFocusListener(focus1);
	}

}
