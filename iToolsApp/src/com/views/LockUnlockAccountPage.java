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

import org.apache.log4j.Logger;

import com.controllers.UserController;
import com.models.Assessor;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;

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

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private String companyCode;
	final static Logger logger = Logger.getLogger(LockUnlockAccountPage.class);

	LockUnlockAccountPage(Assessor user, boolean isDashboard) {
		this.user = user;
		this.isDashboard = isDashboard;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

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
				System.out.println("logOutLabel");
				((EmployeePage) e.getComponent().getParent().getParent().getParent().getParent()).dispose();
			}
		});

		userNameComboBox.setBounds(250, 140, 300, 30);
		userNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectValue = userNameComboBox.getSelectedItem().toString();
				if (!selectValue.equals("")) {
					lockAccountButtom.setEnabled(true);
					unLockAccountButton.setEnabled(true);
				}
			}
		});
		userNameComboBox.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				userNameComboBox.showPopup();
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
		String displayName = userNameComboBox.getSelectedItem().toString();
		String userName = mapDisplayName.get(displayName).getUsername();
		if (e.getSource() == lockAccountButtom) {
			empCtlObj.updateIsActive(userName, this.companyCode, 0);
			JOptionPane.showMessageDialog(container, "Completed Lock Account!", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
		}

		if (e.getSource() == unLockAccountButton) {
			empCtlObj.updateIsActive(userName, this.companyCode, 1);
			JOptionPane.showMessageDialog(container, "Completed UnLock Account!", "Notify result",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

}