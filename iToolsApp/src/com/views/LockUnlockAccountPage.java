package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.controllers.LoginController;
import com.controllers.UserController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
import com.utils.Config;
import com.utils.FilterComboBox;
import com.utils.MyFocusListener;
import com.utils.PopUpKeyboard;
import com.utils.StringUtils;

public class LockUnlockAccountPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	Assessor user;

	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));
	JLabel changePassLabel = new JLabel(bundleMessage.getString("App_ChangePassword"));
	JLabel splitLabel = new JLabel(" | ");
	JLabel backToDashboardLabel = new JLabel(bundleMessage.getString("Employee_Back_To_Dashboard"));

	JButton lockAccountButtom = new JButton(bundleMessage.getString("LockUnlockAccount_Page_Lock_Account"));
	JButton unLockAccountButton = new JButton(bundleMessage.getString("LockUnlockAccount_Page_UnLock_Account"));

	JLabel usernameLabel = new JLabel(bundleMessage.getString("LockUnlockAccount_Page_Username"));
	JComboBox<String> userNameComboBox = new JComboBox<String>();
	Map<String, Assessor> mapDisplayName = new HashMap<>();

	UserController empCtlObj = new UserController();
	boolean isDashboard;
	List<Assessor> listUsers = empCtlObj.getUsersOfCompany(companyCode);

	private static final Config cfg = new Config();
	final static Logger logger = Logger.getLogger(LockUnlockAccountPage.class);

	static LoginController ctlObj = new LoginController();
	JFrame parent;

	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));
	private static final String companyCodeUH = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE_UH"));
	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;
	String userName = "";
	public PopUpKeyboard keyboard;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int windowWidth = (int) screenSize.getWidth();
	static int windowHeight = (int) screenSize.getHeight();
	static int extWidth = (windowWidth > 900) ? 0 : 0;
	static int extHeight = (windowHeight > 700) ? 0 : 0;


	LockUnlockAccountPage(Assessor user, boolean isDashboard) {
		this.user = user;
		this.isDashboard = isDashboard;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		this.userName = user.getUsername();
		logger.info("Init " + Enum.LOCK_UNLOCK_PAGE);
	}

	public void setLayoutManager() {
		container.setLayout(null);

	}

	public void setLocationAndSize() {

		Font labelFont = container.getFont();

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(15 + extWidth, 5 + extHeight, 300, 70);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				updateTimer.restart();
				logger.info(userName + " back to dashboard from " + Enum.LOCK_UNLOCK_PAGE);
				JFrame old = root;
				
				List<Role> listRoles = ctlObj.getUserRoles(userName, companyCode, companyCodeUH);
				root = new DashboardPage(listRoles, user);
				StringUtils.frameInit(root, bundleMessage);

				root.setTitle(userName );
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
				
				logger.info(userName + " click change password from " + Enum.LOCK_UNLOCK_PAGE);
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
		
		usernameLabel.setBounds(100 + extWidth, 150 + extHeight, 150, 60);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		List<String> listAllUserNames = new ArrayList<>();
		listAllUserNames.add("");
		updateDisplayName();

		userNameComboBox.addItem("");
		for (Assessor user : listUsers) {
			String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
			mapDisplayName.put(displayName, user);
			if (!user.getUsername().equals(this.user.getUsername())) {
				userNameComboBox.addItem(displayName);
				listAllUserNames.add(displayName);
			}

		}
		
		userNameComboBox = new FilterComboBox(listAllUserNames, keyboard);
				
		userNameComboBox.setBounds(230 + extWidth, 160 + extHeight, 450, 30);
		userNameComboBox.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		
		userNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateAllFields();
			}
		});
//		userNameComboBox.addFocusListener(new FocusAdapter() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				userNameComboBox.showPopup();
//			}
//		});

		
//		new AutoCompletion(userNameComboBox, keyboard);

		

		lockAccountButtom.setBounds(230 + extWidth, 220 + extHeight, 220, 35);
		lockAccountButtom.setFont(new Font(labelFont.getName(), Font.BOLD, 20));

		unLockAccountButton.setBounds(460 + extWidth, 220 + extHeight, 220, 35);
		unLockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
		validateAllFields();

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + timeoutMess + "</font></html>", "Time Out Lock Unlock",
						JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.LOCK_UNLOCK_PAGE + " time out.");
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

	/**
	 * 
	 * @return
	 */
	private boolean validateAllFields() {
		try {
			updateTimer.restart();
		} catch (Exception e) {
			logger.error("validateAllFields of lockunlockpage: " + e.getMessage());
		}
		String selectedValue = userNameComboBox.getSelectedItem().toString();

		if ("".equals(selectedValue)) {
			lockAccountButtom.setEnabled(false);
			unLockAccountButton.setEnabled(false);
			return false;
		} else {
			lockAccountButtom.setEnabled(true);
			unLockAccountButton.setEnabled(true);
			return true;
		}
	}

	/**
	 * 
	 */
	private void updateDisplayName() {
		mapDisplayName = new HashMap<>();

		Collections.sort(listUsers, new Comparator<Assessor>() {
			public int compare(Assessor o1, Assessor o2) {
				String fullName1 = o1.getFirstName() + " " + o1.getLastName();
				String fullName2 = o2.getFirstName() + " " + o2.getLastName();
				return fullName1.compareToIgnoreCase(fullName2);
			}
		});
		for (Assessor user : listUsers) {
			String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
			mapDisplayName.put(displayName, user);

		}

	}

	/**
	 * 
	 */
	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);
		container.add(backToDashboardLabel);

		container.add(lockAccountButtom);
		container.add(unLockAccountButton);
		container.add(usernameLabel);
		container.add(userNameComboBox);
	}

	/**
	 * 
	 */
	public void addActionEvent() {
		lockAccountButtom.addActionListener(this);
		unLockAccountButton.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };
				int PromptResult = JOptionPane.showOptionDialog(root, "<html><font size=\"5\" face=\"arial\">Are you sure you want to exit?</font></html>",
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
		String displayName = userNameComboBox.getSelectedItem().toString();
		String userNameLock = mapDisplayName.get(displayName).getUsername();
		boolean oldIsActiveValue = mapDisplayName.get(displayName).isLocked();
		if (e.getSource() == lockAccountButtom) {
			empCtlObj.updateIsLocked(userNameLock, companyCode, 1);
			logger.info(userNameLock + " - locked");
			JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + "Completed Lock Account!" + "</font></html>", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
			updateDisplayName();
		}

		if (e.getSource() == unLockAccountButton) {
			empCtlObj.updateIsLocked(userNameLock, companyCode,0);
			logger.info(userNameLock + " - unlocked");
			JOptionPane.showMessageDialog(container, "<html><font size=\"5\" face=\"arial\">" + "Completed UnLock Account!" + "</font></html>", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
			updateDisplayName();
		}

	}

	public void addVirtualKeyboardListener() {
		MyFocusListener focus1 = new MyFocusListener(keyboard);
		userNameComboBox.getEditor().getEditorComponent().addFocusListener(focus1);
	}


}
