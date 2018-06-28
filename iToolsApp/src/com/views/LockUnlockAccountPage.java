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
import java.text.MessageFormat;
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

import com.controllers.LogController;
import com.controllers.UserController;
import com.message.Enum;
import com.models.Assessor;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;
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

	LogController masterLogObj = new LogController();
	JFrame parent;

	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));
	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;
	String userName = "";

	LockUnlockAccountPage(Assessor user, boolean isDashboard) {
		this.user = user;
		this.isDashboard = isDashboard;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		this.userName = user.getUsername();
		masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOCK_UNLOCK_PAGE, "", "", companyCode, machineCode,
				StringUtils.getCurrentClassAndMethodNames());
	}

	public void setLayoutManager() {
		container.setLayout(null);

	}

	public void setLocationAndSize() {

		Font labelFont = container.getFont();

		backToDashboardLabel.setText("<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("Employee_Back_To_Dashboard") + "</u></i></b></font></html></html>");
		backToDashboardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		backToDashboardLabel.setBounds(10, 0, 170, 60);
		backToDashboardLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("backToDashboardLabel");
				updateTimer.restart();
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
		changePassLabel.setBounds(440, 0, 170, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("changePassLabel");
				updateTimer.restart();
			}
		});

		splitLabel.setBounds(605, 0, 15, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 16));

		logOutLabel.setText("<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(620, 0, 100, 60);
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

		userNameComboBox.setBounds(250, 140, 300, 30);
		userNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateAllFields();
			}
		});
		userNameComboBox.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				userNameComboBox.showPopup();
			}
		});

		updateDisplayName();

		userNameComboBox.addItem("");
		for (Assessor user : listUsers) {
			String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
			mapDisplayName.put(displayName, user);
			if (!user.getUsername().equals(this.user.getUsername())) {
				userNameComboBox.addItem(displayName);
			}

		}
		AutoCompletion.enable(userNameComboBox);

		usernameLabel.setBounds(100, 130, 150, 60);
		usernameLabel.setFont(new Font(labelFont.getName(), Font.ITALIC + Font.BOLD, 20));

		lockAccountButtom.setBounds(100, 200, 200, 40);
		lockAccountButtom.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		unLockAccountButton.setBounds(350, 200, 200, 40);
		unLockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
		validateAllFields();

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog(userName, Enum.LOCK_UNLOCK_PAGE, "", Enum.TIME_OUT, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Lock Unlock",
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
			System.err.println("validateAllFields of lockunlockpage");
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

	public void addActionEvent() {
		lockAccountButtom.addActionListener(this);
		unLockAccountButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		String displayName = userNameComboBox.getSelectedItem().toString();
		String userNameLock = mapDisplayName.get(displayName).getUsername();
		String oldIsActiveValue = mapDisplayName.get(displayName).isLocked();
		if (e.getSource() == lockAccountButtom) {
			empCtlObj.updateIsLocked(userNameLock, companyCode, 0);
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "IsLocked", Enum.UPDATE,
					userNameLock + " - " + oldIsActiveValue, userNameLock + " - 0", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			JOptionPane.showMessageDialog(container, "Completed Lock Account!", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
			updateDisplayName();
		}

		if (e.getSource() == unLockAccountButton) {
			empCtlObj.updateIsLocked(userNameLock, companyCode, 1);
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "IsLocked", Enum.UPDATE,
					userNameLock + " - " + oldIsActiveValue, userNameLock + " - 1", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			JOptionPane.showMessageDialog(container, "Completed UnLock Account!", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
			updateDisplayName();
		}

	}

}
