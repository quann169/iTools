package com.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.controllers.EmployeeController;
import com.controllers.UserController;
import com.models.Assessor;
import com.models.Machine;
import com.models.Tool;
import com.utils.AdvancedEncryptionStandard;
import com.utils.AutoCompletion;
import com.utils.Config;

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

	JLabel matchPasswordLabel = new JLabel();

	JComboBox<String> usernameComboBox = new JComboBox<String>();
	JTextField passwordTextField = new JPasswordField();
	JTextField rePasswordTextField = new JPasswordField();

	JButton resetPassButton = new JButton(bundleMessage.getString("ResetPassword_Page_ResetPassword"));

	JCheckBox isFirstChange = new JCheckBox(bundleMessage.getString("ResetPassword_Page_FirstChange"));

	private static final Config cfg = new Config();
	private static final String COMPANY_CODE = "COMPANY_CODE";
	private static final String MACHINE_CODE = "MACHINE_CODE";
	String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(COMPANY_CODE));
	String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty(MACHINE_CODE));

	final static Logger logger = Logger.getLogger(ResetPasswordPage.class);
	UserController empCtlObj = new UserController();

	Assessor user;
	Map<String, Assessor> mapDisplayName = new HashMap<>();
	int resultValue;
	boolean isDashboard;

	ResetPasswordPage(Assessor user, boolean isDashboard) {
		this.isDashboard = isDashboard;
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
		Font labelFont = usernameLabel.getFont();

		Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
		fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

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
				((ResetPasswordPage) e.getComponent().getParent().getParent().getParent().getParent()).dispose();
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

		usernameComboBox.addItem("");
		for (Assessor user : listUsers) {
			String displayName = user.getFirstName() + " " + user.getLastName() + " - " + user.getUsername();
			mapDisplayName.put(displayName, user);
			System.out.println(user.getUsername());
			System.out.println(this.user.getUsername());
			System.out.println(user.getUsername() != this.user.getUsername());
			System.out.println("----");
			if (!user.getUsername().equals(this.user.getUsername())) {
				usernameComboBox.addItem(displayName);
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

		resetPassButton.setBounds(270, 310, 200, 30);
		resetPassButton.setFont(new Font(labelFont.getName(), Font.BOLD, 18));
		resetPassButton.setEnabled(false);
	}

	private boolean validateAllFields() {
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
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);
		container.add(backToDashboardLabel);

		container.add(usernameLabel);
		container.add(usernameComboBox);

		container.add(passwordLabel);
		container.add(passwordTextField);
		container.add(rePasswordTextField);
		container.add(rePasswordLabel);
		container.add(matchPasswordLabel);

		container.add(isFirstChange);

		container.add(resetPassButton);

	}

	public void addActionEvent() {
		resetPassButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetPassButton) {

			JPanel panel = new JPanel();

			panel.setLayout(null);

			String password = rePasswordTextField.getText();
			String username = mapDisplayName.get(usernameComboBox.getSelectedItem().toString()).getUsername();


			int dialogResult = JOptionPane.showConfirmDialog(container,
					bundleMessage.getString("ResetPassword_Page_Reset_Confirmation") + " " + username + "?", "Confirmation",
					JOptionPane.YES_NO_OPTION);

			if (dialogResult == 0) {
				System.out.println("Yes option");
				empCtlObj.updatePassword(username, this.companyCode, password, isFirstChange.isSelected());
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