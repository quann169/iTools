package com.views;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.apache.log4j.Logger;

import com.controllers.LogController;
import com.controllers.SyncController;
import com.message.Enum;
import com.models.Assessor;
import com.models.Role;
import com.utils.AdvancedEncryptionStandard;
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

	final static Logger logger = Logger.getLogger(DashboardPage.class);

	LogController masterLogObj = new LogController();

	private static final Config cfg = new Config();
	private static final String companyCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("COMPANY_CODE"));
	private static final String machineCode = AdvancedEncryptionStandard.decrypt(cfg.getProperty("MACHINE_CODE"));

	String userName = "";

	JFrame root = this;
	Timer updateTimer;
	int expiredTime = Integer.valueOf(cfg.getProperty("Expired_Time")) * 1000;

	List<String> listRoleName = new ArrayList<>();

	DashboardPage(List<Role> listRoles, Assessor user) {
		this.listRoles = listRoles;
		userName = user.getUsername();
		for (Role role : this.listRoles) {
			listRoleName.add(role.getRoleName().toLowerCase());
		}

		this.user = user;
		setLayoutManager();
		setLocationAndSize();
		addComponentsToContainer();
		addActionEvent();
		masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.SHOW_DASHBOARD, "", "", companyCode, machineCode,
				StringUtils.getCurrentClassAndMethodNames());
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

		changePassLabel.setText("<html><html><font size=\"6\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_ChangePassword") + "</u></i></b></font></html></html>");
		changePassLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changePassLabel.setBounds(450, 10, 250, 60);
		changePassLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("changePassLabel");
				masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.CHANGE_PASS, "", "", companyCode, machineCode,
						StringUtils.getCurrentClassAndMethodNames());
				updateTimer.restart();
			}
		});

		splitLabel.setBounds(663, 10, 25, 60);
		splitLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		logOutLabel.setText("<html><font size=\"6\" face=\"arial\" color=\"#0181BE\"><b><i><u>"
				+ bundleMessage.getString("App_Logout") + "</u></i></b></font></html>");
		logOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logOutLabel.setBounds(685, 10, 150, 60);
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

		// accounting role
		unlockMachineButton.setBounds(80, 170, 305, 60);
		unlockMachineButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		takeOverButton.setBounds(420, 170, 305, 60);
		takeOverButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		putInsButton.setBounds(80, 320, 305, 60);
		putInsButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		// sub admin role
		resetPasswordButton.setBounds(80, 170, 305, 60);
		resetPasswordButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		lockAccountButton.setBounds(420, 170, 305, 60);
		lockAccountButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		manualSyncButton.setBounds(80, 320, 305, 60);
		manualSyncButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		getToolButton.setBounds(420, 320, 305, 60);
		getToolButton.setFont(new Font(labelFont.getName(), Font.BOLD, 25));

		updateTimer = new Timer(expiredTime, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				masterLogObj.insertLog(userName, Enum.DASHBOARD_PAGE, "", Enum.TIME_OUT, "", "", companyCode,
						machineCode, StringUtils.getCurrentClassAndMethodNames());
				String timeoutMess = MessageFormat.format(bundleMessage.getString("App_TimeOut"),
						cfg.getProperty("Expired_Time"));
				JOptionPane.showMessageDialog(container, timeoutMess, "Time Out Dashboard",
						JOptionPane.WARNING_MESSAGE);

				logger.info(userName + ": " + Enum.DASHBOARD_PAGE + " time out.");
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

	public void addComponentsToContainer() {
		container.add(splitLabel);
		container.add(changePassLabel);
		container.add(logOutLabel);

		if (listRoleName.contains(Enum.ACCT.text().toLowerCase())
				|| listRoleName.contains(Enum.TKOVER.text().toLowerCase())
				|| listRoleName.contains(Enum.PUTIN.text().toLowerCase())) {
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

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		updateTimer.restart();
		if (e.getSource() == unlockMachineButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.UNLOCK_MACHINE, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			final JDialog d = new JDialog();
			JPanel p1 = new JPanel(new GridBagLayout());
			JLabel progress = new JLabel("Please Wait...");
			p1.add(progress, new GridBagConstraints());
			d.getContentPane().add(p1);
			d.setBounds(100, 100, 500, 200);
			// d.setLocationRelativeTo(f);
			d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			d.setModal(true);

			SwingWorker<?, ?> worker = new SwingWorker<Void, Integer>() {
				protected Void doInBackground() throws InterruptedException {
					int x = 0;
					for (; x <= 100; x += 10) {
						publish(x);
						Thread.sleep(200);
					}
					return null;
				}

				protected void process(List<Integer> chunks) {
					int selection = chunks.get(chunks.size() - 1);
					progress.setText("Please Wait..." + selection + "s");
				}

				protected void done() {
					System.out.println("Complete");
					d.dispose();
					if (listRoleName.contains(Enum.PUTIN.text().toLowerCase())) {
						putInsButton.setEnabled(true);
					}

					if (listRoleName.contains(Enum.TKOVER.text().toLowerCase())) {
						takeOverButton.setEnabled(true);
					}
				}
			};
			worker.execute();
			d.setVisible(true);
		}
		if (e.getSource() == takeOverButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.TKOVER, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			root.dispose();
			root = new PutInTakeOverPage(user, Enum.TKOVER.text());
			StringUtils.frameInit(root, bundleMessage);
			root.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			root.show();
		}
		if (e.getSource() == putInsButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.PUTIN, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			root.dispose();
			root = new PutInTakeOverPage(user, Enum.PUTIN.text());
			StringUtils.frameInit(root, bundleMessage);
			root.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			root.show();
		}

		if (e.getSource() == resetPasswordButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.RESET_PASS, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			root.dispose();
			root = new ResetPasswordPage(user, true, false);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			root.show();
		}
		if (e.getSource() == lockAccountButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.LOCK_USER, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			root.dispose();
			root = new LockUnlockAccountPage(user, true);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			root.show();
		}

		if (e.getSource() == getToolButton) {
			updateTimer.stop();
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.GETTOOL, "", "", companyCode, machineCode,
					StringUtils.getCurrentClassAndMethodNames());
			root.dispose();
			root = new EmployeePage(userName, true);
			StringUtils.frameInit(root, bundleMessage);
			// empPage.setJMenuBar(StringUtils.addMenu());
			root.setTitle(user.getUsername() + " - " + user.getFirstName() + " " + user.getLastName());
			root.show();
		}

		if (e.getSource() == manualSyncButton) {
			SyncController syncCtl = new SyncController();
			List<String> syncResult = syncCtl.syncDataManually(companyCode, machineCode);
			masterLogObj.insertLog(userName, Enum.ASSESSOR, "", Enum.SYNC_MANUALLY, "", syncResult.toString(),
					companyCode, machineCode, StringUtils.getCurrentClassAndMethodNames());

		}
	}

}
