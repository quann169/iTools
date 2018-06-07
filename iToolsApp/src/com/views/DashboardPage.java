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
import com.models.Role;
import com.utils.Config;

public class DashboardPage extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ResourceBundle bundleMessage = ResourceBundle.getBundle("com.message.ApplicationMessages",
			new Locale("vn", "VN"));
	Container container = getContentPane();
	List<Role> listRoles;

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

	DashboardPage(List<Role> listRoles) {
		this.listRoles = listRoles;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();

	}

	public void setLayoutManager() {
//		container.setLayout(new GridLayout(0, 2));
		
		JButton button;
		container.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		button = new JButton("Button 1");
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		container.add(button, c);

		button = new JButton("Button 2");
		c.gridx = 1;
		c.gridy = 0;
		container.add(button, c);

		button = new JButton("Button 3");
		c.gridx = 2;
		c.gridy = 0;
		container.add(button, c);

		button = new JButton("Long-Named Button 4");
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		container.add(button, c);

		button = new JButton("5");
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		container.add(button, c);
		
	}

	public void setLocationAndSize() {

		Font labelFont = container.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		changePassLabel.setText(
				"<html><html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>Change Password</u></i></b></font></html></html>");
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

		logOutLabel.setText(
				"<html><font size=\"5\" face=\"arial\" color=\"#0181BE\"><b><i><u>Logout</u></i></b></font></html>");
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
//		unlockMachineButton.setSize(new Dimension(40, 30));
//		unlockMachineButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
//
//		takeOverButton.setSize(new Dimension(40, 30));
//		takeOverButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
//
//		putInsButton.setSize(new Dimension(40, 30));
//		putInsButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

		// sub admin role
//		putInsButton.setSize(new Dimension(40, 30));
//		putInsButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
//
//		resetPasswordButton.setSize(new Dimension(40, 30));
//		resetPasswordButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
//
//		lockAccountButton.setSize(new Dimension(40, 30));
//		lockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));
//
//		manualSyncButton.setSize(new Dimension(40, 30));
//		manualSyncButton.setFont(new Font(labelFont.getName(), Font.BOLD, 15));

	}

	public void addComponentsToContainer() {
//		List<String> listRoleName = new ArrayList<>();
//		for (Role role : this.listRoles) {
//			listRoleName.add(role.getRoleName());
//		}
//		if (listRoleName.contains(Enum.TKOVER) || listRoleName.contains(Enum.PUTIN)) {
//			container.add(unlockMachineButton);
//			container.add(takeOverButton);
//			container.add(putInsButton);
//			if (listRoleName.contains(Enum.TKOVER)) {
//
//			}
//			if (listRoleName.contains(Enum.PUTIN)) {
//
//			}
//		} else {
//			container.add(getToolButton);
//			container.add(resetPasswordButton);
//			container.add(lockAccountButton);
//			container.add(manualSyncButton);
//		}

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

		}
	}

}
