package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.Config;
import com.utils.StringUtils;

public class DashboardPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	List<Role> listRoles;
	Assessor user;

	JLabel logOutLabel = new JLabel(bundleMessage.getString("App_Logout"));
	JLabel changePassLabel = new JLabel(bundleMessage.getString("App_ChangePassword"));
	JLabel splitLabel = new JLabel(" | ");

	JButton getToolButton = new JButton(bundleMessage.getString("Dashboard_Page_Get_Tool"));
	JButton resetPasswordButton = new JButton(bundleMessage.getString("Dashboard_Page_Reset_Password"));
	JButton lockAccountButton = new JButton(bundleMessage.getString("Dashboard_Page_Lock_Account"));
	JButton manualSyncButton = new JButton(bundleMessage.getString("Dashboard_Page_Manual_Sync"));

	JButton unlockMachineButton = new JButton(bundleMessage.getString("Dashboard_Page_Unlock_Machine"));
	JButton putInsButton = new JButton(bundleMessage.getString("Dashboard_Page_Putins"));
	JButton takeOverButton = new JButton(bundleMessage.getString("Dashboard_Page_Take_Over"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	final static Logger logger = Logger.getLogger(DashboardPage.class);

	DashboardPage(List<Role> listRoles, Assessor user) {
		this.listRoles = listRoles;
		this.user = user;
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

		// Map<TextAttribute, Integer> fontAttributes = new
		// HashMap<TextAttribute, Integer>();
		// fontAttributes.put(TextAttribute.UNDERLINE,
		// TextAttribute.UNDERLINE_ON);

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

		// accounting role
		unlockMachineButton.setBounds(50, 120, 250, 40);
		unlockMachineButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		takeOverButton.setBounds(400, 120, 250, 40);
		takeOverButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		putInsButton.setBounds(50, 250, 250, 40);
		putInsButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		// sub admin role
		resetPasswordButton.setBounds(50, 120, 250, 40);
		resetPasswordButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		lockAccountButton.setBounds(400, 120, 250, 40);
		lockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		manualSyncButton.setBounds(50, 250, 250, 40);
		manualSyncButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		getToolButton.setBounds(400, 250, 250, 40);
		getToolButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

	}

	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);

		List<String> listRoleName = new ArrayList<>();
		for (Role role : this.listRoles) {
			listRoleName.add(role.getRoleName());
		}
		if (listRoleName.contains(Enum.ACCT) || listRoleName.contains(Enum.TKOVER)
				|| listRoleName.contains(Enum.PUTIN)) {
			container.add(unlockMachineButton);
			container.add(takeOverButton);
			container.add(putInsButton);
			takeOverButton.setEnabled(false);
			putInsButton.setEnabled(false);
		} else {
			container.add(getToolButton);
			container.add(resetPasswordButton);
			container.add(lockAccountButton);
			container.add(manualSyncButton);
		}

	}

	public void addActionEvent() {
		unlockMachineButton.addActionListener(this);
		takeOverButton.addActionListener(this);
		putInsButton.addActionListener(this);
		getToolButton.addActionListener(this);
		resetPasswordButton.addActionListener(this);
		lockAccountButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == unlockMachineButton) {

		}
		if (e.getSource() == takeOverButton) {

		}
		if (e.getSource() == putInsButton) {

		}
		if (e.getSource() == getToolButton) {

		}
		if (e.getSource() == resetPasswordButton) {

		}
		if (e.getSource() == lockAccountButton) {
			LockUnlockAccountPage lockUnlockPage = new LockUnlockAccountPage(user, true);
			StringUtils.frameInit(lockUnlockPage, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			lockUnlockPage.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			lockUnlockPage.show();
		}

		if (e.getSource() == getToolButton) {
			EmployeePage empPage = new EmployeePage(true);
			StringUtils.frameInit(empPage, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			empPage.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			empPage.show();
		}
	}

}
