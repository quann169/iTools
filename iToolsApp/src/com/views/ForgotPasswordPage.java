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
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.LogController;
import com.controllers.UserController;
import com.message.Enum;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class ForgotPasswordPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();

	JLabel usernameLabel = new JLabel(bundleMessage.getString("Forgot_Password_Page_Username"));
	JTextField usernameTextField = new JTextField();

	JLabel emailLabel = new JLabel(bundleMessage.getString("Forgot_Password_Page_Email"));
	JTextField emailTextField = new JTextField();

	JButton forgotPassButton = new JButton(bundleMessage.getString("Forgot_Password_Page_Reset"));
	JButton cancelButton = new JButton(bundleMessage.getString("Forgot_Password_Page_Cancel"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	final static Logger logger = Logger.getLogger(ForgotPasswordPage.class);
	UserController empCtlObj = new UserController();

	LogController masterLogObj = new LogController();
	public PopUpKeyboard keyboard;

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	ForgotPasswordPage() {
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		masterLogObj.insertLog("", Enum.ASSESSOR, "", Enum.FORGOT_PASS_PAGE, "", "", companyCode, machineCode,
				StringUtils.getCurrentClassAndMethodNames());
	}

	public void setLayoutManager() {
		container.setLayout(null);
	}

	public void setLocationAndSize() {
		Font labelFont = usernameLabel.getFont();

		usernameLabel.setBounds(120, 100, 250, 40);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		usernameTextField.setBounds(260, 105, 430, 40);
		usernameTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		usernameTextField.setText("");

		usernameTextField.getDocument().addDocumentListener(new DocumentListener() {
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
		
		usernameTextField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) { 
		        if (usernameTextField.getText().length() >= 20 )
		            e.consume(); 
		    }  
		});

		emailLabel.setBounds(120, 190, 250, 40);
		emailLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		emailTextField.setBounds(260, 195, 430, 40);
		emailTextField.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		emailTextField.setText("");

		emailTextField.getDocument().addDocumentListener(new DocumentListener() {
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

		forgotPassButton.setEnabled(false);
		forgotPassButton.setBounds(260, 280, 230, 50);
		forgotPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		cancelButton.setBounds(510, 280, 180, 50);
		cancelButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog("", Enum.FORGOT_PASS_PAGE, "", Enum.TIME_OUT, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Reset Pass",
						JOptionPane.WARNING_MESSAGE);

				logger.info("" + ": " + Enum.FORGOT_PASS_PAGE + " time out.");
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

		String username = usernameTextField.getText();
		String email = emailTextField.getText();
		if (email.length() > 0 && username.length() > 0) {
			forgotPassButton.setEnabled(true);
			return true;
		} else {
			forgotPassButton.setEnabled(false);
			return true;
		}
	}

	public void addComponentsToContainer() {

		container.add(usernameLabel);
		container.add(usernameTextField);
		container.add(emailLabel);
		container.add(emailTextField);
		container.add(cancelButton);
		container.add(forgotPassButton);

	}

	public void addActionEvent() {
		forgotPassButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root, "Are you sure you want to exit?",
						"Confirm Close", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
						ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == forgotPassButton) {
			String email = emailTextField.getText();
			String userName = usernameTextField.getText();
			try {
				InternetAddress emailAddr = new InternetAddress(email);
				emailAddr.validate();
			} catch (AddressException ex) {
				JOptionPane.showMessageDialog(container, "Wrong email format", "Wrong Validation",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			boolean validateUsernameEmail = empCtlObj.validateUsernameEmail(userName, email, companyCode, machineCode);

			if (validateUsernameEmail) {

				final JDialog d = new JDialog();
				JPanel p1 = new JPanel(new GridBagLayout());
				JLabel progress = new JLabel("Please Wait...");
				p1.add(progress, new GridBagConstraints());
				d.getContentPane().add(p1);
				d.setBounds(150, 200, 500, 200);
				// d.setLocationRelativeTo(f);
				d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
				d.setModal(true);

				SwingWorker<?, ?> worker = new SwingWorker<Void, String>() {
					protected Void doInBackground() throws InterruptedException {
						empCtlObj.resetPassword(userName, email, companyCode, machineCode);

						return null;
					}

					protected void process(List<String> chunks) {
						String selection = chunks.get(chunks.size() - 1);
						progress.setText(
								"<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b>Please Wait... "
										+ selection + "s</b></font></html></html>");

					}

					protected void done() {
						d.dispose();
						masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.FORGOT_PASS_RESET_OK, "",
								userName + " - " + email, companyCode, machineCode,
								StringUtils.getCurrentClassAndMethodNames());
						logger.info(userName + " - " + email + " forgot pass ok.");

						JOptionPane.showMessageDialog(container,
								MessageFormat.format(
										bundleMessage.getString("Forgot_Password_Page_Username_Email_Match"), email),
								"Notify Result", JOptionPane.INFORMATION_MESSAGE);

						JFrame old = root;
						root = new LoginPage();
						StringUtils.frameInit(root, bundleMessage);

						root.setTitle(bundleMessage.getString("Login_Page_Title"));
						root.getRootPane().setDefaultButton(((LoginPage) root).loginButton);
						old.dispose();
					}
				};
				worker.execute();
				d.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(container,
						bundleMessage.getString("Forgot_Password_Page_Username_Email_Not_Match"), "Wrong Validation",
						JOptionPane.WARNING_MESSAGE);
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.FORGOT_PASS_USERNAME_EMAIL_NOT_MATCH, "",
						userName + " - " + email, companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				logger.info(userName + " - " + email + " fail validate");
				return;
			}

		}
	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		emailTextField.addFocusListener(focus1);
		usernameTextField.addFocusListener(focus1);
	}

}
